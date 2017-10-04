

class Node{
	int Attribute;
	int NodeId;
	int leftNodeId;
	int RightNodeId;
	double NodeEntropy;
	
	Node(int id,int att, double entropy)
	{
		this.NodeId = id;
		this.NodeEntropy = entropy;
		this.Attribute = att;
		System.out.println(this.NodeId+" "+this.Attribute+" "+this.NodeEntropy);
	}
	
}