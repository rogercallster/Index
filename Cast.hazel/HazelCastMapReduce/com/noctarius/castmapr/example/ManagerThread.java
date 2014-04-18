package com.noctarius.castmapr.example;

public class ManagerThread implements Runnable  {
	
	 private Thread t;
	   private String threadName;

	   ManagerThread ( String name){
	       threadName = name;
	       System.out.println("Creating " +  threadName );
	   }
	   public void run() {
	      System.out.println("Running " +  threadName );

	      try {
	    	  System.out.println("Invoke Thread Manager");
			Manager.manager();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	     System.out.println("Thread " +  threadName + " exiting.");
	   }

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
