package nn;

import java.util.LinkedList;

/**
 * Neuron - MultiDoublylinkedListneuron - modified into a neuron
 * @author Kenneth Cason
 * @version 1.0
 */

public class Neuron {

	private double value; // ニューロンの値: neuron value
	private double learningRateCoefficient; 	// 学習率x学習率係数：　learning rate * learning rate coefficient = total learning rate
	
	private double refactoryPeriod; // 不応期
									// period in which a neuron must "rest" before firing again 
	private double refactoryRate;  	// 神経細胞が回復するレート
									// rate at which the neuron recharges
	private double refactoryLevel;	// 
	
	private LinkedList<Synapse> synapses; // axons
	private LinkedList<Neuron> prev;	// links to the previous connected neurons
	
	private boolean check;

	/**
	 *　Neuronのクラスを初期化する
	 * Constructor for objects of class Neuron
	 * @Param double - the value being stored in the neuron
	 */
	public Neuron(double d) {
		value = d;
		check = false;
		refactoryPeriod = refactoryRate = refactoryLevel = 0;
		prev = new LinkedList<Neuron>();
		synapses = new LinkedList<Synapse>();
	}
	
	/**
	 *　Neuronのクラスを初期化する
	 * @Param double - the value being stored in the neuron
	 */
	public Neuron() {
		value = 0;
		check = false;
		refactoryPeriod = refactoryRate = refactoryLevel = 0;
		prev = new LinkedList<Neuron>();
		synapses = new LinkedList<Synapse>();
	}

	/**
	 * getValue - ニューロンの値を得る - returns the value
	 * @Return double - the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * setValue - ニューロンの値を設定する - set the neuron value
	 * @Param double - the value
	 */
	public void setValue(double value) {
		this.value = value;
	}


	/**
	 * connect - 他のニューロンと連結: connect to another neuron
	 * @Param ニューロン: neuron 
	 */
	public void connect(Neuron neuron) {
		synapses.add(new Synapse(neuron));
	//	System.out.println("Orig new Weight length:" + weights.size());
		// now set previous connection
		//System.out.println("CONNECTING");
	//	System.out.println("neurons new Weight length:" + weights.size());
	//	System.out.println("This "+this);
	//	System.out.println("neuron "+neuron);
	//	System.out.println("Prev "+neuron.getPrev());
		neuron.getPrev().add(this);
	}

	/**
	 * getAllSynapses - 各シナプスを得る - gets all the synapses
	 * @Return LinkedList<Axon> - シナプス: synapses
	 */
	public LinkedList<Synapse> getAllSynapses() {
		return synapses;
	}

	/**
	 * getSynapse - 一つのシナプスを得る- get a synapse
	 * @Param i - which element to return
	 * @Return シナプス:　synapse
	 */
	public Synapse getSynapse(int i) {
		return synapses.get(i);
	}
	
	/**
	 * setAllSynapses - 各シナプスを設定する　- sets synapses
	 * @Param シナプス: synapses
	 */
	public void setAllSynapses(LinkedList<Synapse> axons) {
		this.synapses = axons;
	}

	/**
	 * getPrev - returns Linked List of previous neurons 
	 * @Param int - which element to return
	 * @Return Neuron - the specific linked element
	 */
	public LinkedList<Neuron> getPrev() {
		return prev;
	}
	

	/**
	 * getPrev - returns Linked List of previous neurons 
	 * @Param int - which element to return
	 * @Return Neuron - the specific linked element
	 */
	public Neuron getPrev(int i) {
		return prev.get(i);
	}
	
	/**
	 * ニューロンの学習率係数を設定する - set the neuron learning rate coefficient
	 * @param ニューロンの学習率係数:　neuron learning rate coefficient
	 */
	public void setLearningRateCoefficient(double rate) {
		learningRateCoefficient = rate;
	}

	/**
	 * ニューロンの学習率係数を得る - get the neuron learning rate coefficient
	 * @return ニューロンの学習率係数:　neuron learning rate coefficient
	 */
	public double getLearningRateCoefficient() {
		return learningRateCoefficient;
	}	
	
	/**
	 * 不応期レベルを得る - get refactory level 
	 * @return　不応期レベル: refactory Level
	 */
	public double getRefactoryLevel() {
		return refactoryLevel;
	}	
	
	/**
	 * 不応期レベルを設定する - set neurons refactory level
	 * @param 不応期レベル: refactory Level
	 */
	public void setRefactoryLevel(double level) {
		refactoryLevel = level;
	}

	
	/**
	 * 不応期のレートを設定する - set the refactory rate
	 * @param 不応期のレート: refactoryRate
	 */
	public void setRefactoryRate(double rate) {
		refactoryRate = rate;
	}

	/**
	 * 不応期のレートを得る - get the refactory rate
	 * @return 不応期のレート: refactoryRate
	 */
	public double getRefactoryRate() {
		return refactoryRate;
	}	
	
	/**
	 * 不応期を設定する - set the refactory period
	 * @param 不応期:　refactoryPeriod
	 */
	public void setRefactoryPeriod(double period) {
		refactoryPeriod = period;
	}

	/**
	 *  不応期を得る - get the refactory period
	 * @return　不応期:　refactoryPeriod
	 */
	public double getRefactoryPeriod() {
		return refactoryPeriod;
	}		
	
	
	/**
	 * getCheck - チェックが付いているかどうか - returns whether or not the neuron is checked
	 * @Return boolean - checked or not
	 */
	public boolean getCheck() {
		return check;
	}

	/**
	 * check - チェックを付ける - check the neuron
	 */
	public void check() {
		check = true;
	}

	/**
	 * unCheck - チェックを消す - uncheck the neuron
	 */
	public void unCheck() {
		check = false;
	}

	/**
	 * destroySynapse - 一つのシナプスを消す - delete a synapse
	 * @Param int - which synapse to delete
	 * @Return synapse - the synapse being deleted
	 */
	public Synapse destroySynapse(int i) {
		// first remove the previous link in the connecting neuron, i
		synapses.get(i).getConnected().getPrev().remove(this);
	
		return synapses.remove(i);
	}
	
	/**
	 * destroyAllAxons - 各シナプスを消す - deletes all axons
	 * @Return synapse
	 */
	public LinkedList<Synapse> destroyAllAxons() {
		// first remove the previous link in all connecting neurons
		for(Synapse n : synapses) {
			n.getConnected().getPrev().remove(this);
		}
		LinkedList<Synapse> temp = synapses;
		synapses.clear();
		return temp;
	}
	

	/**
	 * isEqual - compare 2 pieces of value
	 * @Para double - the value to compare
	 * @Return boolean - returns true if equal, else false
	 */
	public boolean isEqual(double value) {
		// System.out.println(this.value+" "+value);
	    if (this.value == value) {
		    return true;
		}
		return false;
	}
	
	/**
	 * compare - compare 2 pieces of value
	 * @Para double - the value to compare
	 * @Return double - returns the absolute value of the difference
	 * between values
	 */
	public double compare(double value) {
		return Math.abs(this.value - value);
	}

}
