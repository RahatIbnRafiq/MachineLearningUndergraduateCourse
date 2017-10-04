

public class Node{
	public  int nodeId;
	public boolean  splitted;
	public int splittedValue;
	public int rightChildId;
	public int leftChildId;
	public Integer[][]Matrix;
	double gain;
	int attribute_id;
	public  double  [][] gainMatrix;
	public int answer;
	
	//Node(int attribute_id, double gain, double value,Integer[][]attributeMatrix)
	Node(int attribute_id,double [][]gain ,Integer[][]attributeMatrix)
	{
		this.attribute_id = attribute_id;
		//this.nodeId = this.nodeId++;
		//this.gain = gain;
		//this.splittedValue = (int) value;
		//System.out.println(id+" "+this.gain);
		this.splitted = false;
		this.leftChildId = this.rightChildId = -1;
		this.Matrix = attributeMatrix;
		this.gainMatrix = new double[9][2];
		this.gainMatrix = gain;
		this.splittedValue = (int) gainMatrix[attribute_id][0];
		this.answer = -1;
		
	}
	
}