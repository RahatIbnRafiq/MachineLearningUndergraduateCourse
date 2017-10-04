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
public class RealValuedFunctions {

    ArrayList<String> training_data_list = new ArrayList<String>();
    ArrayList<HiddenLayer> hidden_layers_list = new ArrayList<HiddenLayer>();
    ArrayList<Double> input_values = new ArrayList<Double>();
    ArrayList<Double> output_values = new ArrayList<Double>();
    OutputLayeer output_layer;
    int hidden_layer_number;
    int iteration_number;
    double eta;
    double total_error=0.0;

    public RealValuedFunctions(ArrayList<String> training_data_list, int hidden_layer_number, int iteration_number, double eta) {
        this.training_data_list = training_data_list;
        this.hidden_layer_number = hidden_layer_number;
        this.eta = eta;
        this.iteration_number = iteration_number;
        initialize_hidden_layers();
        initialize_output_layer();
        input_output_parsing();
        compute_function_values();
        //System.out.println(this.training_data_list.size());
    }

    public void input_output_parsing()
    {
        int i;
        for(i=0;i<training_data_list.size();i++)
        {
            String temp = training_data_list.get(i);
            int index = temp.indexOf(",");
            Double d1 = Double.parseDouble(temp.substring(0, index));
            input_values.add(d1);
            double d2 = Double.parseDouble(temp.substring(index+1, temp.length()));
            output_values.add(d2);
        }
    }

    public void initialize_hidden_layers()
    {
        int i;
        for(i=0;i<hidden_layer_number;i++)
        {
            HiddenLayer temp = new HiddenLayer(Random_Double_Generate());
            hidden_layers_list.add(temp);
            //System.out.println(hidden_layers_list.get(i).inputweight);
        }
    }

    public void initialize_output_layer()
    {
       this.output_layer = new OutputLayeer(hidden_layer_number);
       int i;
       for(i=0;i<hidden_layer_number;i++)
       {
           this.output_layer.inputweight[i] = Random_Double_Generate();
           //System.out.println(hidden_layers_list.get(i).inputweight);
       }
    }


    public void compute_function_values()
    {
        int i,j,k;
        for(i=0;i<iteration_number;i++)
        {
            for(j=0;j<training_data_list.size();j++)
            {
                double actual_input = input_values.get(j);
                double actual_output = output_values.get(j);
                for(k=0;k<hidden_layers_list.size();k++)
                {
                    hidden_layers_list.get(k).sigmoid(actual_input);
                    output_layer.input[k] = hidden_layers_list.get(k).output;
                }
                output_layer.sigmoid();

                /////////////////////calculating the error term
                output_layer.calculate_error_term(actual_input);

                for(k=0;k<hidden_layers_list.size();k++)
                {
                    hidden_layers_list.get(k).calculate_error_term(output_layer.deltaK,output_layer.inputweight[k]);
                }
                ////////////////////////updating the weights

                output_layer.update_weight(eta);
                for(k=0;k<hidden_layer_number;k++)
                {
                    hidden_layers_list.get(k).update_weight(eta,actual_input);
                }
            }
        }
        double error = 0.0;
        for(i=0;i<training_data_list.size();i++)
        {
            double actual_input = input_values.get(i);
            double actual_output = output_values.get(i);
            for(k=0;k<hidden_layers_list.size();k++)
            {
                hidden_layers_list.get(k).sigmoid(actual_input);
                output_layer.input[k] = hidden_layers_list.get(k).output;
            }
            output_layer.sigmoid();
            //System.out.println(output_layer.output);
            double temp = ((output_layer.output-actual_output)*(output_layer.output-actual_output));
            error = error+temp;
        }
        this.total_error = error/ (2*training_data_list.size());
       // System.out.println("______________________________________");
        //System.out.println(total_error);
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

}
