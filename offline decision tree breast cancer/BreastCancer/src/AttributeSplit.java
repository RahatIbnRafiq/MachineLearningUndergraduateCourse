
public class AttributeSplit{
	
	Integer [][]AttributeMatrix;
	
	
	public void CheckThisValue(int i)
	{
		
	}
	
	AttributeSplit(Integer [][]attribute)
	{
		AttributeMatrix = new Integer[attribute.length][10];
		this.AttributeMatrix = attribute;
		int i;
		for(i=1;i<11;i++)
		{
			CheckThisValue(i);
		}
		//System.out.println(attribute.length);
	}
}