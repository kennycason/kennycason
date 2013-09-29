package nn;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * 
 * @author kenny cason
 *
 */
public class NeuralNetworkTool {

	NeuralNetwork nn;
	LinkedList<TrainData> train = new LinkedList<TrainData>();;
	double maxCycles;
	double desiredError;

	/**
	 * 
	 */
	public NeuralNetworkTool() {
	}
	
	public NeuralNetwork getNN() {
		return nn;
	}
	
	/**
	 * 
	 * @param i
	 */
	public void initToTrainDataSpecifications(int i) {
		if(i >= 0 && i < train.size()) {
			nn = new NeuralNetwork();

			nn.init(train.get(i).getNumInputNodes(), train.get(i).getNumCenterLayers(), 
					train.get(i).getNumCenterNodes(), train.get(i).getNumOutputNodes(),false); 
			desiredError = train.get(i).getError();
			maxCycles = train.get(i).getMaxCycles();
			nn.setLearningRate(train.get(i).getLearningRate());
		} else {
			System.out.println("Can't init the neural network!");
		}
	}
	
	/**
	 * 
	 * @param inputNeurons
	 * @param centerLayers
	 * @param centerNeurons
	 * @param outputNeurons
	 * @param error
	 * @param cycles
	 * @param learnRate
	 */
	public void initToCustomSpefications(int inputNeurons, int centerLayers, int centerNeurons,
			int outputNeurons, double error, int cycles, double learnRate, boolean useBias) {
		nn = new NeuralNetwork();
		nn.init(inputNeurons, centerLayers, centerNeurons, outputNeurons,useBias); 
		desiredError = error;
		maxCycles = cycles;
		nn.setLearningRate(learnRate);
	}
	
	/**
	 * 
	 * @param xmlFile
	 */
	public void addTrainData(String _xmlFile) {
		train.add(new TrainData(_xmlFile));
		System.out.println("Added Train Data");
	}
	
	/**
	 * 
	 * @param data
	 */
	public void addTrainData(TrainData _data) {
		train.add(_data);
	}
	
	/**
	 * 
	 * @return
	 */
	public NeuralNetwork trainNNWithReinforcement() {
		System.out.println("Training Neural Network.");
		// 訓練データを学習する: teach the training data
		int count = 0;
		double error = desiredError + 1; // make it greater than the desiredError
		int numData = 0;
		for(int xmlFile = 0; xmlFile < train.size(); xmlFile++) {
			numData += train.get(xmlFile).getNumData();
		}
		while (error > desiredError && count < maxCycles) {
			error = 0;
			count++;
			// 各訓練データを誤差が小さくなるまで学習する: teach each piece of training data until the error reaches desired minimal value.
			for(int xmlFile = 0; xmlFile < train.size(); xmlFile++) {
				for(int i = 0; i < train.get(xmlFile).getNumData(); i++) {
					// 入力層に値を設定する: set the input layers values
					for(int j = 0; j < train.get(xmlFile).getNumInputNodes(); j++) {
						nn.setInput(j,train.get(xmlFile).getTrainingSet()[i][j]);
					}
					// 教師信号を設定する: set the training signal
					for(int j = 0; j < train.get(xmlFile).getNumOutputNodes(); j++) {
						nn.setTeacherSignal(j,train.get(xmlFile).getDesiredOutputs()[i][j]);
					}
					// 学習開始: begin teaching
					nn.feedForward();
					error += nn.calculateError();
					nn.backPropagate();
				}
			}
			error /= numData;
			System.out.println(count + "\t" + error);
		}
		System.out.println("Cycles: "+count + "\tError:" + error);
		return nn;
	}
	
	/**
	 * 
	 * @return
	 */
	public NeuralNetwork trainNNIteratively() {
		System.out.println("Training Neural Network.");
		// 訓練データを学習する: teach the training data
		for(int xmlFile = 0; xmlFile < train.size(); xmlFile++) {
			int count = 0;
			double error = desiredError + 1; // make it greater than the desiredError
			while (error > desiredError && count < maxCycles) {
				error = 0;
				count++;
				// 各訓練データを誤差が小さくなるまで学習する: teach each piece of training data until the error reaches desired minimal value.
				for(int i = 0; i < train.get(xmlFile).getNumData(); i++) {
					// 入力層に値を設定する: set the input layers values
					for(int j = 0; j < train.get(xmlFile).getNumInputNodes(); j++) {
						nn.setInput(j,train.get(xmlFile).getTrainingSet()[i][j]);
					}
					// 教師信号を設定する: set the training signal
					for(int j = 0; j < train.get(xmlFile).getNumOutputNodes(); j++) {
						nn.setTeacherSignal(j,train.get(xmlFile).getDesiredOutputs()[i][j]);
					}
					// 学習開始: begin teaching
					nn.feedForward();
					error += nn.calculateError();
					nn.backPropagate();
				}
				error /= train.get(xmlFile).getNumData();
				//System.out.println(count + "\t" + error);
			}
			System.out.println("Cycles: "+count + "\tError:" + error);
		}
		return nn;
	}
	
	/**
	 * 
	 * @param file
	 */
	public void saveNN(String file) {
	    try {
	        BufferedWriter out = new BufferedWriter(new FileWriter(file));
	        out.write("<usebias>"+nn.isUseBias()+"</usebias>\n");
        	out.write("<numilneurons>"+nn.getInputLayer().getNumNeurons()+"</numilneurons>\n");
        	out.write("<numcl>"+nn.getCenterLayers().length+"</numcl>\n");
        	for(int i = 0; i < nn.getCenterLayers().length; i++) {
        		out.write("<numclneurons>"+nn.getCenterLayers()[i].getNumNeurons()+"</numclneurons>\n");
        	}
        	out.write("<numolneurons>"+nn.getOutputLayer().getNumNeurons()+"</numolneurons>\n");
	        out.write("<il>\n");
        	out.write("  <childl>"+nn.getInputLayer().childLayer.getNumNeurons()+"</childl>\n");
            for(int j = 0; j < nn.getInputLayer().getNumNeurons(); j++) {
            	out.write("  <node>\n");
            	for(int k = 0; k < nn.getInputLayer().childLayer.getNumNeurons(); k++) {
	            	out.write("    <weight>"+String.valueOf(nn.getInputLayer().getNeuron(j).getSynapse(k).getWeight())+"</weight>\n");
	            	if(nn.getInputLayer().isUseBias()) {
	            		out.write("    <biasweight>"+String.valueOf(nn.getInputLayer().getBiasWeight(k))+"</biasweight>\n");
	            		out.write("    <biasvalue>"+String.valueOf(nn.getInputLayer().getBiasValue(k))+"</biasvalue>\n");
	            	}
            	}	
            	out.write("  </node>\n");
            }
	        out.write("</il>\n");
	      
	        for(int i = 0; i < nn.getCenterLayers().length; i++) {
	        	out.write("<cl>\n");
	        	out.write("  <parentl>"+nn.getCenterLayers()[i].parentLayer.getNumNeurons()+"</parentl>\n");
	        	out.write("  <childl>"+nn.getCenterLayers()[i].childLayer.getNumNeurons()+"</childl>\n");
	            for(int j = 0; j < nn.getCenterLayers()[i].getNumNeurons(); j++) {
	            	out.write("  <node>\n");
	            	for(int k = 0; k < nn.getCenterLayers()[i].childLayer.getNumNeurons(); k++) {
		            	out.write("    <weight>"+String.valueOf(nn.getCenterLayers()[i].getNeuron(j).getSynapse(k).getWeight())+"</weight>\n");
		            	if(nn.getCenterLayers()[i].isUseBias()) {
		            		out.write("    <biasweight>"+String.valueOf(nn.getCenterLayers()[i].getBiasWeight(k))+"</biasweight>\n");
		            		out.write("    <biasvalue>"+String.valueOf(nn.getCenterLayers()[i].getBiasValue(k))+"</biasvalue>\n");
		            	}
		            }
	            	out.write("  </node>\n");
	            }
	        	out.write("</cl>\n");
	        }
	        
	        out.write("<ol>\n");
        	out.write("  <parentl>"+nn.getOutputLayer().parentLayer.getNumNeurons()+"</parentl>\n");
            for(int j = 0; j < nn.getOutputLayer().getNumNeurons(); j++) {
            	out.write("  <node></noded>\n");
            }
	        out.write("</ol>\n");
	        out.close();
	        System.out.println("Saved Neural Network to file: "+file);
	    } catch (IOException e) {
	    	System.out.println("Error writing Neural Network save file!");
	    }
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public NeuralNetwork loadNN(String file)  {
		int numInputNeurons = 0;
		int numCenterLayers = 0;
 		int numCenterNeurons = 0;
		int numOutputNeurons = 0;
		boolean useBias = false;
		
		LinkedList<double[]> biasValuesList = new LinkedList<double[]>(); 	// バイアス値（閾値）: bias value bias value 
		LinkedList<double[]> biasWeightsList = new LinkedList<double[]>();	// バイアスの重み:bias weights	
		LinkedList<double[]> weightsList = new LinkedList<double[]>();	// 重み: weights	
		
		XMLParser parser = new XMLParser(file);
		LinkedList<String> parsedXML = parser.getParsedData();
		int initNum = 0; // when equals for init the neural network
		while(parsedXML.size() > 0) {
			if(initNum == 4) {
				nn.init(numInputNeurons, numCenterLayers, numCenterNeurons, numOutputNeurons,useBias);
				initNum = 0;
			}
			String xml = parsedXML.poll();
			if(xml != null && xml.substring(0,1).equals("/")) {
				// do nothing
			} else if(xml.equalsIgnoreCase("usebias")) {
				useBias = Boolean.parseBoolean(parsedXML.poll());
			} else if(xml.equalsIgnoreCase("numilneurons")) {
				numInputNeurons = Integer.parseInt(parsedXML.poll());
				initNum++;
			} else if(xml.equalsIgnoreCase("numcl")) {
				numCenterLayers = Integer.parseInt(parsedXML.poll());
				initNum++;
			} else if(xml.equalsIgnoreCase("numclneurons")) {
				numCenterNeurons = Integer.parseInt(parsedXML.poll());
				initNum++;
			} else if(xml.equalsIgnoreCase("numolneurons")) {
				numOutputNeurons = Integer.parseInt(parsedXML.poll());
				initNum++;
			} else if(xml.equalsIgnoreCase("il")) {
				double[] biasValues = new double[numCenterNeurons];
				double[] biasWeights = new double[numCenterNeurons];
				double[] weights = new double[numCenterNeurons];
				while(!parsedXML.peek().equals("/il")) {
					System.out.println("Loading Input Layer");
					if(parsedXML.poll().equalsIgnoreCase("node")) {
						for(int i = 0; i < numInputNeurons; i++) {
							xml = parsedXML.poll();
							if(xml.equalsIgnoreCase("weight")) {
								weights[i] = Double.parseDouble(parsedXML.poll());
								parsedXML.poll(); //</weight>
							} else if(xml.equalsIgnoreCase("biasweight")) {
								biasWeights[i] = Double.parseDouble(parsedXML.poll());
								parsedXML.poll(); //</biasweight>
							} else if(xml.equalsIgnoreCase("biasvalue")) {
								biasValues[i] = Double.parseDouble(parsedXML.poll());
								parsedXML.poll(); //</biasvalue>
							}
						}	
						weightsList.add(weights);
						biasWeightsList.add(biasWeights);
						biasValuesList.add(biasValues);
					}
				}
			} else if(xml.equalsIgnoreCase("cl")) {
				double[] biasValues = new double[numCenterNeurons];
				double[] biasWeights = new double[numCenterNeurons];
				double[] weights = new double[numCenterNeurons];
				while(!parsedXML.peek().equals("/cl")) {
					System.out.println("Loading Center Layer");
					if(parsedXML.poll().equalsIgnoreCase("node")) {
						for(int i = 0; i < numInputNeurons; i++) {
							xml = parsedXML.poll();
							if(xml.equalsIgnoreCase("weight")) {
								weights[i] = Double.parseDouble(parsedXML.poll());
								parsedXML.poll(); //</weight>
							} else if(xml.equalsIgnoreCase("biasweight")) {
								biasWeights[i] = Double.parseDouble(parsedXML.poll());
								parsedXML.poll(); //</biasweight>
							} else if(xml.equalsIgnoreCase("biasvalue")) {
								biasValues[i] = Double.parseDouble(parsedXML.poll());
								parsedXML.poll(); //</biasvalue>
							}
						}	
						weightsList.add(weights);
						biasWeightsList.add(biasWeights);
						biasValuesList.add(biasValues);
					}
				}
			} else if(xml.equalsIgnoreCase("ol")) {
				double[] biasValues = new double[numCenterNeurons];
				double[] biasWeights = new double[numCenterNeurons];
				double[] weights = new double[numCenterNeurons];
				while(!parsedXML.peek().equals("/ol")) {
					System.out.println("Loading Output Layer");
					if(parsedXML.poll().equalsIgnoreCase("node")) {
						for(int i = 0; i < numInputNeurons; i++) {
							xml = parsedXML.poll();
							if(xml.equalsIgnoreCase("weight")) {
								weights[i] = Double.parseDouble(parsedXML.poll());
								parsedXML.poll(); //</weight>
							} else if(xml.equalsIgnoreCase("biasweight")) {
								biasWeights[i] = Double.parseDouble(parsedXML.poll());
								parsedXML.poll(); //</biasweight>
							} else if(xml.equalsIgnoreCase("biasvalue")) {
								biasValues[i] = Double.parseDouble(parsedXML.poll());
								parsedXML.poll(); //</biasvalue>
							}
						}	
						weightsList.add(weights);
						biasWeightsList.add(biasWeights);
						biasValuesList.add(biasValues);
					}
				}
			}	
		}
		return nn;
	}
	
	/**
	 * 
	 * @return
	 */
	public String toStringInput() {
		String s = "";
		for(int xmlFile = 0; xmlFile < train.size(); xmlFile++) {
			s += ("TRAINING SET ["+xmlFile+"]");
			for(int i = 0; i < train.get(xmlFile).getNumData(); i++) {
				s += "\n";
				for(int j = 0; j < train.get(xmlFile).getNumInputNodes(); j++) {
					if(j%3 == 0) {
						s += " ";
					}
					if(j%train.get(xmlFile).getInputWidth() == 0) {
						s += "\n";
					}/*
					if(train.get(xmlFile).getTrainingSet()[i][j] > 0.75) {
						s += "■ ";
					} else {
						s += "□ ";	
					}*/
					
					if(train.get(xmlFile).getTrainingSet()[i][j] == 1.0) {
						s += "O ";
					} else if(train.get(xmlFile).getTrainingSet()[i][j] == 0.5) {
						s += "X ";	
					} else {
						s+= "□ ";
					}
				}
			}
			s += "\n";
		}
		return s;
	}
	
	/**
	 * 
	 * @return
	 */
	public String toStringOutput() {
		String s = "";
		for(int xmlFile = 0; xmlFile < train.size(); xmlFile++) {
			s += ("TRAINING SET ["+xmlFile+"]");
			for(int i = 0; i < train.get(xmlFile).getNumData(); i++) {
				for(int j = 0; j < train.get(0).getNumInputNodes(); j++) {
					nn.setInput(j,train.get(xmlFile).getTrainingSet()[i][j]);
				}
				nn.feedForward();	// 出力を計算する: calculate the output layer
			    s += "\n";
				for(int j = 0; j < train.get(0).getNumOutputNodes(); j++) {
					if(j%train.get(xmlFile).getOutputWidth() == 0) {
						s += "\n";
					}
					/*if(nn.getOutput(j) > 0.75) {
						s += "■ ";
					} else {
						s += "□ ";	
					}*/
					
					if(nn.getOutput(j) > 0.80) {
						s += "O ";
					} else if(nn.getOutput(j) > 0.40 && nn.getOutput(j) < 0.60) {
						s += "X ";	
					} else {
						s+= "□ ";
					}
				}
			}
			s += "\n";
		}
		return s;
	}
	
	
}
