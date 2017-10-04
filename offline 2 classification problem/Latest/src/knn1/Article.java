/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rahat
 */

package knn1;
import java.util.*;
import java.util.ArrayList;
public class Article {
    String Topic;
    int article_no;
    int size;
    Hashtable<Integer,Integer>word_frequency;

    Article(String topic_name,int article_no,Hashtable<Integer,Integer>word_frequency) {
        this.Topic = topic_name;
        this.article_no=article_no;
        this.word_frequency=word_frequency;
    }

    public void Set_up()
    {
    }

}
