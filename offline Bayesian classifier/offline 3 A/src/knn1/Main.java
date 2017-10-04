/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package knn1;

import java.io.*;
import java.util.*;
import java.util.ArrayList;
//import java.sql.*;

/**
 *
 * @author Rahat
 */

public class Main {

    public void compute_document_per_topic(ArrayList<Article> training_example,Hashtable<String,Integer> Documents_per_topic,Hashtable<String,Integer> Topic_table,Hashtable<String,Double> Documents_prior_probability)
    {
         Enumeration items = Topic_table.keys();
         while(items.hasMoreElements())
         {
             String s = items.nextElement().toString();
             int i;
             int article_no=0;
             double probability = 0.0;;
             for(i=0;i<training_example.size();i++)
             {
                 if(training_example.get(i).Topic.equals(s))
                 {
                     article_no++;
                 }
             }
             probability = (double)(article_no)/(double)(training_example.size());
             Documents_per_topic.put(s, article_no);
             Documents_prior_probability.put(s, probability);

         }
    }

    public void ConcatText(Hashtable<String,Integer> Topic_table,ArrayList<Article> training_example,ArrayList<Text> TEXTj,Hashtable<String,Integer> Documents_per_topic,Hashtable<String,Text> Textj_hash_list,Hashtable<Integer,String> Inverse_Word_table)
    {
        Enumeration item1 = Topic_table.keys();
        int j=0;
        while(item1.hasMoreElements())
        {
            String s = item1.nextElement().toString();
            int i;
            if(Documents_per_topic.get(s)>0)
            {
                j++;
                for(i=0;i<training_example.size();i++)
                {
                    if(training_example.get(i).Topic.equals(s))
                    {
                        if(Textj_hash_list.get(s)!=null)
                        {
                            Text t = Textj_hash_list.get(s);
                            Enumeration item2 = training_example.get(i).word_frequency.keys();
                            while(item2.hasMoreElements())
                            {
                                Integer word_id = Integer.parseInt(item2.nextElement().toString());
                                Integer frequency = training_example.get(i).word_frequency.get(word_id);
                                if(t.word_frequency.get(word_id)==null)
                                {
                                    t.word_frequency.put(word_id, frequency);
                                }
                                else
                                {
                                    Integer temp = t.word_frequency.get(word_id);
                                    temp = temp+ frequency;
                                    t.word_frequency.put(word_id, temp);
                                }
                            }
                        }
                        else
                        {
                            Text t = new Text (s);
                            t.word_frequency = training_example.get(i).word_frequency;
                            Textj_hash_list.put(s,t);
                        }

                    }
                }
            }
        }

        /*System.out.println(Textj_hash_list.get("interest").word_frequency.size());
        Enumeration item3 = Textj_hash_list.get("interest").word_frequency.keys();
        
        while(item3.hasMoreElements())
        {
            Integer s= Integer.parseInt(item3.nextElement().toString());
            String word = Inverse_Word_table.get(s);
            System.out.println(word +" ase total "+Textj_hash_list.get("interest").word_frequency.get(s));
        }*/
    }
    public void training_list_count(ArrayList<Article> example,Hashtable<String,Integer> Topic_table,Hashtable<String,Integer> Word_table,String filepath,Hashtable<Integer,Integer> Word_occurence_documents)
    {
        String delim = "[^A-Za-z]";
        try
        {
            FileReader fr = new FileReader(filepath);
            BufferedReader textReader = new BufferedReader(fr);
            String  s = new String();
            int article_no=0;
            int bal =0;


            while((s=textReader.readLine())!=null)
            {
                if(s.length()>0)
                {
                    //System.out.println(s+" s");
                    Hashtable<Integer,Integer>word_frequency = new Hashtable<Integer,Integer>();
                    if(Topic_table.get(s.toString())!=null)
                    {
                        String s2 = new String();
                        String s3 = new String();
                        while((s2=textReader.readLine())!=null)
                        {
                            if(s2.length()>0)
                            {
                                String [] tokens_string1 = s2.split(delim);
                                int i;
                                for(i=0;i<tokens_string1.length;i++)
                                {
                                   if(Word_table.get(tokens_string1[i])!=null)
                                   {

                                       if(word_frequency.get(Word_table.get(tokens_string1[i]))==null)
                                       {
                                           word_frequency.put(Word_table.get(tokens_string1[i]),1);
                                       }
                                        else
                                       {
                                           int x = word_frequency.get(Word_table.get(tokens_string1[i]))+1;
                                           word_frequency.put(Word_table.get(tokens_string1[i]),x);
                                       }
                                   }

                                }
                                if((s2.contains("Reuter")) ||(s2.contains("REUTER")))
                                {
                                    s3 = textReader.readLine();
                                    if(s3.length()==0)
                                    {
                                        bal++;
                                        //System.out.println(s2+" Sesh "+bal);

                                        break;
                                    }
                                    else
                                    {
                                        //System.out.println(s2);
                                        //System.out.println(s3);
                                        String [] tokens_string2 = s3.split(delim);
                                        for(i=0;i<tokens_string2.length;i++)
                                        {
                                           if(Word_table.get(tokens_string2[i])!=null)
                                           {

                                               if(word_frequency.get(Word_table.get(tokens_string2[i]))==null)
                                               {
                                                   word_frequency.put(Word_table.get(tokens_string2[i]),1);
                                               }
                                                else
                                               {
                                                   int x = word_frequency.get(Word_table.get(tokens_string2[i]))+1;
                                                   word_frequency.put(Word_table.get(tokens_string2[i]),x);
                                               }
                                           }

                                        }
                                    }
                                }
                                else
                                {
                                    //System.out.println(s2);
                                }
                            }
                        }

                        Article a = new Article(s, article_no,word_frequency);
                        Enumeration items = a.word_frequency.keys();
                        while(items.hasMoreElements())
                        {

                            Integer z = Integer.parseInt(items.nextElement().toString());
                            if(Word_occurence_documents.get(z)==null)
                            {
                                Word_occurence_documents.put(z, 1);
                            }
                            else
                            {
                               int temp = Word_occurence_documents.get(z);
                               temp+=1;
                               Word_occurence_documents.put(z, temp);
                            }
                        }

                         example.add(a);
                         article_no++;

                    }

                }
            }
        }catch(Exception e)
        {
            System.out.println(e);

        }
    }

    public void word_list_populate(Hashtable<String,Integer> Word_table)
    {
        String delim = "[^A-Za-z]";

        try
        {
            FileReader fr = new FileReader("training.data");
            BufferedReader textReader = new BufferedReader(fr);
            FileWriter f_stream = new FileWriter("word_list.txt");
            BufferedWriter out = new BufferedWriter(f_stream);
            String  s = new String();
            int word_key =0;
            int i;
            while((s=textReader.readLine())!=null)
            {   
                String [] tokens_string = s.split(delim);

                for(i=0;i<tokens_string.length;i++)
                {
                    if(Word_table.get(tokens_string[i])==null)
                    {
                        if(tokens_string[i].length()>0)
                        {
                            out.write(tokens_string[i]+"\n");
                            Word_table.put(tokens_string[i],word_key++);
                        }
                    }
                }
            }
            out.close();
            textReader.close();
        }catch(Exception e)
        {
            System.out.println("word list porte problem! "+e);

        }
    }

    public void topic_list_populate(Hashtable<String,Integer> Topic_table)
    {
             try
            {
                 FileReader fr = new FileReader("topics.data");
                 BufferedReader textReader = new BufferedReader(fr);
                 String  s = new String();
                 int a =1;
                 while((s=textReader.readLine())!=null)
                 {
                     Topic_table.put(s,a++);
                 }
             }catch(Exception e)
             {
                 System.out.println("topic fileporte problem!!! --"+e);
             }
    }
    public static void main(String[] args) {
     Hashtable<String,Integer> Topic_table = new Hashtable<String,Integer>();
     Hashtable<String,Integer> Word_table = new Hashtable<String,Integer>();
     Hashtable<Integer,String> Inverse_Word_table = new Hashtable<Integer,String>();
     Hashtable<Integer,Integer> Word_occurence_documents_training = new Hashtable<Integer,Integer>();
     Hashtable<Integer,Integer> Word_occurence_documents_testing = new Hashtable<Integer,Integer>();
     Hashtable<String,Integer> Documents_per_topic = new Hashtable<String,Integer>();
     Hashtable<String,Double> Documents_prior_probability = new Hashtable<String,Double>();
     Hashtable<String,Text> Textj_hash_list = new Hashtable<String,Text>();

     ArrayList<Article> training_example = new ArrayList<Article>();
     ArrayList<Article> testing_example = new ArrayList<Article>();
     ArrayList<Text> TEXTj = new ArrayList<Text>();
     ArrayList<Text> TEXTj2 = new ArrayList<Text>();
     Main m = new Main();
     int cor =0;
     int cor1=0;

     m.topic_list_populate(Topic_table);
     m.word_list_populate(Word_table);
     m.training_list_count(training_example, Topic_table,Word_table,"training.data",Word_occurence_documents_training);
     m.training_list_count(testing_example, Topic_table,Word_table,"test.data",Word_occurence_documents_testing);

     ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     Enumeration items = Word_table.keys();
     while(items.hasMoreElements())
     {
         String s = items.nextElement().toString();
         Integer n = Word_table.get(s);
         Inverse_Word_table.put(n, s);
     }
     m.compute_document_per_topic(training_example,Documents_per_topic,Topic_table,Documents_prior_probability);

     m.ConcatText(Topic_table, training_example, TEXTj,Documents_per_topic,Textj_hash_list,Inverse_Word_table);
     ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

     int i,j;

     Enumeration items1 = Textj_hash_list.keys();
     while(items1.hasMoreElements())
     {
         String topic = items1.nextElement().toString();
         Text t = Textj_hash_list.get(topic);
         TEXTj.add(t);

      }
     ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     for(i=0;i<TEXTj.size();i++)
     {
         Text temp = TEXTj.get(i);
         Enumeration items2 = Word_table.keys();
         while(items2.hasMoreElements())
         {
             String word = items2.nextElement().toString();
             Integer word_id = Word_table.get(word);
             if(temp.word_frequency.get(word_id)!=null)
             {
                 Double probability = (double)(temp.word_frequency.get(word_id)+1)/(double)(Word_table.size()+temp.word_frequency.size());
                 temp.Probability_table.put(word, probability);
             }
            else
            {
                 //Double probability = (double)(1.0)/(double)(Word_table.size()+temp.word_frequency.size());
                //temp.Probability_table.put(word, probability);
            }
         }
         temp.Distinct_word_position(Word_table);
         TEXTj2.add(temp);
     }

     ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
     for(i=0;i<testing_example.size();i++)
     {
         Article a = testing_example.get(i);
         double max=-1000000.0;
         String topic=null;
         for(j=1;j<TEXTj2.size();j++)
         {
             double total=0.0;
             Enumeration items3 = a.word_frequency.keys();
             while(items3.hasMoreElements())
             {
                 Integer word_id = Integer.parseInt(items3.nextElement().toString());
                 Integer frequency = a.word_frequency.get(word_id);
                 if(frequency!=null)
                 {
                     int k;
                     for(k=0;k<frequency;k++)
                     {
                         if(TEXTj2.get(j).Probability_table.get(Inverse_Word_table.get(word_id))!=null)
                            total+=Math.log(TEXTj2.get(j).Probability_table.get(Inverse_Word_table.get(word_id)));
                         else
                             total+=Math.log(1.0/(Word_table.size()+TEXTj2.get(j).distinct_word_position));
                     }
                 }
                
             }
             total = Math.log(Documents_prior_probability.get(TEXTj2.get(j).Topic))+total;
             //System.out.println("test: "+TEXTj2.get(j).Topic+"  asol topic:  "+testing_example.get(i).Topic+" ekn total : "+total+" max val: "+max);
             if(total > max)
             {
                 topic = TEXTj2.get(j).Topic;
                 //System.out.println("max er vitore "+topic+"  "+testing_example.get(i).Topic);
                 max = total;
             }
         }

         if(topic.equals(testing_example.get(i).Topic))
         {
             System.out.println("milse "+topic+"  "+testing_example.get(i).Topic+" "+i);
             cor1++;
         }
        else
        {
             System.out.println("mile nai "+topic+"  "+testing_example.get(i).Topic+" "+i);
             cor++;
        }
     }
     double accuracy = (double)(cor1)/(double)(testing_example.size());
     System.out.println(accuracy);

     double y = -.00001;
     ////////////////////////////////////////////////////////////////////////////////////////////////////////////////



  }

}
