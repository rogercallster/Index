package com.noctarius.castmapr.example;

import java.io.*;
import java.net.*;

	class TCPServer
	{
	   
		   public static void main(String argv[]) throws Exception
		      {
			   
			   
		         String clientSentence;
		         String capitalizedSentence;
		         @SuppressWarnings("resource")
				ServerSocket welcomeSocket = new ServerSocket(7006);
		         Manager.manager("ftp://storkcloud.org");
                 int counter  = 0;
               //busy waiting 
		         while(true) 
		         {
		        	 counter ++;
		        	// naive way to see for Updates not at all intelligent.. I will change when get time!!
                      
		        	 if(counter == 100) 		        	 {
		        		 //this is to call for update in manager
		        		 Manager.manager("ftp://storkcloud.org");
		        		 counter =0;
		         }
		            Socket connectionSocket = welcomeSocket.accept();
		            BufferedReader inFromClient =
		               new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		            clientSentence = inFromClient.readLine();
		            
		            String[] splitArray = clientSentence.split("\\s+");
		            //"filename"." " . $filename . " " ."perm"." ". $perm . " "."size"." " . $size . " "."time"." " .$time;
		            String str="";
		            for(int i =0;i<splitArray.length ;i++)
		            	{
		            	if(splitArray[i].equals("filename"))
		            	 {
		            		i++;
		            		 str =  UniqueList.getUnique(AttributeObjects.NAME.getKey(splitArray[i]));
                          //call mapper  method from here  from here        	
		            	 //System.out.println ("Result that is found "+ AttributeObjects.NAME.getKey(splitArray[i]));
		            	
		            	 }
		            	
	            		if(str.length()<0)
	            		  {
	            			String [] mr =new String[2];
		            		mr[0]= "/home/hduser/Project/readfrm.txt";
		            	    mr[1]="/home/hduser/Project/results";
		            		//MapReduce.mapreduce(mr);
		            		outToClient.writeBytes(str);

	            		  }
		            	
		            	}
		            
		           System.out.println("Received: " + clientSentence);
		          //  capitalizedSentence = clientSentence.toUpperCase() ;
		            
		           // outToClient.writeBytes(capitalizedSentence);
		            
		             
		         }
	         }
	      
	} 
	
	
	
	
	
	
	
