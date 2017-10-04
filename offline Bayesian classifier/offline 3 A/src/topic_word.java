/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rahat
 */
public class topic_word {

    String topic_name;
    Integer [] word_count;

    public topic_word(String topic, int size) {
        this.topic_name= topic;
        this.word_count = new Integer[size];
    }

    public void print()
    {
        System.out.println("YES!");
    }

}
