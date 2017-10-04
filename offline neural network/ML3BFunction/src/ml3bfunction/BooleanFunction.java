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
public class BooleanFunction {
    ArrayList<String> training_data_list = new ArrayList<String>();
    //ArrayList<Integer> input_values = new ArrayList<Integer>();
    ///////////////
    ArrayList<Double> input_values = new ArrayList<Double>();
    //////////////
    ArrayList<Integer> output_values = new ArrayList<Integer>();
    ArrayList<Hidden_Layers_Boolean> hidden_layer_list = new ArrayList<Hidden_Layers_Boolean>();
    Output_Layer_Boolean output_layer;
    int hidden_layer_number;
    int iteration_number;
    double eta;
    int input_number;
    double accuracy = 0.0;
    double size;

    public BooleanFunction(ArrayList<String> training_data_list,int hidden_layer_number, int iteration_number, double eta)
    {
        this.size = (double)(training_data_list.size());
        this.eta = eta;
        this.hidden_layer_number=hidden_layer_number;
        this.iteration_number = iteration_number;
        this.training_data_list = training_data_list;
        parse_values();
        initialize_hidden_layers();
        initialize_output_layer();
        compute_boolean_values();
        //System.out.println(hidden_layer_list.size());
        //System.out.println(hidden_layer_list.get(0).input_weights[0]);

    }
    
    public void compute_boolean_values()
    {
        int i,j,k;
        for(i=0;i<iteration_number;i++)
        {
            for(j=0;j<input_values.size();j+=input_number)
            {
                //System.out.println(input_values.get(j)+" "+input_values.get(j+1)+" "+output_values.get(j/2));
                int actual_output = output_values.get(j/input_number);
                for(k=0;k<hidden_layer_number;k++)
                {
                    hidden_layer_list.get(k).hidden_layer_sigmoid(j);
                    //System.out.println("output from hidden layer "+(k+1)+" "+hidden_layer_list.get(k).output_hiddenlayer);
                    output_layer.input_from_hidden_layers[k] = hidden_layer_list.get(k).output_hiddenlayer;
                }

                output_layer.linear_output_layer();

                ///////////////////////calculating the error term
                output_layer.calculate_error_term(actual_output);

                for(k=0;k<hidden_layer_number;k++)
                {
                    hidden_layer_list.get(k).calculate_error_terms(output_layer.deltaK, output_layer.input_weights[k]);

                }

                /////////////////updating the weights
                output_layer.weight_update(eta);
                for(k=0;k<hidden_layer_number;k++)
                {
                    hidden_layer_list.get(k).update_weight(eta, j);
                    //hidden_layer_list.get(k).update_weight(eta, 0);

                }

            }
            //System.out.println("_____________");
        }

        double match =0.0;

        for(i=0;i<input_values.size();i+=input_number)
        {
            int actual_output = output_values.get(i/input_number);
            for(k=0;k<hidden_layer_number;k++)
            {
                hidden_layer_list.get(k).hidden_layer_sigmoid(i);
                output_layer.input_from_hidden_layers[k] = hidden_layer_list.get(k).output_hiddenlayer;
            }

           output_layer.linear_output_layer();
           if(output_layer.output ==  actual_output)
               match++;
        }

        this.accuracy = (match/size)*100;

    }

    public void initialize_output_layer()
    {
        output_layer = new Output_Layer_Boolean(hidden_layer_number);
    }

    public void initialize_hidden_layers()
    {
        int i;
        for(i=0;i<hidden_layer_number;i++)
        {
            System.out.println("hidden layer number "+(i+1));
            Hidden_Layers_Boolean temp = new Hidden_Layers_Boolean(input_number,input_values);

            hidden_layer_list.add(temp);

        }
    }
    public Double Random_Double_Generate()
    {
        //++random;
        Double d = Math.random();
        if(d>0.5)
        {
            d = 0.5-d;
        }
            return d/10;
    }

    public void parse_values()
    {
        int i=0;
        
        for(i=0;i<training_data_list.get(0).length();i++)
        {
            if(training_data_list.get(0).charAt(i)==',')
            {
                input_number++;
            }
        }
        for(i=0;i<training_data_list.size();i++)
        {
            StringTokenizer st = new StringTokenizer(training_data_list.get(i),",");
            int loop=0;
            while(st.hasMoreTokens())
            {
                loop++;
                if(loop<=input_number)
                {
                    String temp = st.nextElement().toString();
                    //input_values.add(Integer.parseInt(temp));
                    input_values.add(Double.parseDouble(temp));

                }
                else
                {
                    String temp = st.nextElement().toString();
                    output_values.add(Integer.parseInt(temp));
                }
            }
        }
    }



}
