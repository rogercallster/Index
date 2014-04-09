package com.noctarius.castmapr.example;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

	class TCPServer
	{
	   
		   public static void main(String argv[]) throws Exception
		      {
			     String clientSentence;
		         @SuppressWarnings("resource")
				ServerSocket welcomeSocket = new ServerSocket(7005);
		           
		         Manager.manager();
		     	// naive way to see for Updates not at all intelligent.. I will change when get time!!  ---May be use thread if required to do this 
                 
		         while(true) 
		         {
		        	 Socket connectionSocket=null;
		        	 
		        	 try { 
		             connectionSocket = welcomeSocket.accept();
		        	      }catch (IOException ex) {
		        	    	  							System.out.println("Can't accept client connection. ");
		        	                              }
		        	 
		            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		        		clientSentence = inFromClient.readLine();
     		            String[] splitArray = clientSentence.split("\\s+");
	    	            //CLIENT INPUT : "filename"." " . $filename . " " ."perm"." ". $perm . " "."size"." " . $size . " "."time"." " .$time;
        	
		                
		                ClientRequestWorkerThread.clientHandle(splitArray,connectionSocket);
	            		
		                
		           System.out.println("Received: " + clientSentence);
		          //  capitalizedSentence = clientSentence.toUpperCase() ;
		            
		           
		            
		             
		         }//END OF WHILE LOOP
	         }//END OF MAIN METHOD
	      
	} //END OF SERVER
	
	
	
	
	
	
	
