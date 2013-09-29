package nn;

import java.util.LinkedList;

import nn.XMLParser;


public class TrainData {
	
	private XMLParser parser;
	private LinkedList<String> parsedXML;
	
	private String description;
	
	private int inputNodes;
	private int numCenterLayers;
	private int centerNodes;
	private int outputNodes;
	
	private double learningRate;
	private double error;
	private int maxCycles;
	
	private int numData;
	
	private int inputWidth, inputHeight;
	private int outputWidth, outputHeight;
	
	private double[][] trainingSet;
	private double[][] desiredOutputs;
	
	private LinkedList<String> pre;
	
	public TrainData(String file) {
		loadFile(file);
	}
	
	/**
	 * getParsedXML - another way to get the parsed Training Data
	 * probably won't need this method.
	 * @return LinkedList
	 */
	public LinkedList<String> getParsedXML() {
		return parsedXML;
	}
	
	/**
	 * getter methods for parsedXML data
	 */
	public String getDescription() {
		return description;
	}
	
	public int getNumInputNodes() {
		return inputNodes;
	}
	
	public int getNumCenterNodes() {
		return centerNodes;
	}
	
	public int getNumOutputNodes() {
		return outputNodes;
	}
	
	public int getNumCenterLayers() {
		return numCenterLayers;
	}
	
	public double getLearningRate() {
		return learningRate;
	}
	
	public double getError() {
		return error;
	}
	
	public int getMaxCycles() {
		return maxCycles;
	}
	
	public int getNumData() {
		return numData;
	}
	
	public int getInputWidth() {
		return inputWidth;
	}
	
	public int getInputHeight() {
		return inputHeight;
	}
	
	public int getOutputWidth() {
		return outputWidth;
	}
	
	public int getOutputHeight() {
		return outputHeight;
	}
	
	public double[][] getTrainingSet() {
		return trainingSet;
	}
	
	public double[][] getDesiredOutputs() {
		return desiredOutputs;
	}

    /**
     * loadTextFileWithoutSpaces - load the data file to be fed to the MLL data structure
     * @Param String - the file name
     */
    public void loadFile(String file)
    {
		parser = new XMLParser(file);
		parsedXML = parser.getParsedData();
		int cData = 0; // which data is being input
		while(parsedXML.size() > 0) {
			String xml = parsedXML.poll();
			if(xml != null && xml.substring(0,1).equals("/")) {
				// do nothing
			} else if(xml.equalsIgnoreCase("description")) {
				description = parsedXML.poll();
			} else if(xml.equalsIgnoreCase("pre")) {
				pre.add(parsedXML.poll());
			} else if(xml.equalsIgnoreCase("innodes")) {
				inputNodes = Integer.parseInt(parsedXML.poll());
			} else if(xml.equalsIgnoreCase("midlayers")) {
				numCenterLayers = Integer.parseInt(parsedXML.poll());
			}else if(xml.equalsIgnoreCase("midnodes")) {
				centerNodes = Integer.parseInt(parsedXML.poll());
			}else if(xml.equalsIgnoreCase("outnodes")) {
				outputNodes = Integer.parseInt(parsedXML.poll());
			}else if(xml.equalsIgnoreCase("learnrate")) {
				learningRate = Double.parseDouble(parsedXML.poll());
			}else if(xml.equalsIgnoreCase("maxcycles")) {
				maxCycles = Integer.parseInt(parsedXML.poll());
			}else if(xml.equalsIgnoreCase("error")) {
				error = Double.parseDouble(parsedXML.poll());
			}else if(xml.equalsIgnoreCase("numdata")) {
				numData = Integer.parseInt(parsedXML.poll());
				trainingSet = new double[numData][inputNodes];
				desiredOutputs = new double[numData][outputNodes];
			}else if(xml.equalsIgnoreCase("inwidth")) {
				inputWidth = Integer.parseInt(parsedXML.poll());
			}else if(xml.equalsIgnoreCase("inheight")) {
				inputHeight = Integer.parseInt(parsedXML.poll());
			}else if(xml.equalsIgnoreCase("outwidth")) {
				outputWidth = Integer.parseInt(parsedXML.poll());
			}else if(xml.equalsIgnoreCase("outwidth")) {
				outputWidth = Integer.parseInt(parsedXML.poll());
			} else if(xml.equalsIgnoreCase("data")) {
				if(numData > 0 && cData < numData && inputNodes > 0 && outputNodes > 0) {
					for(int i = 0; i < inputNodes; i++) {
						parsedXML.poll(); // <i>
						trainingSet[cData][i] = Double.parseDouble(parsedXML.poll());
						parsedXML.poll(); //</i>
					}
					for(int i = 0; i < outputNodes; i++) {
						parsedXML.poll(); // <o>
						desiredOutputs[cData][i] = Double.parseDouble(parsedXML.poll());
						parsedXML.poll(); //</o>	
					}		
				}
				cData++;
			}
		}
    }  

}
