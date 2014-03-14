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
	          if((uniqueList.get(value))!=null)
	              return  uniqueList.get(value);
	          else
	             
	          {  
	              arrayList.size();
	              arrayList.add(value);
	              uniqueList.put(value,((arrayList.size()-1)));
	              return ((arrayList.size()-1));
	          }
	    //      return index;
	       }

	      // getUnique get array list of all the entries of Attribute hash table and it pasrse through the list
	      
	      public static String  getUnique(ArrayList<Integer> arrayList2 ) throws FileNotFoundException, UnsupportedEncodingException
	      {
	    	  
	    	  if(arrayList2.size()==0)
	    		  return null;
	    	  String SendString="";
	    	  for(int i : arrayList2)
	    	  { 
	          
	    		  System.out.println("NULL");
	    	 SendString=SendString.concat(arrayList.get(i));
	    	 SendString=SendString.concat(" ");
	              System.out.println(arrayList.get(i ));
	              
	          
	    	  }
	    	    PrintWriter writer = new PrintWriter("/home/hduser/Project/readfrm.txt", "UTF-8");
	              writer.println(SendString);
	              writer.close();
	              
	    	  return SendString;
	    	  
	      }
	}

	


