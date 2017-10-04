
import java.lang.*;
import java.util.*;
import java.lang.Math;
public class Attributes{
	String [] data;
	private double  entropy;
	//private double [] gain;
	public double [][] gain;
	int  att;
	/*int [] Clump_Thickness;
	int [] Cell_Size;
	int[]Marginal_Adhesion,
	int[]Single_Epithelial_Cell_Size;
	int[]Uniformity_of_Cell_Shape;
	int[]Bare_Nuclei;
	int[]Bland_Chromatin;
	int[]Normal_Nucleoli;
	int[]Mitoses; */
	Integer[][]attributeMatrix;
	static int nodeid;
	public void populateAttributes()
	{
		int i,j,k,s;
		StringTokenizer st;
		for(i=0;i<data.length;i++)
		{
			st = new StringTokenizer(data[i]);
			k=0;
			while (st.hasMoreTokens())
			{
				s = Integer.parseInt(st.nextToken());
				attributeMatrix[i][k]=s;
				k++;
			}
		}
	}
	
	
	public double entropyEqn(int pos, int neg, int total)
	{
		double ent;
		if(pos>0&& neg>0)
		{
			double d1 = Math.log10(neg)-Math.log10(total);
			double d2 = Math.log10(pos)-Math.log10(total);
		    ent = -(pos*d2+neg*d1)/total;
			
		}
		else if(pos == neg)
		{
			ent = 1;
		}
		else
		{
			ent = 0;
		}
		return ent;
	}
	
	public void computeEntropy()
	{
		int positive=0;
		int negative=0;
		int i;
		for(i=0;i<data.length;i++)
		{
			if(attributeMatrix[i][9]==1)
				positive++;
			else
				negative++;			
		}
		entropy = entropyEqn(positive,negative,data.length)*3.322;
	}
	
	public int  SelectBestAttribute()
	{
		//double temp = 0;
		int temp = 0;
		double max = 0;
		int i;
		for(i=0;i<9;i++)
		{
			if(gain[i][1]>max)
			{
				temp = i;
				max = gain[(int)temp][1];
			}
		}
		//gain-[temp][1] = -1;
		return temp;
	}
	
	public void ComputeBestAttribute()
	{
		int negative1;
		int positive1;
		int negative2;
		int positive2;
		double ent1 ;
		double ent2;
		double avg;
		double tempGain;
		int i;
		int j;
		int k;
		for(j=0;j<9;j++)
		{
			for(k=1;k<11;k++)
			{
				ent1 =0; ent2 =0; avg = 0;
				positive1 =0;positive2=0;negative1=0;negative2=0;
				for(i=0;i<data.length;i++)
				{
					if(attributeMatrix[i][9]==1 && attributeMatrix[i][j]<=k)
					{
						positive1++;
					}
					else if(attributeMatrix[i][9]==1 && attributeMatrix[i][j]>k)
					{
						positive2++;
					}
					else if(attributeMatrix[i][9]==0 && attributeMatrix[i][j]<=k)
					{
						negative1++;
					}
					else
						negative2++;
				}
			    ent1 = entropyEqn(positive1,negative1,positive1+negative1)*3.322;
			    ent2 = entropyEqn(positive2,negative2,positive2+negative2)*3.322;
			    avg = (ent1*(positive1+negative1)+ent2*(positive2+negative2))/data.length;
				tempGain = entropy-avg;
				if(tempGain>gain[j][1])
				{
					gain[j][0] = k;
					gain[j][1] = tempGain;
				}
				//System.out.println(gain[j]);
				//System.out.println(positive1+" "+positive2+" "+negative1+" "+negative2);
			}	
		}
		
		for(i=0;i<9;i++)
		{
			//System.out.println(gain[i][0]+"  "+gain[i][1]+"  "+i);
		}
	}
	Attributes(String[]trainingExamples)
	{
		int size = trainingExamples.length;
		data = new String[size];
		data = trainingExamples;
		attributeMatrix = new Integer [trainingExamples.length][10];
		gain = new double [9][2];
		populateAttributes();
		computeEntropy();
		int i;
		ComputeBestAttribute();
	    att = SelectBestAttribute();
		//System.out.println(att+"  "+gain[att][1]);
		//Node n = new  Node(nodeid, att, gain[att][1]);
		//gain[att][1] = -1;//this node has been selected and will not be considered again in the later levels.
		//AttributeSplit as = new AttributeSplit(attributeMatrix);
		
	}
}