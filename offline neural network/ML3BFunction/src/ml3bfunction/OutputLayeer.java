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
public class OutputLayeer {

    double inputweight[];
    double input[];
    double output=0.0;
    double deltaK;

    public OutputLayeer(int hiddenlayer_node_number) {
        this.inputweight = new double[hiddenlayer_node_number];
        this.input = new double[hiddenlayer_node_number];
    }

    public void sigmoid()
    {
        int i;
        double net=0.0;
        this.output = 0.0;
        for(i=0;i<input.length;i++)
        {
            net = net + (input[i]*inputweight[i]);
        }
        this.output = 1.0/(1.0+(Math.exp((-1.0)*net)));

    }

    public void calculate_error_term(double actual_value)
    {
        this.deltaK = 0.0;
        this.deltaK = (this.output) * (1-this.output) * (actual_value - this.output);
    }

    public void update_weight(double eta)
    {
        double deltaW = eta * deltaK;
        int i;
        for(i=0;i<input.length;i++)
        {
            double temp = deltaW*input[i];
            inputweight[i] = inputweight[i] +temp;
        }
    }



}
