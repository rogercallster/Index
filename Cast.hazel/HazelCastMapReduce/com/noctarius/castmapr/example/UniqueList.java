package com.noctarius.castmapr.example;
//import java.io.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;

	//it takes the unique string and returns its index
	//get funtion calls with index to get the string to reducer
	public class UniqueList {

	    public static ArrayList<String> arrayList = new ArrayList<String>();
	      public static Hashtable<String,Integer> uniqueList = new Hashtable<String,Integer>();
	      int index=0;
	     
	      public static int addUnqiueList( String value )
	       {
	    	  
	    	  System.out.println("Value  = "+value);
	    	  
	          if((uniqueList.get(value))!=null)
	              return  uniqueList.get(value);
	          else
	             
	          {  
	              arrayList.size();
	              arrayList.add(value);
	              uniqueList.put(value,((arrayList.size()-1)));
	              //System.out.println("inside add unique"+ value + "Size of table is " + (arrayList.size()-1));
	              return ((arrayList.size()-1));
	          }
	    //      return index;
	       }

	      // getUnique get array list of all the entries of Attribute hash table and it pasrse through the list
	      
	      public static ArrayList<String>  getUnique(ArrayList<Integer> arrayList2 ) throws FileNotFoundException, UnsupportedEncodingException
	      {
	    	  
	    	  System.out.println("get unique called <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	    	  if(arrayList2.size()==0)
	    		  return null;
	    	  
	    	  for(int i=0 ; i <  arrayList2.size();i++)   
	    		  	    	 Buffer.buffer.add(arrayList.get(arrayList2.get(i)));
	    	 
//	        System.out.println("Ankur                        "+arrayList.get(arrayList2.get(i)));
	         
	              
	          
	    	 
	    	  
	    	    //PrintWriter writer = new PrintWriter("/home/hduser/Project/readfrm.txt", "UTF-8");
	           //   writer.println(SendString);
	            //  writer.close();
	              
	    	  return Buffer.buffer;
	    	  
	      }
	}

	


