/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ml3bfunction;

/**
 *
 * @author Rahat
 */
public class Output_Layer_Boolean {

    double input_weights[];
    double input_from_hidden_layers[];
    int output;
    int hidden_layer_number;
    double deltaK;

    public Output_Layer_Boolean(int hidden_layer_number)
    {
        this.hidden_layer_number = hidden_layer_number;
        input_weights = new double[hidden_layer_number];
        input_from_hidden_layers = new double[hidden_layer_number];
        int i;
        for(i=0;i<hidden_layer_number;i++)
        {
            input_weights[i] = Random_Double_Generate();
            //System.out.println("output layer weight "+input_weights[i]);
        }
    }

    public void weight_update(double eta)
    {
        double deltaW = eta * deltaK;
        int i;
        for(i=0;i<hidden_layer_number;i++)
        {
            double temp = this.input_from_hidden_layers[i]*deltaW;
            this.input_weights[i] = this.input_weights[i]+temp;
        }
    }
    
    public void calculate_error_term(int actual_output)
    {
        this.deltaK = 0.0;
        this.deltaK =   (actual_output - this.output);
    }

    public void linear_output_layer()
    {
        int i;
        double net =0.0;
        this.output = 0;
        for(i=0;i<hidden_layer_number;i++)
        {
            double temp1 = input_from_hidden_layers[i];
            double temp2 = input_weights[i];
            //System.out.println(temp1+temp2+" eikhane liner addition");
            double temp3 = temp1*temp2;
            net+= temp3;
        }


        if(net>0.0)
            this.output = 1;
        else
            this.output = 0;

        //System.out.println(net+" "+this.output);
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
