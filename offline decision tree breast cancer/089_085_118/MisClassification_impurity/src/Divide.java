
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
public class Divide
{
	public String []trainingExamples;
	public String []verifyingExamples;
	public String []data;
	private FileReader fr ;
	private BufferedReader textReader;
	String filepath;
	int numOfLines;
	int trainingExampleLength;
	
	public void computeLines() throws IOException
	{
		fr = new FileReader(this.filepath);
		textReader = new BufferedReader(fr);
		numOfLines = 0;
		while(textReader.readLine()!=null)
		{
			numOfLines++;
		}
		textReader.close();
		fr.close();
	}
	
	public void populateExamples()throws IOException
	{
		fr = new FileReader(this.filepath);
		textReader = new BufferedReader(fr);
		int i;
		int j=0;
		int k=0;
		Random rand = new Random();
		//int tra;
		for(i=0; i<numOfLines; i++)
		{
			if(rand.nextInt()%2==0 && j < trainingExampleLength)
			{
				trainingExamples[j] = textReader.readLine();
				j++;
			}
			else if(k<(numOfLines - trainingExampleLength - 1) && (rand.nextInt()%2==1))
			{
				verifyingExamples[k] = textReader.readLine();
				k++;	
			}
			else if( verifyingExamples.length == k)
			{
				trainingExamples[j] = textReader.readLine();
				j++;
			}
			else{
					verifyingExamples[k] = textReader.readLine();
				k++;	
			}
		}
		textReader.close();
		fr.close();
		
		//System.out.println("K is " + k + " j is " + j+" "+numOfLines);
	}
	
	Divide(String filepath) throws IOException
	{
		
		this.filepath = filepath;
		computeLines();
		trainingExampleLength = (int)(0.8*numOfLines);
		trainingExamples = new String[trainingExampleLength];
		verifyingExamples = new String[numOfLines-trainingExampleLength];
		populateExamples();
	}
	
}