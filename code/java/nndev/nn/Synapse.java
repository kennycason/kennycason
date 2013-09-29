package nn;


public class Synapse {
	
	private Neuron connectTo; // the neuron to connect to
	private double weight; // weight
	
	
	public Synapse() {
		connectTo = null; // this is the neuron to connect to
		weight = 0;
	}
	
	public Synapse(Neuron neuron) {
		connectTo = neuron; // this is the neuron to connect to
		weight = 0;
	}
	
	/**
	 * setWeight - sets the weight
	 * @Param double -  the value of the weight
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	/**
	 * getWeight - gets the weight
	 * @Return double
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * getConnected - get connected neuron
	 * @Return Neuron - neuron
	 */
	public Neuron getConnected() {
		return connectTo;
	}
	
	/**
	 * connect - connect to a neuron
	 * @Param Neuron -  the neuron to connect to
	 */
	public void connect(Neuron neuron) {
		this.connectTo = neuron;
	}
	
	/**
	 * unConnect - unConnect this axon from it's connecting neuron
	 * @Return Neuron - the neuron being deleted
	 */
	public Neuron unConnect() {
		Neuron temp = connectTo;
		connectTo = null;
		return temp;
	}
	

}
