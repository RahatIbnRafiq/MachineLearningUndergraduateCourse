/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package knn1;
import java.util.*;
import java.util.ArrayList;

/**
 *
 * @author Rahat
 */
public class Text {
    String Topic;
    int size;
    Hashtable<Integer,Integer>word_frequency;

    Text(String topic_name,Hashtable<Integer,Integer>word_frequency)
    {
        this.Topic = topic_name;
        this.word_frequency=word_frequency;
    }

}
