/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ml3bfunction;
import java.io.*;
import java.util.*;
import java.util.ArrayList;

/**
 *
 * @author Rahat
 */
public class Main {

    ArrayList<Double>input_values = new ArrayList<Double>();
    ArrayList<HiddenLayer> hidden_layers = new ArrayList<HiddenLayer>();
    ArrayList<Integer> output = new ArrayList<Integer>();
    ArrayList<String> training_data_list = new ArrayList<String>();
    OutputLayeer output_layer;
    double best_eta;
    int best_iteration;
    int best_hidden_layer_number;
    int iterations[] = {100,1000};
    double eta[] = {0.1,0.5,0.9};
    int hidden_layer_number[] = {3,5,7};


    public void input(String filename)
    {
        try
        {
            FileReader fr = new FileReader(filename);
            BufferedReader textReader = new BufferedReader(fr);
            String  s = new String();
            while((s=textReader.readLine())!=null)
            {
                training_data_list.add(s.toString());
            }
            //System.out.println(training_data_list.size());
            textReader.close();

        }catch(Exception e)
        {
            System.out.println("problem in loading the training data list! "+e);

        }
    }
    
    public void Real_Valued_Functions()
    { 
        input("data.dat");
        int i,j,k;
        double lowest_error=100.0;

        for(i=0;i<iterations.length;i++)
        {
            for(j=0;j<eta.length;j++)
            {
                for(k=0;k<hidden_layer_number.length;k++)
                {
                    RealValuedFunctions r = new RealValuedFunctions(training_data_list,hidden_layer_number[k],iterations[i],eta[j]);
                    System.out.println("eta = "+eta[j]);
                    System.out.println("iteration number = "+iterations[i]);
                    System.out.println("hidden_layer_number = "+hidden_layer_number[k]);
                    System.out.println(" ********************************************");
                    System.out.println(r.total_error);
                    System.out.println("__________________________________________________");
                    if(r.total_error< lowest_error)
                    {
                        lowest_error = r.total_error;
                        this.best_hidden_layer_number = hidden_layer_number[k];
                        this.best_eta = eta[j];
                        this.best_iteration = iterations[i];
                    }
                }
            }
            
            System.out.println(" best eta = "+best_eta);
            System.out.println("best iteration number = "+best_iteration);
            System.out.println("best hidden_layer_number = "+best_hidden_layer_number);
            System.out.println(" ********************************************");
            System.out.println(lowest_error);
            System.out.println("__________________________________________________");


        }
        RealValuedFunctions r = new RealValuedFunctions(training_data_list,6,1000,0.9);

    }


    public void Boolean_Function(String filename)
    {
        input(filename);

        int i,j,k;
        double best_accuracy = 0.0;

        for(i=0;i<iterations.length;i++)
        {
            for(j=0;j<eta.length;j++)
            {
                for(k=0;k<hidden_layer_number.length;k++)
                {
                    BooleanFunction b = new BooleanFunction(training_data_list,hidden_layer_number[k],iterations[i],eta[j]);
                    System.out.println("eta = "+eta[j]);
                    System.out.println("iteration number = "+iterations[i]);
                    System.out.println("hidden_layer_number = "+hidden_layer_number[k]);
                    System.out.println(" ********************************************");
                    System.out.println(b.accuracy);
                    System.out.println("__________________________________________________");
                    if(b.accuracy>best_accuracy)
                    {
                        this.best_eta = eta[j];
                        this.best_iteration = iterations[i];
                        this.best_hidden_layer_number = hidden_layer_number[k];
                        best_accuracy = b.accuracy;
                    }
                }
            }
        }

        System.out.println(" best eta = "+best_eta);
        System.out.println("best iteration number = "+best_iteration);
        System.out.println("best hidden_layer_number = "+best_hidden_layer_number);
        System.out.println(" ********************************************");
        System.out.println(best_accuracy);
        System.out.println("__________________________________________________");
        
       // System.out.println(b.accuracy);


    }

    public static void main(String[] args) {
        Main m = new Main();
        //m.Real_Valued_Functions();
        //m.Boolean_Function("XORFunc.dat");
       // m.Boolean_Function("AnotherFunc.dat");
       m.Boolean_Function("data_2.dat");
    }

}
