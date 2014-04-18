package com.noctarius.castmapr.example;




	class TCPServer
	{
	   
		
		 public static void main(String argv[]) throws Exception
         {
        ManagerThread MT=new ManagerThread("Starting Manager Thread ..... ");
        MT.start();
		NetworkThread NT = new NetworkThread("Network Thread ....");
		NT.start();
	      
         }
	} //END OF SERVER
	
	
	
	
	
	
	
