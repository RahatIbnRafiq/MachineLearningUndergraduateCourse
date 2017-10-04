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

    public int Check(String s)
    {
        if(s==null)
        {
            return 0;
        }
        else if(s.contains("Reuter"))
            return 0;
        else if (s.contains("REUTER"))
            return 0;

        return 1;
    }

    public void TF_IDF_Distance(ArrayList<Article> training_example,ArrayList<Article> testing_example,Hashtable<Integer,Integer> Word_occurence_documents_training,Hashtable<Integer,Integer> Word_occurence_documents_testing)
    {
        double tf_ifd_total;
        int correct_answer_tf_idf1=0;
        int correct_answer_tf_idf3=0;
        int correct_answer_tf_idf5=0;

        String Topic_tf_idf1 = "null";
        String Topic_tf_idf2 = "null";
        String Topic_tf_idf3 = "null";
        String Topic_tf_idf4 = "null";
        String Topic_tf_idf5 = "null";

        int i1,i2;
        int test_article_word_number;
        int training_article_word_number;
        double max_tf_idf1=-1;
        double max_tf_idf2=-1;
        double max_tf_idf3=-1;
        double max_tf_idf4=-1;
        double max_tf_idf5=-1;
        for(i1=0;i1<testing_example.size();i1++)
        {
            max_tf_idf1=-1;
            max_tf_idf2=-1;
            max_tf_idf3=-1;
            max_tf_idf4=-1;
            max_tf_idf5=-1;
            //Topic_tf_idf1=null;
            test_article_word_number=0;
            training_article_word_number=0;
            for(i2=0;i2<training_example.size();i2++)
            {
                tf_ifd_total=0;
                test_article_word_number=0;
                training_article_word_number=0;
                Enumeration items2 = training_example.get(i2).word_frequency.keys();
                while(items2.hasMoreElements())
                {
                    Integer s = Integer.parseInt(items2.nextElement().toString());
                    training_article_word_number+=training_example.get(i2).word_frequency.get(s);
                }


                Enumeration items1 = testing_example.get(i1).word_frequency.keys();
                while(items1.hasMoreElements())
                {
                    Integer s = Integer.parseInt(items1.nextElement().toString());
                    test_article_word_number+=testing_example.get(i1).word_frequency.get(s);
                }
                Enumeration items3 = testing_example.get(i1).word_frequency.keys();
                double test_tf,test_idf,training_tf,training_idf;
                while(items3.hasMoreElements())
                {
                    test_tf=test_idf=training_tf=training_idf=0.0;
                    Integer s = Integer.parseInt(items3.nextElement().toString());
                    if(training_example.get(i2).word_frequency.get(s)!=null)
                    {
                        test_tf =(double)testing_example.get(i1).word_frequency.get(s) / test_article_word_number;
                        test_idf = Math.log10(testing_example.size() / Word_occurence_documents_testing.get(s));
                        training_tf =(double) training_example.get(i2).word_frequency.get(s) / training_article_word_number;
                        training_idf = Math.log10((training_example.size() / Word_occurence_documents_training.get(s)));
                        tf_ifd_total+=(test_tf*test_idf)*(training_tf*training_idf);
                    }
                }
                if(max_tf_idf1<tf_ifd_total)
                {
                    max_tf_idf5 = max_tf_idf4;
                    Topic_tf_idf5=Topic_tf_idf4;

                    max_tf_idf4 = max_tf_idf3;
                    Topic_tf_idf4=Topic_tf_idf3;

                    max_tf_idf3 = max_tf_idf2;
                    Topic_tf_idf3=Topic_tf_idf2;

                    max_tf_idf2 = max_tf_idf1;
                    Topic_tf_idf2=Topic_tf_idf1;


                    max_tf_idf1 = tf_ifd_total;
                    Topic_tf_idf1 = training_example.get(i2).Topic;
                }
                else if(max_tf_idf2 < tf_ifd_total)
                {
                    max_tf_idf5 = max_tf_idf4;
                    Topic_tf_idf5=Topic_tf_idf4;

                    max_tf_idf4 = max_tf_idf3;
                    Topic_tf_idf4=Topic_tf_idf3;

                    max_tf_idf3 = max_tf_idf2;
                    Topic_tf_idf3=Topic_tf_idf2;

                    max_tf_idf2 = tf_ifd_total;
                    Topic_tf_idf2 = training_example.get(i2).Topic;
                }

                else if(max_tf_idf3 < tf_ifd_total)
                {
                    max_tf_idf5 = max_tf_idf4;
                    Topic_tf_idf5=Topic_tf_idf4;

                    max_tf_idf4 = max_tf_idf3;
                    Topic_tf_idf4=Topic_tf_idf3;

                    max_tf_idf3 = tf_ifd_total;
                    Topic_tf_idf3 = training_example.get(i2).Topic;
                }

                else if(max_tf_idf4 < tf_ifd_total)
                {
                    max_tf_idf5 = max_tf_idf4;
                    Topic_tf_idf5=Topic_tf_idf4;

                    max_tf_idf4 = tf_ifd_total;
                    Topic_tf_idf4 = training_example.get(i2).Topic;
                }
                else if(max_tf_idf5 < tf_ifd_total)
                {
                    max_tf_idf5 = tf_ifd_total;
                    Topic_tf_idf5 = training_example.get(i2).Topic;
                }
            }
            if(Topic_tf_idf1.equals(testing_example.get(i1).Topic))
            {
                System.out.println("k==1: "+Topic_tf_idf1+" "+testing_example.get(i1).Topic+" "+max_tf_idf1);
                correct_answer_tf_idf1++;
            }
            if(Topic_tf_idf3.equals(testing_example.get(i1).Topic))
            {
                System.out.println("k==3: "+Topic_tf_idf3+" "+testing_example.get(i1).Topic+" "+max_tf_idf3);
                correct_answer_tf_idf3++;
            }

            if(Topic_tf_idf5.equals(testing_example.get(i1).Topic))
            {
                System.out.println("k==5: "+Topic_tf_idf5+" "+testing_example.get(i1).Topic+" "+max_tf_idf5);
                correct_answer_tf_idf5++;
            }

        }

        double x1 = 100.0*correct_answer_tf_idf1;
        double x2 = 100.0*correct_answer_tf_idf3;
        double x3 = 100.0*correct_answer_tf_idf5;
        System.out.println("accuracy percentage (for k=1)is: "+x1/testing_example.size() );
        System.out.println("accuracy percentage (for k=3) is: "+x2/testing_example.size() );
        System.out.println("accuracy percentage (for k=5) is: "+x3/testing_example.size() );

    }

    public void Hamming_Eucledian_Distance(ArrayList<Article> training_example,ArrayList<Article> testing_example)
    {
        int i1,i2;
        int min_hamming_distance;
        int min_hamming_distance2;
        int min_hamming_distance3;
        int min_hamming_distance4;
        int min_hamming_distance5;
        String Topic_hamming_1="null";
        String Topic_hamming_2="null";
        String Topic_hamming_3="null";
        String Topic_hamming_4="null";
        String Topic_hamming_5="null";
        int correct_answer_hamming1 =0;
        int correct_answer_hamming3 =0;
        int correct_answer_hamming5 =0;

        double min_eucledian_distance;
        double min_eucledian_distance2;
        double min_eucledian_distance3;
        double min_eucledian_distance4;
        double min_eucledian_distance5;

        String Topic_eucledian1="null";
        String Topic_eucledian2="null";
        String Topic_eucledian3="null";
        String Topic_eucledian4="null";
        String Topic_eucledian5="null";

        int correct_answer_eucledian1 =0;
        int correct_answer_eucledian3 =0;
        int correct_answer_eucledian5 =0;
        for(i1=0;i1<testing_example.size();i1++)
        {
            min_eucledian_distance=100000;
            min_eucledian_distance2=100000;
            min_eucledian_distance3=100000;
            min_eucledian_distance4=100000;
            min_eucledian_distance5=100000;

            min_hamming_distance=100000;
            min_hamming_distance2 =100000;
            min_hamming_distance3 =100000;
            min_hamming_distance4 =100000;
            min_hamming_distance5 =100000;
            for(i2=0;i2<training_example.size();i2++)
            {
                double eucledian=0;
                int hamming=0;
                 Enumeration items1 = training_example.get(i2).word_frequency.keys();
                 while(items1.hasMoreElements())
                 {
                     Integer s = Integer.parseInt(items1.nextElement().toString());
                     if(testing_example.get(i1).word_frequency.get(s)==null)
                     {
                         eucledian+=Math.pow(training_example.get(i2).word_frequency.get(s), 2.0);
                         hamming++;
                     }
                     else
                     {
                         eucledian+=Math.pow(training_example.get(i2).word_frequency.get(s) - testing_example.get(i1).word_frequency.get(s), 2.0);
                     }
                         
                 }
                 Enumeration items2 = testing_example.get(i1).word_frequency.keys();
                 while(items2.hasMoreElements())
                 {

                     Integer s = Integer.parseInt(items2.nextElement().toString());
                     if(training_example.get(i2).word_frequency.get(s)==null)
                     {
                         eucledian+=Math.pow(testing_example.get(i1).word_frequency.get(s), 2.0);
                         hamming++;
                     }
                 }

                 if(Math.sqrt(eucledian)<=min_eucledian_distance)
                 {
                     min_eucledian_distance5=min_eucledian_distance4;
                     Topic_eucledian5=Topic_eucledian4;

                     min_eucledian_distance4=min_eucledian_distance3;
                     Topic_eucledian4=Topic_eucledian3;

                     min_eucledian_distance3=min_eucledian_distance2;
                     Topic_eucledian3=Topic_eucledian2;

                     min_eucledian_distance2=min_eucledian_distance;
                     Topic_eucledian2=Topic_eucledian1;

                     min_eucledian_distance = Math.sqrt(eucledian);
                     Topic_eucledian1 = training_example.get(i2).Topic;
                 }
                else if(Math.sqrt(eucledian) <= min_eucledian_distance2)
                 {
                     min_eucledian_distance5=min_eucledian_distance4;
                     Topic_eucledian5=Topic_eucledian4;

                     min_eucledian_distance4=min_eucledian_distance3;
                     Topic_eucledian4=Topic_eucledian3;

                     min_eucledian_distance3=min_eucledian_distance2;
                     Topic_eucledian3=Topic_eucledian2;

                     min_eucledian_distance2 = Math.sqrt(eucledian);
                     Topic_eucledian2 = training_example.get(i2).Topic;
                 }
                 else if(Math.sqrt(eucledian) <= min_eucledian_distance3)
                 {
                     min_eucledian_distance5=min_eucledian_distance4;
                     Topic_eucledian5=Topic_eucledian4;

                     min_eucledian_distance4=min_eucledian_distance3;
                     Topic_eucledian4=Topic_eucledian3;

                     min_eucledian_distance3 = Math.sqrt(eucledian);
                     Topic_eucledian3 = training_example.get(i2).Topic;
                 }
                 else if(Math.sqrt(eucledian) <= min_eucledian_distance4)
                 {
                     min_eucledian_distance5=min_eucledian_distance4;
                     Topic_eucledian5=Topic_eucledian4;

                     min_eucledian_distance4 = Math.sqrt(eucledian);
                     Topic_eucledian4 = training_example.get(i2).Topic;
                 }
                 else if(Math.sqrt(eucledian) <= min_eucledian_distance5)
                 {
                     min_eucledian_distance5 = Math.sqrt(eucledian);
                     Topic_eucledian5 = training_example.get(i2).Topic;
                 }

                 //////////////////////////////////////////////////////////////////////////////////////////////////////
                 if(hamming<=min_hamming_distance)
                 {
                     min_hamming_distance5 = min_hamming_distance4;
                     Topic_hamming_5=Topic_hamming_4;

                     min_hamming_distance4 = min_hamming_distance3;
                     Topic_hamming_4=Topic_hamming_3;

                     min_hamming_distance3 = min_hamming_distance2;
                     Topic_hamming_3=Topic_hamming_2;

                     min_hamming_distance2=min_hamming_distance;
                     Topic_hamming_2=Topic_hamming_1;

                     min_hamming_distance = hamming;
                     Topic_hamming_1 = training_example.get(i2).Topic;
                 }
                else if(hamming <= min_hamming_distance2)
                 {
                     min_hamming_distance5 = min_hamming_distance4;
                     Topic_hamming_5=Topic_hamming_4;

                     min_hamming_distance4 = min_hamming_distance3;
                     Topic_hamming_4=Topic_hamming_3;

                     min_hamming_distance3=min_hamming_distance2;
                     Topic_hamming_3=Topic_hamming_2;

                     min_hamming_distance2 = hamming;
                     Topic_hamming_2=training_example.get(i2).Topic;
                 }
                 else if(hamming <= min_hamming_distance3)
                 {
                     min_hamming_distance5 = min_hamming_distance4;
                     Topic_hamming_5=Topic_hamming_4;

                     min_hamming_distance4 = min_hamming_distance3;
                     Topic_hamming_4=Topic_hamming_3;

                     min_hamming_distance3 = hamming;
                     Topic_hamming_3=training_example.get(i2).Topic;
                 }

                 else if(hamming <= min_hamming_distance4)
                 {
                     min_hamming_distance5 = min_hamming_distance4;
                     Topic_hamming_5=Topic_hamming_4;

                     min_hamming_distance4 = hamming;
                     Topic_hamming_4=training_example.get(i2).Topic;
                 }
                 else if(hamming <= min_hamming_distance5)
                 {
                     min_hamming_distance5 = hamming;
                     Topic_hamming_5=training_example.get(i2).Topic;
                 }
                 //////////////////////////////////////////////////////////////////////////////////////////////////
            }

            //////////////////////////////////////eucledian////////////////////////////////////////////////////////////////

            if(Topic_eucledian1.equals(testing_example.get(i1).Topic))
            {
                System.out.println("Eucledian :"+min_eucledian_distance+" "+Topic_eucledian1+" "+testing_example.get(i1).Topic);
                correct_answer_eucledian1++;
            }
            if(Topic_eucledian3.equals(testing_example.get(i1).Topic))
            {
                System.out.println("Eucledian k=3 :"+min_eucledian_distance3+" "+Topic_eucledian3+" "+testing_example.get(i1).Topic);
                correct_answer_eucledian3++;
            }

            if(Topic_eucledian5.equals(testing_example.get(i1).Topic))
            {
                System.out.println("Eucledian k=5 :"+min_eucledian_distance5+" "+Topic_eucledian5+" "+testing_example.get(i1).Topic);
                correct_answer_eucledian5++;
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //////////////////hamming/////////////////////////////////////////////////////////////////////////////////////////////
            if(Topic_hamming_1.equals(testing_example.get(i1).Topic))
            {
                System.out.println("Hamming :"+ min_hamming_distance+" "+Topic_hamming_1+" "+testing_example.get(i1).Topic);
                correct_answer_hamming1++;
            }
            if(Topic_hamming_3.equals(testing_example.get(i1).Topic))
            {
                System.out.println("Hamming k=3:"+ min_hamming_distance3+" "+Topic_hamming_3+" "+testing_example.get(i1).Topic);
                correct_answer_hamming3++;
            }

            if(Topic_hamming_5.equals(testing_example.get(i1).Topic))
            {
                System.out.println("Hamming k=5:"+ min_hamming_distance5+" "+Topic_hamming_5+" "+testing_example.get(i1).Topic);
                correct_answer_hamming5++;
            }
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
        double x1 = 100.0*correct_answer_hamming1;
        double y1 = 100.0*correct_answer_hamming3;
        double z1 = 100.0*correct_answer_hamming5;
        System.out.println("accuracy percentage  for k=1 Hamming distance: "+x1/testing_example.size() );
        System.out.println("accuracy percentage  for k=3 Hamming distance: "+y1/testing_example.size() );
        System.out.println("accuracy percentage  for k=5 Hamming distance: "+z1/testing_example.size() );
        System.out.println("______________________________________________");
        //System.out.println(correct_answer_hamming1+"  "+correct_answer_hamming3+"  "+correct_answer_hamming5);

        double x2 = 100.0*correct_answer_eucledian1;
        double y2 = 100.0*correct_answer_eucledian3;
        double z2 = 100.0*correct_answer_eucledian5;
        System.out.println("accuracy percentage for k=1 eucledian distance: "+x2/testing_example.size() );
        System.out.println("accuracy percentage for k=3 eucledian distance: "+y2/testing_example.size() );
        System.out.println("accuracy percentage for k=5 eucledian distance: "+z2/testing_example.size() );
        //System.out.println(correct_answer_eucledian1+"  "+correct_answer_eucledian3+"  "+correct_answer_eucledian5);
        System.out.println("______________________________________________");
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
                 int key =0;
                 while((s=textReader.readLine())!=null)
                 {
                     Topic_table.put(s,key++);
                 }
             }catch(Exception e)
             {
                 System.out.println("topic fileporte problem!!! --"+e);
             }
    }
    public static void main(String[] args) {
     Hashtable<String,Integer> Topic_table = new Hashtable<String,Integer>();
     Hashtable<String,Integer> Word_table = new Hashtable<String,Integer>();
     Hashtable<Integer,Integer> Word_occurence_documents_training = new Hashtable<Integer,Integer>();
     Hashtable<Integer,Integer> Word_occurence_documents_testing = new Hashtable<Integer,Integer>();
     ArrayList<Article> training_example = new ArrayList<Article>();
     ArrayList<Article> testing_example = new ArrayList<Article>();
     Main m = new Main();

     m.topic_list_populate(Topic_table);
     m.word_list_populate(Word_table);
     m.training_list_count(training_example, Topic_table,Word_table,"training.data",Word_occurence_documents_training);
    // System.out.println(training_example.size());
     m.training_list_count(testing_example, Topic_table,Word_table,"test.data",Word_occurence_documents_testing);
     //System.out.println(testing_example.size());
     m.Hamming_Eucledian_Distance(training_example, testing_example);

     m.TF_IDF_Distance(training_example, testing_example, Word_occurence_documents_training, Word_occurence_documents_testing);
     
    }

}
