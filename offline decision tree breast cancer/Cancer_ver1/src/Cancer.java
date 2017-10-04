/**
 * @(#)Cancer.java
 *
 * Cancer application
 *
 * @author 
 * @version 1.00 2011/10/18
 */
import java.lang.*;
import java.util.*;
import java.lang.Math;
import java.io.BufferedReader;
import java.io.FileReader;

//static int id;
public class Cancer {
	
	//public  double  [][] gain;
	
	Cancer()
	{
		//gain = new double[9][2];
	}
	
	public int  printResult(ArrayList<Node> node_list, int [] input)
	{
		int i = 0;
		while(node_list.get(i).answer==-1)
		{
			if(input[node_list.get(i).attribute_id] > node_list.get(i).splittedValue)
			{
				i = node_list.get(i).rightChildId;
			}
			else
			{
				i = node_list.get(i).leftChildId;
			}
		}
		if(node_list.get(i).answer == 1)
		{
			//System.out.println("YESSSSSSSSSSS");
			return 1;
		}
		else
		{
			//System.out.println("NOOOOOOOOOOOOO");
			return 0;
		}
		
		//System.out.println(node_list.get(i).attribute_id);
	}
	
	
	public int Maxgain(double [][]gain)
	{
		int i;
		int max = 0;
		for(i=0;i<9;i++)
		{
			if(gain[i][1]>gain[max][1])
			{
				max = i;
			}
		}
		
		return max;
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
	
	
	public double computeEntropy(Integer[][]attributeMatrix, int length)
	{
		int positive=0;
		int negative=0;
		double entropy;
		int i;
		for(i=0;i<length;i++)
		{
			if(attributeMatrix[i][9]==1)
				positive++;
			else
				negative++;			
		}
		entropy = entropyEqn(positive,negative,length)*3.322;
		return entropy;
	}
	
	public void ComputeBestAttribute(Integer[][]attributeMatrix,int length,double [][]gain)
	{
		double entropy;
		int negative1;
		int positive1;
		int negative2;
		int positive2;
		double ent1;
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
				for(i=0;i<length;i++)
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
			    avg = (ent1*(positive1+negative1)+ent2*(positive2+negative2))/length;
			    entropy = computeEntropy(attributeMatrix,length); 
				tempGain = entropy-avg;
				if((tempGain>=gain[j][1]) && (gain[j][1] > -1))
				{
					gain[j][0] = k;
					gain[j][1] = tempGain;
				}
			}	
		}
	}
    
    public static void main(String[] args) {
    	
    	ArrayList<Node> node_list = new ArrayList<Node>();
    	ArrayList<Integer> Explored_node = new ArrayList<Integer>();
    	
    	Divide dataSamples;
    	Integer[][]attributeMatrix;
    	Integer[][]testingMatrix;
    	double  [][] gain = new double [9][2];
    	Cancer c = new Cancer();
    	int [] input = new int [9];
    	
    	try
    	{
    	    dataSamples = new Divide("C:/data.txt");
    		int i,j,k,s;
			StringTokenizer st;
			attributeMatrix = new Integer [dataSamples.trainingExamples.length][10];
			testingMatrix = new Integer [dataSamples.numOfLines - dataSamples.trainingExampleLength][10];
			for(i=0;i<dataSamples.trainingExampleLength;i++)
			{
				st = new StringTokenizer(dataSamples.trainingExamples[i]);
				k=0;
				while (st.hasMoreTokens())
				{
					s = Integer.parseInt(st.nextToken());
					attributeMatrix[i][k]=s;
					k++;
				}
			}
			
			for(i=0;i<(dataSamples.numOfLines - dataSamples.trainingExampleLength);i++)
			{
				st = new StringTokenizer(dataSamples.trainingExamples[i]);
				k=0;
				while (st.hasMoreTokens())
				{
					s = Integer.parseInt(st.nextToken());
					testingMatrix[i][k]=s;
					k++;
				}
			}
			
			
			c.ComputeBestAttribute(attributeMatrix,dataSamples.trainingExampleLength,gain);
			int max = c.Maxgain(gain);
			Explored_node.add(max);
			Node root = new Node(max,gain,attributeMatrix);
			node_list.add(root); 
			
			//int i;
			for(i=0; i < node_list.size(); i++)
			{
				if(node_list.get(i).answer>-1)
					continue;
				System.out.println(i+" loop");
				int leftchildNo=0;int RightchildNo=0;
				if( node_list.get(i).splitted == false)
				{
					node_list.get(i).splitted = true;
					int p;
					for(j=0;j<node_list.get(i).Matrix.length;j++)
					{
						if(node_list.get(i).Matrix[j][node_list.get(i).attribute_id] <6)
						{
							leftchildNo++;
						}
						else
							RightchildNo++;
					}
				}
				Integer[][]LeftMatrix = new Integer[leftchildNo][10];
				Integer[][]RightMatrix = new Integer[RightchildNo][10];
				
				int m,n;
				int left=0;
				int  right = 0;
				for(m=0; m<node_list.get(i).Matrix.length; m++)
				{
					if( node_list.get(i).Matrix[m][node_list.get(i).attribute_id] < 6 )             // 5 er choto gulan 
					{
						for(n=0; n < 10; n++)
						{
							LeftMatrix[left][n] = node_list.get(i).Matrix[m][n] ;
						}
						
						left++;
					}
					else
					{
						for(n=0; n < 10; n++)
						{
							RightMatrix[right][n] = node_list.get(i).Matrix[m][n] ;
						}
						
						right++;
					}
				}
				
				double  [][] left_gain = new double [9][2];
				double  [][] right_gain = new double [9][2];
				//c.ComputeBestAttribute(attributeMatrix,dataSamples.trainingExampleLength,left_gain);
				for(j=0;j<9;j++)
				{
					if(Explored_node.contains(j))
					{
						left_gain[j][1] = -1;
						right_gain[j][1] = -1;
					}
				}
				
				int cur_node = i;
				
				c.ComputeBestAttribute(LeftMatrix, LeftMatrix.length,left_gain);
				c.ComputeBestAttribute(RightMatrix, RightMatrix.length,right_gain);
				int max1 = c.Maxgain(left_gain);
				int max2 = c.Maxgain(right_gain);
				Explored_node.add(max1);
				Explored_node.add(max2);
				Node left_child_node = new Node(max1,left_gain,LeftMatrix);
				Node right_child_node = new Node(max2,right_gain,RightMatrix);
				//left child er answer ki yes naki no naki nth
				int p,q;
				int pos1=0;
				int pos2=2;
				int neg1=0;
				int neg2=0;
				for(p=0; p<LeftMatrix.length;p++)
				{
					if(LeftMatrix[p][9]==0)
					{
						neg1++;
					}
					else
						pos1++;
						
				}
				if(neg1<20 && neg1<pos1)
				{
					left_child_node.answer = 1;
				}
				else if(pos1<20 && neg1 > pos1)
				{
					left_child_node.answer = 0;
				}
				//node_list.add(left_child_node);
				
				
				for(p=0; p<RightMatrix.length;p++)
				{
					if(RightMatrix[p][9]==0)
					{
						neg2++;
					}
					else
						pos2++;
						
				}
				if(neg2<20 && neg2<pos2)
				{
					right_child_node.answer = 1;
				}
				else if(pos2<20 && neg2>pos2)
				{
					right_child_node.answer = 0;
				}
				
				//node_list.add(right_child_node);
				
				System.out.println(neg1+" "+pos1+" "+neg2+" "+pos2);
				//node_list.get(i).leftChildId = left_child_node.nodeId-2;
				//node_list.get(i).rightChildId = right_child_node.nodeId-1;
				left_child_node.nodeId = node_list.size();
				right_child_node.nodeId = node_list.size()+1;
				node_list.get(i).leftChildId = left_child_node.nodeId;
				node_list.get(i).rightChildId =  right_child_node.nodeId;
				node_list.add(left_child_node);
				node_list.add(right_child_node);
				//System.out.println(left_child_node.nodeId+"  "+right_child_node.nodeId);	
			}
			
			for(i=0;i<node_list.size();i++)
			{
				System.out.println(node_list.get(i).splittedValue+" "+node_list.get(i).nodeId+" "+node_list.get(i).answer+" "+node_list.get(i).rightChildId+" "+node_list.get(i).leftChildId);
			}
			
		double expected_Negative=0;
		double expected_Positive = 0;
		double Test_positive = 0;
		double Test_negative = 0;
		double false_positive = 0;
		double false_negative = 0;
		double true_positive = 0;
		double true_negative = 0;
		int [] test = new int [testingMatrix.length];
		for(i=0;i<testingMatrix.length;i++)
		{
			if(testingMatrix[i][9] == 0)
			{
				expected_Negative++;
			}	
			else
			{
				expected_Positive++;
			}
			
			for(j=0;j<9;j++)
			{
				test[j] = testingMatrix[i][j]; 
			}
			if(c.printResult(node_list,test) == 0)
			{
				if(testingMatrix[i][9] == 1)
				{
					false_negative++;
				}
				else
				{
					true_positive++;
				}
				Test_negative++;
			}	
			else
			{
				if(testingMatrix[i][9] == 0)
				{
					false_positive++;
				}
				else
				{
					true_negative++;
				}
				Test_positive++;
			}	
		}
		double accuracy = (true_negative+true_positive)/(true_negative+true_positive+false_positive+false_negative);
		double precision = (true_positive) / (true_positive+false_positive);
		double recall = (true_positive) / (true_positive+false_negative);
		double x = recall* ((true_negative)/(true_negative+false_positive));
		double G_Near = Math.sqrt(x);
		//System.out.println(accuracy+"  "+precision+" "+recall+" "+G_Near);
		System.out.println("accuracy is "+accuracy);
		System.out.println("precision is "+precision);
		System.out.println("recall is "+recall);
		System.out.println("G_Near is "+G_Near);
		
    	} catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
}
