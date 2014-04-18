package com.noctarius.castmapr.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkThread implements Runnable{
	 private Thread t;
	   private String threadName;

	   NetworkThread( String name){
	       threadName = name;
	       System.out.println("Creating " +  threadName );
	   }
	   public void run() {
	      System.out.println("Running " +  threadName );
	      String clientSentence = null;
          @SuppressWarnings("resource")
		ServerSocket welcomeSocket = null;
		try {
			welcomeSocket = new ServerSocket(7010);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    	  while(true) 
	             {
	               Socket connectionSocket=null;
	               
	             
	                 try {
						connectionSocket = welcomeSocket.accept();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                   
	               
	                BufferedReader inFromClient = null;
					try {
						inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                try {
						clientSentence = inFromClient.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                    String[] splitArray = clientSentence.split("\\s+");
	                    //CLIENT INPUT : "filename"." " . $filename . " " ."perm"." ". $perm . " "."size"." " . $size . " "."time"." " .$time;
	          
	                    
	                    try {
							ClientRequestWorkerThread.clientHandle(splitArray,connectionSocket);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                  
	                    
	               System.out.println("Received: " + clientSentence);
	              //  capitalizedSentence = clientSentence.toUpperCase() ;
	                
	               
	                
	                 
	             }//END OF WHILE LOOP
	           }//END OF MAIN METHOD


	   

	   public void start ()
	   {
	      System.out.println("Starting " +  threadName );
	      if (t == null)
	      {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }

	}
