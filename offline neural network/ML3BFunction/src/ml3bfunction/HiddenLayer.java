/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ml3bfunction;

/**
 *
 * @author Rahat
 */
public class HiddenLayer {
    double inputweight;
    double output=0.0;
    double deltaH;

    public HiddenLayer(double d)
    {
        //this.inputweight;
        this.inputweight = d;
    }

    public void sigmoid(double d1)
    {
        this.output = 0.0;
        double net = d1*inputweight;
        this.output = 1.0/(1.0+(Math.exp((-1.0)*net)));
    }

    public void calculate_error_term(double deltaK, double weight)
    {
        this.deltaH=0.0;
        this.deltaH = output *(1-output)* (deltaK*weight);
    }

    public void update_weight(double eta,double input_value)
    {
        double temp = eta * deltaH* input_value;
        inputweight = inputweight+temp;
    }

}
