/**
 * @(#)BreastCancer.java
 *
 * BreastCancer application
 *
 * @author 
 * @version 1.00 2011/10/15
 */
 
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
public class BreastCancer {
    
    public static void main(String[] args) {
    	
    	Divide data;
    	
    	try
    	{
    		 data = new Divide("C:/data.txt");
    		 Attributes atr = new Attributes(data.trainingExamples);
    		 Node n = new  Node(atr.nodeid,atr.att,atr.gain[atr.att][1]);
    	} catch(Exception e)
    	{
    		System.out.println(e);
    	}
    	
    	
    	
    }
}
