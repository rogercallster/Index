package com.noctarius.castmapr.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import stork.ad.Ad;
import stork.module.ftp.FTPSession;


/*
 * Manager tries to update the changes in the system
 * Now its a new thread with handle
 * 
 * */


public class Manager {
	
	public static void manager ()throws Exception{
		String homeDir = System.getProperty( "user.home" );
		
		new File(homeDir+"/repo").mkdir();
		
		String path = homeDir+"/repo/"+String.valueOf(System.currentTimeMillis());
		File file = new File(path);
		 
    	System.out.println("Starting Manager....");
		itterable("/home/bing/test/100",file);
             			
	}//end of manager 
		   
	/*
	 * Note : Working directory of this engine should be owned by/permission for this user to read/write form it apart form execute permission
	 * 
	 * */
		   public static void itterable(String Path,File file) throws ClientProtocolException, IOException, URISyntaxException
		   {

		         String StrOfCurrDir= Http_GET.itterator(Path).toString(); 
			   //adding it to file now
			   FileWriter fileWriter = new FileWriter(file,true);
			   if (file.exists())
			 	  
		        { 
				   BufferedWriter bufferFileWriter  = new BufferedWriter(fileWriter);
		           fileWriter.append(StrOfCurrDir);
		           bufferFileWriter.close();
		        }
			   
			   //write this string to file 
			   //System.out.println(Ad.marshal((s.list(Path).sync())).toString());
			 
			   ArrayList<JavaToJason> AL= new  ArrayList<JavaToJason>();
			   String[] str = StrOfCurrDir.split("\\s+");
			   JavaToJason tempObj=null;
			 //  System.out.println(str[0]);
			   int i;
			   int ArrayListIndex=0;
			   
			   for (i=0;i<str.length;i++)
			   {
				   //itterate to extract attribute out of string list
				   
				  // System.out.println((str[i]));
				   if(str[i].equals("["))
				   {
					   
					   
					   tempObj=new JavaToJason();
						 i++;
						  if(str[i].equals("time"))
						  {
						   i+=2;
						// System.out.println(Long.parseLong(str[i].replace(";","")));//Long.getLong
						   tempObj.time=Long.parseLong(str[i].replace(";",""));
						  //System.out.println(tempObj.time);
						   i++;
						  }
						  
						  if(str[i].equals("dir"))
						  {
						   i+=2;
						   tempObj.dir=Boolean.parseBoolean(str[i].replace(";",""));
						   //System.out.println(tempObj.dir );
						   i+=4;
						  
						  }
						  

						  if(str[i].equals("name"))
						  {
						   i+=2;
						   String st = str[i].replaceAll(";","");
						   st = st.replaceAll("\"","");
						   tempObj.name=st;
						   //System.out.println(tempObj.name );
						   i++;
						  
						  }
						 
						  if(str[i].equals("perm"))
						  {
						   i+=2;
						   String st = str[i].replaceAll(";","");
						   st = st.replaceAll("\"","");
						   tempObj.perm=st;
						   //System.out.println(tempObj.perm );
						   i++;
						  
						  }
						  if(str[i].equals("size"))
						  {
						   i+=2;
						// System.out.println(Long.parseLong(str[i].replace(";","")));//Long.getLong
						   tempObj.size=Long.parseLong(str[i].replace(";",""));
						 // System.out.println(tempObj.size);
						   i++;
						  }
						  
						  
						//  System.out.println( ArrayListIndex+" index and value ");			  
				   AL.add(ArrayListIndex,tempObj);
				  // System.out.println(AL.get(ArrayListIndex).name);
				   ArrayListIndex++;
				   
				   }
				   
			   
			   }
			   
			  //System.out.println(AL.get(0));
			   
			   for(JavaToJason O :  AL)
			   {	   
				   
				 if(O.name!=null)
				     //{System.out.println(O.name+" PATH " + Path+"/"+O.name);}
				   {
					 
				   int addr= UniqueList.addUnqiueList( Path+"/"+O.name );
				   AttributeObjects.TIME.putKey(String.valueOf(O.time),addr);
				   AttributeObjects.NAME.putKey(O.name,addr);
				   AttributeObjects.PERM.putKey(O.perm,addr);
				   AttributeObjects.SIZE.putKey(String.valueOf(O.size),addr);
				   
				   		if(O.dir && !(O.perm.equals( "700")))
				   		{
				   			
					   System.out.println("   "+O.dir+ " with permission  >>>>>" +O.name + " path  "+ Path);
					   //System.out.println( O.name + "path = " + Path + " O.dir = "+O.dir);   
				   itterable( Path+"/"+O.name,file);
			        
				       }
				    }
			    }//end of for loop ///
//			   	    
		   
		   }//end of itterable method
				 
			      
			
			   
		   
		   

		  

	}//end of class 


