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
public class Hidden_Layers_Boolean {

    double input_weights[];
    double output_hiddenlayer;
    int input_hiddenlayer[];
    ArrayList<Double> input_values;
    int input_number;
    double deltaH;

    public Hidden_Layers_Boolean(int input_number,ArrayList<Double> input_values)
    {
        input_weights = new double[input_number];
        input_hiddenlayer = new int[input_number];
        this.input_values = input_values;
        this.input_number = input_number;

        int i;
        for(i=0;i<input_number;i++)
        {
            input_weights[i] = Random_Double_Generate();
            //System.out.println("input weight number "+i+"weight holo "+input_weights[i]);
        }
    }

    public void calculate_error_terms(double deltaK, double weight)
    {
        this.deltaH = 0.0;
        double temp1 = output_hiddenlayer * (1-output_hiddenlayer);
        this.deltaH = temp1 * deltaK*weight;
    }

    public void hidden_layer_sigmoid(int index)
    {
        this.output_hiddenlayer=0.0;
        int i,j;
        double net = 0.0;
        for(i=index,j=0;i<input_number+index;i++,j++)
        {
            Double temp = input_values.get(i);
            double weight_temp = input_weights[j];
            double temp2 = temp * weight_temp;
            net+= temp2;
        }

        this.output_hiddenlayer = 1.0/(1.0+(Math.exp((-1.0)*net)));
    }

    public void update_weight(double eta,int input_index)
    {
        int i,j;
        double temp1 = eta * deltaH;
        for(i=input_index,j=0;i<input_index+input_number;i++,j++)
        {
            double temp2 = input_values.get(i);
            double temp3 = temp1*temp2;
            input_weights[j] = input_weights[j]+temp3;
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



}
