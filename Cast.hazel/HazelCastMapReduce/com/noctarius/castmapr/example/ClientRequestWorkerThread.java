package com.noctarius.castmapr.example;
/*
 * This is stills not tuned for all cases corner cases not touched at all
 * no guarantee to work
 * 
 * 
 * */
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientRequestWorkerThread 
{
	
    //public static void clientHandle(){
	public static void clientHandle(String[] splitArray,
			Socket connectionSocket) throws Exception {
		// TODO Auto-generated method stub
		System.out.println (splitArray[0]);
        ArrayList<String> str = null;
        //CLIENT INPUT : "filename"." " . $filename . " " ."perm"." ". $perm . " "."size"." " . $size . " "."time"." " .$time;
         int TotalNumberOfAttr= 6;
        if (splitArray.length < TotalNumberOfAttr)
        {
        	//create a hazel cast instance else just return output to client
        
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
  	  {
			String st="";
			//DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter( connectionSocket.getOutputStream()));
			for(String send : str)
		{
				st=st.concat(send).concat(" ");
				
			//bw.write(send);
		}
			bw.flush();
			bw.write(st);
			st="";
			bw.flush();
			
			connectionSocket.close();
			System.out.println("printing buffer");
			//Buffer.printbuffer();
		//System.out.println("send this to client  "+st);
	}
		//else use MAP reduce for this purpose
	
        }//IF CONDITION OVER AFTER CHECKING NOT TO CREATE HAZEL CAST INSTANCE
		
    	
			else {  
				        			
                 
		         	
				MapReduceMain task =new MapReduceMain();
				
				String[] arg= new String[2];
				arg[0]="1";
				arg[1]="";
				task.TaskManager(arg,connectionSocket, splitArray);
			}//END OF ELSE 
				return;
			}

	    	
	    }
	

		
	

