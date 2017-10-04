/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package knn1;
import java.util.*;

/**
 *
 * @author Rahat
 */
public class Text {

    String Topic;
    Hashtable<String,Integer> Word_table_text = new Hashtable<String,Integer>();
    Hashtable<Integer,Integer>word_frequency = new Hashtable<Integer,Integer>();
    Hashtable<String,Double> Probability_table = new Hashtable<String,Double>();
    Integer distinct_word_position;

    Text(String topic_name)
    {
        this.Topic = topic_name;
        this.distinct_word_position=0;
    }

    public void Distinct_word_position(Hashtable<String,Integer> Word_table)
    {
        Enumeration items2 = Word_table.keys();
         while(items2.hasMoreElements())
         {
             String word = items2.nextElement().toString();
             Integer word_id = Word_table.get(word);
             if(this.word_frequency.get(word_id)!=null)
             {
                 this.distinct_word_position+=this.word_frequency.get(word_id);
             }
             //temp.distinct_word_position+=temp.word_frequency.get(word_id);
         }
    }

    public void set_up_probabilities(String s,Integer vocabulary,Integer word_id)
    {
       Integer Nk = this.word_frequency.get(word_id);
       Double probability = (double)(Nk+1)/(double)(vocabulary+this.distinct_word_position);
       Probability_table.put(s, probability);
    }

}
