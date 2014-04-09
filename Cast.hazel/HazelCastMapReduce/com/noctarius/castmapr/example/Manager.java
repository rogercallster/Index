package com.noctarius.castmapr.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import stork.ad.Ad;
import stork.module.ftp.FTPSession;


/*
 * Manager tries to update the changes in the system
 * 
 * */


public class Manager {
	
	/*
	public static void printTree(FileTree ft) {
		printTree("", ft);
	} 
	public static void printTree(String prefix, FileTree ft) {
		System.out.println(prefix+"/"+ft.name);
		if (ft.files != null) for (FileTree f : ft.files)
			printTree(prefix+"/"+ft.name, f);
	}
	*/
	
	public static void manager ()throws Exception{
		
		/*String Path="/pub/FreeBSD/CERT/packages";
		    EndPoint e = new EndPoint(string);
 			FTPSession s= FTPSession.connect(e).sync();
 		*/
		
		itterable("/");
		
 			//itterable (s,Path);
             			//System.out.println (Ad.marshal((s.list("/pub/FreeBSD").sync()))); //take out of comment
	}//end of manager 
		   
		   public static void itterable(String Path) throws ClientProtocolException, IOException, URISyntaxException
		   {
			   
			   
			   String StrOfCurrDir= Http_GET.itterator(Path).toString();
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
				   		if(O.dir)
				   		{
					   //System.out.println("   "+O.dir);
					   //System.out.println( O.name + "path = " + Path + " O.dir = "+O.dir);   
				    itterable( Path+"/"+O.name);
			        
				       }
				    }
			    }//end of for loop ///
//			   	    
		   
		   }//end of itterable method
				 
			      
			
			   
		   
		   

		  

	}//end of class 


