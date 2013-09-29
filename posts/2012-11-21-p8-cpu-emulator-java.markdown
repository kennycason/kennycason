---
title: P8 CPU Emulator - Java
author: Kenny Cason
tags: assembly, cpu emulator, Java, p8
---

A Java implementation of the P8 CPU.

Github Repo <a href="https://github.com/kennycason/p8" target="_blank">here</a>

Based on the documentation found <a href="http://www.rexfisher.com/P8/P8_Instructions.htm" target="_blank"><b>Here</b></a> 

P8.java

```{.java .numberLines startFrom="1"}
package lib.cpu.p8;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Simple 8-bit CPU modeled after: http://www.rexfisher.com/P8/P8_TOC.htm
 * http://www.rexfisher.com/P8/P8_Instructions.htm
 * 
 * @author Kenny
 * 
 */
public class P8 {
	
	private static final String[] OPCODE_NAMES = new String[] {
		 "", 		// ---
		 "IN", 		// 00001
		 "OUT",		// 00010
		 "", 		// ---
		 "JMP", 	// 00100
		 "JNZ",  	// 00101
		 "JZ",  	// 00110
		 "CMP",  	// 00111
		 "LDA",  	// 01000
		 "LDR",  	// 01001
		 "STA",  	// 01010
		 "STR",  	// 01011
		 "ADD",  	// 01100
		 "SUB",  	// 01101
		 "DEC",  	// 01110
		 "", 		// ---
		 "OR", 		// 10000
		 "INV",  	// 10001
		 "SHL" 		// 10010
	};
	
	private static final String[] ADDRESS_MODE_NAMES = new String[] {
		 "MEM(address)",	// Direct Memory Address in Byte 2
		 "---", 			// Not Used
		 "A", 				// A Register
		 "R", 				// R Register
		 "MEM(R)", 			// Memory Address in R Register
		 "---", 			// Not Used
		 "data", 			// Byte 2 of Instruction
		 "---"				// Not Used
	};

	public Registers r;

	public MMU mmu;

	public Port port;

	public boolean halt;

	public boolean printStack = false;
	
	public boolean printOp = true;

	public P8() {
		this(0x100, 0x100);
	}
	
	public P8(int stackSize) {
		this(stackSize, 0x100);
	}
	
	public P8(int stackSize, int portSize) {
		r = new Registers();
		mmu = new MMU(stackSize); // 0x00 - 0xFF
		port = new Port(portSize);
		halt = false;
	}

	public void exec() {
		if (!halt) {
			try {
				int instr = mmu.readByte(r.IP++);
				int opCode = (instr & 0xF8) >> 3; // high 5 bits
				int operand = getOperand(instr);
				callOp(opCode, operand);
				if(printOp) {
					System.out.println(getInstructionName(instr));
				}
			} catch (Exception e) {
				halt = true;
				System.out.println("["  + e.getMessage() + "] Halting...");
			}
		}

	}

	public void dispatch() {
		while (!halt) {
			exec();
			if (printStack) {
				System.out.println(this);
			}
		}
	}
	
	public void reset() {
		r.reset();
		mmu.reset();
		port.reset();
		halt = false;
	}

	private int getOperand(int instr) {
		int addrMode = instr & 0x07; // low 3 bits
		switch (addrMode) {
			case 0x00: // Direct Memory Address in Byte 2
				return mmu.readByte(mmu.readByte(r.IP++));
			case 0x01: // Not Used ---
				halt = true;
				return 0x00;
			case 0x02: // Register A Register
				return r.A;
			case 0x03: // Register R Regsiter
				return r.R;
			case 0x04: // Indirect Memory Address in R Register
				r.IP++;
				return mmu.readByte(r.R);
			case 0x05: // Not Used ---
				halt = true;
				return 0x00;
			case 0x06: // Immediate Byte 2 of Instruction
				return mmu.readByte(r.IP++);
			case 0x07: // Not Used ---
				halt = true;
				return 0x00;
			default:
				halt = true;
				return 0x00;
		}
	}

	private void callOp(int opCode, int operand) {
		switch (opCode) {
			case 0x01: // IN 00001
				IN(operand);
				break;
			case 0x02: // OUT 00010
				OUT(operand);
				break;
			case 0x04: // JMP 00100
				JMP(operand);
				break;
			case 0x05: // JNZ 00101
				JNZ(operand);
				break;
			case 0x06: // JZ 00110
				JZ(operand);
				break;
			case 0x07: // CMP 00111
				CMP(operand);
				break;
			case 0x08: // LDA 01000
				LDA(operand);
				break;
			case 0x09: // LDR 01001
				LDR(operand);
				break;
			case 0x0A: // STA 01010
				STA(operand);
				break;
			case 0X0B: // STR 01011
				STR(operand);
				break;
			case 0x0C: // ADD 01100
				ADD(operand);
				break;
			case 0x0D: // SUB 01101
				SUB(operand);
				break;
			case 0x0E: // DEC 01110
				DEC(operand);
				break;
			case 0x10: // OR 10000
				OR(operand);
				break;
			case 0x11: // INV 10001
				INV(operand);
				break;
			case 0x12: // SHL 10010
				SHL(operand);
				break;
			default:
				XX();
				break;
		}
	}

	private String getInstructionName(int instr) {
		int opCode = (instr & 0xF8) >> 3; // high 5 bits
		int addrMode = instr & 0x07; // low 3 bits
		String instrName = " ["
				+ String.format("%5s", Integer.toBinaryString(opCode)).replace(
						' ', '0')
				+ " "
				+ String.format("%3s", Integer.toBinaryString(addrMode))
						.replace(' ', '0') + "] ";
	
		if(opCode < OPCODE_NAMES.length) {
			instrName += P8.OPCODE_NAMES[opCode] + " " + P8.ADDRESS_MODE_NAMES[addrMode];
		}
		return instrName;
	}

	/*
	 * Instructions
	 */
	private void XX() {
		System.out.println("Unknown Instruction. Halting...");
		halt = true;
	}

	// 1. Input / Output. These instructions transfer data between the
	// accumulator and external I/O devices.
	/**
	 * IN = Read Input Port
	 * 
	 * @param operand
	 */
	private void IN(int operand) {
		r.A = port.readByte(operand);
	}

	/**
	 * OUT = Write Output Port
	 * 
	 * @param operand
	 */
	private void OUT(int operand) {
		port.writeByte(operand, r.A);
	}

	// 2. Program Control. These instructions change the sequence of program
	// execution. They are often called branch instructions.

	/**
	 * JMP = Unconditional Jump
	 */
	private void JMP(int operand) {
		r.IP = operand;
	}

	/**
	 * JNZ = Jump If Not Zero (Conditional Jump)
	 */
	private void JNZ(int operand) {
		if (r.Z == 0) {
			JMP(operand);
		}
	}

	/**
	 * JZ = Jump If Zero (Conditional Jump)
	 */
	private void JZ(int operand) {
		if (r.Z == 1) {
			JMP(operand);
		}
	}

	/**
	 * CMP = Compare (Sets / Resets Zero Bit For Conditional Jumps)
	 */
	private void CMP(int operand) {
		if (r.A - operand == 0) {
			r.Z = 1;
		}
	}

	// 3. Data Transfer. These instructions cause data in one location (either
	// the internal registers or external memory) to be copied to another
	// location.

	/**
	 * LDA = Load A Register
	 */
	private void LDA(int operand) {
		r.A = operand;
	}

	/**
	 * LDR = Load R Register
	 */
	private void LDR(int operand) {
		r.R = operand;
	}

	/**
	 * STA = Store A Register
	 * 
	 * @param operand
	 */
	private void STA(int operand) {
		mmu.writeByte(operand, r.A);
	}

	/**
	 * STR = Store R Register
	 */
	private void STR(int operand) {
		mmu.writeByte(operand, r.R);
	}

	// 4. Arithmetic. These instructions perform numerical operations on data.
	// Floating point operations are not supported.

	/**
	 * ADD = Add To A Register
	 */
	private void ADD(int operand) {
		r.A += operand;
	}

	/**
	 * SUB = Subtract From A Register
	 */
	private void SUB(int operand) {
		r.A -= operand;
	}

	/**
	 * DEC = Decrement
	 */
	private void DEC(int operand) {
		r.A = operand--;
	}

	// 5. Logical. These instructions perform Boolean operations on data,
	// including bit shifting.

	/**
	 * OR = Or With A Register
	 * 
	 * @param operand
	 */
	private void OR(int operand) {
		r.A |= operand;
	}

	/**
	 * INV = Invert & Move To A Register
	 */
	private void INV(int operand) {
		r.A = ~operand;
	}

	/**
	 * SHL = Shift Left & Move To A Register
	 */
	private void SHL(int operand) {
		r.A = operand << 1;
	}
	
	/**
	 * 
	 * @param instructions
	 */
	public void loadInstructions(int[] instructions) {
		for (int i = 0; i < instructions.length; i++) {
			mmu.writeByte(i, instructions[i]);
		}
	}

	/**
	 * Binary representation
	 * 
	 * @param instructions
	 */
	public void loadInstructions(String[] instructions) {
		for (int i = 0; i < instructions.length; i++) {
			mmu.writeByte(i, Integer.parseInt(instructions[i], 2));
		}
	}
	
	/**
	 * 
	 */
	public void loadInstructionsFromFile(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = "";
			ArrayList<String> instructions = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				line = line.trim();
				instructions.add(line.substring(0, 8));
			}
			br.close();
			loadInstructions(instructions.toArray(new String[instructions.size()]));
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Halting...");
			halt = true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Halting...");
			halt = true;
		}
	}

	public String toString() {
		return r.toString() + "\n" + mmu.toString();
	}

}

```

Registers.java

```{.java .numberLines startFrom="1"}
package lib.cpu.p8;

public class Registers {
	
	public int IP;	// Instruction Pointer
	
	public  int A;	// Accumulator
	
	public int R;	// Data/Address 
	
	public int Z;	// zero flag
	
	public void reset() {
		IP = 0;
		A = 0;
		R = 0;
		Z = 0;
	} 
	
	public String toString() {
		return "[\n\tIP: " + IP + "\n" + 
				"\tA: " + A + "\n" + 
				"\tR: " + R + "\n" + 
				"\tZ: " + Z + "\n]";
	}
	
}

```

MMU.java

```{.java .numberLines startFrom="1"}
package lib.cpu.p8;

public class MMU extends AbstractObservableBuffer {

	public MMU(int size) {
		super(size);
	}

}

```

Port.java

```{.java .numberLines startFrom="1"}
package lib.cpu.p8;

public class Port extends AbstractObservableBuffer {

	public Port(int size) {
		super(size);
	}
	
}

```

AbstractObservableBuffer.java

```{.java .numberLines startFrom="1"}
package lib.cpu.p8;

import java.util.Observable;

public class AbstractObservableBuffer extends Observable {

	protected int[] buffer;
	
	public AbstractObservableBuffer(int size) {
		buffer = new int[size];
	}
	
	public void reset() {
		for(int i = 0; i < buffer.length; i++) {
			buffer[i] = 0x00;
		}
	}
	
	public void writeByte(int address, int value) throws ArrayIndexOutOfBoundsException {
		if(address < 0 || address >= buffer.length) {
			throw new ArrayIndexOutOfBoundsException(address);
		}
		buffer[address] = value & 0xFF;
		setChanged();
		notifyObservers(address);
	}
	
	public void writeWord(int address, int value)  throws ArrayIndexOutOfBoundsException {
		writeByte(address, value & 0xFF);
		writeByte(address + 1, (value >> 8) & 0xFF);
	}
	
	public int readByte(int address) throws ArrayIndexOutOfBoundsException {
		if(address < 0 || address >= buffer.length) {
			throw new ArrayIndexOutOfBoundsException(address);
		}
		// System.out.println("Reading: " + Integer.toHexString(address).toUpperCase() + " => " +  Integer.toBinaryString(buffer[address]).toUpperCase());
		return buffer[address];
	}
	
	public int readWord(int address) throws ArrayIndexOutOfBoundsException {
		return readByte(address) + (readByte(address + 1) << 8);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < buffer.length; i++) {
			sb.append("[" + String.format("%8s", Integer.toBinaryString(i)).replace(' ', '0')  + "] 0x" + Integer.toHexString(i).toUpperCase());
			sb.append("\t=>\t");
			sb.append("[" + String.format("%8s", Integer.toBinaryString(buffer[i])).replace(' ', '0')  + "] 0x" + Integer.toHexString(buffer[i]).toUpperCase());
			sb.append("\n");
		}
		return sb.toString();
	}
	
}

```

IPortListener.java

```{.java .numberLines startFrom="1"}
package lib.cpu.p8;

import java.util.Observer;

interface IPortListener extends Observer {

}

```

SinglePortListener.java

```{.java .numberLines startFrom="1"}
package lib.cpu.p8;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

public class SinglePortListener implements IPortListener {
	
	private final int port;
	
	private Queue<Integer> data;

	public SinglePortListener(int port) {
		this.port = port;
		data = new LinkedList<Integer>();
	}
	
	@Override
	public void update(Observable o, Object obj) {
		if(o instanceof Port && obj instanceof Integer) {
			Port p = (Port) o;
			Integer portRead = (Integer) obj;
			if(portRead == port) {
				// System.out.println("Reading Port: 0x" + Integer.toHexString(port).toUpperCase() + " => " + p.readByte(port));
				data.add( p.readByte(port));
			}
		}
	}

	public int getPort() {
		return port;
	}
	
	public int size() {
		return data.size();
	}
	
	public int pop() {
		if(data.size() > 0) {
			return data.poll();
		}
		return -1;
	}
	
}

```

test.asm

```{.asm .numberLines startFrom="1"}
01100110	; ADD A, data(0x4)
00000100
01101110	; SUB A, data(0x1)
00000001
00111110	; CMP A, data(0x00)
00000000
00110110	; JZ data(0x00)
11111111	; End program
00101110	; JNZ data(0x02)
00000010

```

MMUTest.java

```{.java .numberLines startFrom="1"}
package lib.cpu.p8;

import static org.junit.Assert.*;
import lib.cpu.p8.MMU;

import org.junit.Test;

public class MMUTest {

	@Test
	public void testReadWrite() {
		MMU mmu = new MMU(0x10);
		
		// first
		mmu.writeByte(0x00, 0xFF);
		assertEquals(0xFF, mmu.readByte(0x00));
		
		// last
		mmu.writeByte(0xF, 0xFF);
		assertEquals(0xFF, mmu.readByte(0x0F));
		
		// out of bounds
		try {
			mmu.readByte(0x10);
			assertTrue(false);
		} catch(ArrayIndexOutOfBoundsException e) {
			assertTrue(true);
		}
		
		try {
			mmu.readByte(-1);
			assertTrue(false);
		} catch(ArrayIndexOutOfBoundsException e) {
			assertTrue(true);
		}
		
		// read/write word
		mmu.reset();
		mmu.writeWord(0x00, 0xABCD);
		assertEquals(0xABCD, mmu.readWord(0x00));
		
		mmu.writeWord(0xE, 0xABCD);
		assertEquals(0xABCD, mmu.readWord(0x0E));
		
		System.out.println(mmu.toString());
	}
	
	@Test
	public void testDefault() {
		MMU mmu = new MMU(0x10);
		for(int i = 0; i < 0x10; i++) {
			assertEquals(0, mmu.readByte(i));
		}
	}
	
	@Test
	public void miscTests() {
		MMU mmu = new MMU(0x10);
		int ip = 0x00;
		mmu.writeByte(ip++, 0xFF);
		mmu.writeByte(ip++, 0xCC);
		System.out.println(mmu.toString());
		
		//System.out.println(Integer.toBinaryString(0xF8));
	}

}

```

P8Test.java

```{.java .numberLines startFrom="1"}
package lib.cpu.p8;

import static org.junit.Assert.assertEquals;
import lib.utils.FileUtils;

import org.junit.Test;

public class P8Test {

	@Test
	public void test() {
		P8 p8 = new P8();
		
		int[] instructions = new int[] {
				0x66,	// 0b01100110  ADD A, data(0x4)
				0x04,
				
				0x6E,	// 0b01101110  SUB A, data(0x1)
				0x01,
				
				0x3E,	// 0b00111010  CMP A, data(0x00)
				0x00,
				0x36,	// 0b00110110  JZ data(0x00)
				0xFF,   // Will end program
				0x2E,	// 0b00110110  JNZ data(0x02)
				0x02,		
		};
		p8.loadInstructions(instructions);
		// System.out.println(p8);
		//p8.printStack = true;
		p8.dispatch();
		
	}
	
	@Test
	public void testLoadBinary() {
		P8 p8 = new P8();
		
		String[] instructions = new String[] {
				"01100110", // ADD A, data(0x4)
				"00000100",
				
				"01101110",	// SUB A, data(0x1)
				"00000001",
				
				"00111110",	// CMP A, data(0x00)
				"00000000",
				"00110110",	// JZ data(0x00)
				"11111111", // Will end program
				"00101110",	// JNZ data(0x02)
				"00000010",		
		};
		p8.loadInstructions(instructions);
		// p8.printStack = true;
		// System.out.println(p8);
		p8.dispatch();
		
	}
	
	@Test
	public void testOUT() {
		// test 1
		SinglePortListener port0 = new SinglePortListener(0);
		SinglePortListener port1 = new SinglePortListener(1);
		
		P8 p8 = new P8();
		p8.port.addObserver(port0);
		p8.port.addObserver(port1);
		
		String[] instructions = new String[] {
				"01100110", // ADD A, data(0x4)
				"00000100",
				
				"00010110", // OUT data(0x0) ; write A to port 0x00
				"00000000",
				
		};
		p8.loadInstructions(instructions);
		p8.exec();
		p8.exec();
		assertEquals(1, port0.size());
		assertEquals(0x4, port0.pop());
		assertEquals(0, port1.size());
		
		// test 2
		p8.reset();
		instructions = new String[] {
				"01100110", // ADD A, data(0x7)
				"00000111",
				
				"00010110", // OUT data(0x01) ; write A to port 0x01
				"00000001",
		};
		p8.loadInstructions(instructions);
		p8.exec();
		p8.exec();
		assertEquals(0, port0.size());
		assertEquals(1, port1.size());
		assertEquals(0x7, port1.pop());
		
	}
	
	@Test
	public void testPortIN() {

		P8 p8 = new P8();
		p8.port.writeByte(0x00, 0xFE); // write to port
		
		String[] instructions = new String[] {
			
				"00001010", // IN A ; read port(A)
				"01010110", // STA data
				
		};
		p8.loadInstructions(instructions);
		assertEquals(p8.r.A, 0x00);
		p8.exec();
		assertEquals(p8.r.A, 0xFE);
		p8.exec();
		assertEquals(0xFE, p8.mmu.readByte(0x00));
		
	}
	
	@Test
	public void testLoadFile() {
		P8 p8 = new P8();
		p8.loadInstructionsFromFile(FileUtils.home() + "krunch/src/lib/cpu/p8/test.asm");
		assertEquals(p8.mmu.readByte(0x00), 0x66);
		assertEquals(p8.mmu.readByte(0x01), 0x04);
		assertEquals(p8.mmu.readByte(0x02), 0x6E);
		assertEquals(p8.mmu.readByte(0x03), 0x01);
		assertEquals(p8.mmu.readByte(0x04), 0x3E);
		assertEquals(p8.mmu.readByte(0x05), 0x00);
		assertEquals(p8.mmu.readByte(0x06), 0x36);
		assertEquals(p8.mmu.readByte(0x07), 0xFF);
		assertEquals(p8.mmu.readByte(0x08), 0x2E);
		assertEquals(p8.mmu.readByte(0x09), 0x02);
			
	}

}

```