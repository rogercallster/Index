package com.noctarius.castmapr.example;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

	class TCPServer
	{
	   
		   public static void main(String argv[]) throws Exception
		      {
			     String clientSentence;
		         String capitalizedSentence;
		         @SuppressWarnings("resource")
				ServerSocket welcomeSocket = new ServerSocket(7008);
		           
		         Manager.manager("ftp://ftp.freebsd.org");
		     	// naive way to see for Updates not at all intelligent.. I will change when get time!! 
                 
		         while(true) 
		         {
		                 
		            Socket connectionSocket = welcomeSocket.accept();
		            
		            //ideally TCP server should pass client connection to a search handler and listen to new connection
		            BufferedReader inFromClient =
		               new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		               clientSentence = inFromClient.readLine();
		            
		            String[] splitArray = clientSentence.split("\\s+");
		            //CLIENT INPUT : "filename"." " . $filename . " " ."perm"." ". $perm . " "."size"." " . $size . " "."time"." " .$time;
		            ArrayList<String> str = null;
		            for(int i =0;i<splitArray.length ;i++)
		            	{
		            	if(splitArray[i].equals("filename")&& !(splitArray[++i].equals("perm")))
		            	 {
		            		
		            		System.out.println("index list  "+  (splitArray[i])+ "  and attribute index "+AttributeObjects.NAME.getKey(splitArray[i]));
		            		 str =  UniqueList.getUnique(AttributeObjects.NAME.getKey(splitArray[i]));
                         }
		            	
		            	if(splitArray[i].equals("perm")&& !(splitArray[++i].equals("size")))
		            	 {
		            		
		            		System.out.println("index list "+AttributeObjects.PERM.getKey(splitArray[i]));
		            		 str =  UniqueList.getUnique(AttributeObjects.PERM.getKey(splitArray[i]));
                         //call mapper  method from here  from here        	
		            	 //System.out.println ("Result that is found "+ AttributeObjects.NAME.getKey(splitArray[i]));
		            	
		            	 }
		            	if(splitArray[i].equals("size")&& !(splitArray[++i].equals("time")))
		            	 {
		            		
		            		System.out.println("index list  "+  (splitArray[i])+ "  and attribute index "+AttributeObjects.SIZE.getKey(splitArray[i]));
		            		str =  UniqueList.getUnique(AttributeObjects.NAME.getKey(splitArray[i]));
                        }
		            	
		            	}//END OF ITTERATION OF CLIENT DATA		            	
		            	
	            		if(str.size()> 1)
	            		  {
	            			//String [] mr =new String[2];
		            		//mr[0]= "/home/hduser/Project/readfrm.txt";
		            	    //mr[1]="/home/hduser/Project/results";
		            		//MapReduce.mapreduce(mr);
	            			String st="";
	            			for(String send : str)
		            		{
	            				//outToClient.writeBytes(send);
		            		st=st.concat(send).concat(" ");
		            		
		            		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		            		outToClient.writeChars(send);
		            		
		            		
                             
		            		}
	            			System.out.println("send this to client  "+st);
	            			
	            			//outToClient.writeChars(st);
	            			//outToClient.close();
		            	
		            	}
	            		//else use MAP reduce for this purpose
	            		else {  //
	            			
	            		}
		            
		           System.out.println("Received: " + clientSentence);
		          //  capitalizedSentence = clientSentence.toUpperCase() ;
		            
		           
		            
		             
		         }//END OF WHILE LOOP
	         }//END OF MAIN METHOD
	      
	} //END OF SERVER
	
	
	
	
	
	
	
