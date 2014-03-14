package com.noctarius.castmapr.example;

//import java.io.IOException;
import java.util.ArrayList; 
import java.util.Hashtable;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class AttributeHash {

/**        
 *Key work is to create list of attribute  sheet and maintain a method to update it
 *added just a add method
 *
 *this java file contain put and get method to attribute sheet
 *
 * @author AnkurSaranHome
 */


   
                        //public  String Key;
                       
      public static Hashtable<String,ArrayList<Integer>> AttrHash = new Hashtable<String,ArrayList<Integer>>();
      public static ArrayList<Integer> IndexList ;

      public  ArrayList<Integer>  getKey(String Key)
      {           
                                 
                                   return AttrHash.get(Key);                                   
      }
   
      public  void putKey(String Key, int NewIndex) //Key is the attribute and value is index is Uniqlist index
          {
                   
                        IndexList=AttrHash.get(Key);//key is string 
                        
                                      
                        if(null==IndexList)
                            {
                                IndexList=new ArrayList<Integer>();   
                                IndexList.add(NewIndex);
                                AttrHash.put(Key, IndexList);
                                System.out.println();
                                
                                
                            }
                        else
                            {
                               
                                IndexList=AttrHash.get(Key);
                                IndexList.add(NewIndex);
                                                       
                            }
                       // System.out.println("done putting ?? .........");
                     //  System.out.println("Key is "+ AttrHash.get(Key).get(0));
              //   System.out.println("name of file is "+UniqueList.arrayList.get((AttrHash.get(Key).get(0))));
          }
                  
                                   

      //throw every thing for reducer thing
     
      //trying to create a stub
     
     
}
