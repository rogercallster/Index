
package com.noctarius.castmapr.example;

import com.hazelcast.core.ExecutionCallback;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

/**
 * A basic and simple MapReduce demo application for the Hazelcast MR framework.
 * The example Lorem Ipsum texts were created by this awesome generator: http://www.lipsum.com/
 *
 * For any further questions feel free
 * - to ask at the mailing list: https://groups.google.com/forum/#!forum/hazelcast
 * - read the Javadoc: http://hazelcast.org/docs/latest/javadoc/
 * - read the documentation this demo is for: http://bit.ly/1nQSxhH
 */
public class MapReduceMain {

    //private static final String[] DATA_RESOURCES_TO_LOAD = {"text1.txt", "text2.txt", "text3.txt"};

    public static Boolean TaskManager(String[] args,Socket connectionSocket,String[] splitArray) throws Exception {


    	int nodesCount = Integer.parseInt(args[0]);
    	HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

        try {
            fillMapWithData(hazelcastInstance,splitArray);

            Map<String, Long> countsPerWord = mapReduce(hazelcastInstance);
            System.out.println("Counts per words over " + " Search set ");
           
            //we have counter here and we can choose the result with max count from here
            
            //
            Long max= (long) 0;
            
            for (Map.Entry<String, Long> entry : countsPerWord.entrySet()) {
            	if (max<entry.getValue()) max=entry.getValue();
               // System.out.println("\tWord '" + entry.getKey() + "' occured " + entry.getValue() + " times");
            }
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter( connectionSocket.getOutputStream()));
            String st ="";
            for (Map.Entry<String, Long> entry : countsPerWord.entrySet()) {
            	if( entry.getValue()==max )
            		st=st.concat(entry.getKey()).concat(" ");
                System.out.println("\tWord '" + entry.getKey() + "' occured " + entry.getValue() + " times");
            }
            

			bw.flush();
			bw.write(st);
			st="";
			bw.flush();
			
			connectionSocket.close();
			System.out.println("printing buffer");
			
			
            long wordCount = mapReduceCollate(hazelcastInstance);
            System.out.println("All content sums up to " + wordCount + " words.");

        } finally {
            Hazelcast.shutdownAll();
        }
		return null;
    }

    private static Map<String, Long> mapReduce(HazelcastInstance hazelcastInstance)
            throws Exception {

        // Retrieving the JobTracker by name
        JobTracker jobTracker = hazelcastInstance.getJobTracker("default");

        // Creating the KeyValueSource for a Hazelcast IMap
        IMap<String, String> map = hazelcastInstance.getMap("articles");
        KeyValueSource<String, String> source = KeyValueSource.fromMap(map);

        Job<String, String> job = jobTracker.newJob(source);

        // Creating a new Job
        ICompletableFuture<Map<String, Long>> future = job // returned future
                .mapper(new TokenizerMapper())             // adding a mapper
                .combiner(new WordCountCombinerFactory())  // adding a combiner through the factory
                .reducer(new WordCountReducerFactory())    // adding a reducer through the factory
                .submit();                                 // submit the task

        // Attach a callback listener
        future.andThen(buildCallback());

        // Wait and retrieve the result
        return future.get();
    }

    private static long mapReduceCollate(HazelcastInstance hazelcastInstance)
            throws Exception {

        // Retrieving the JobTracker by name
        JobTracker jobTracker = hazelcastInstance.getJobTracker("default");

        // Creating the KeyValueSource for a Hazelcast IMap
        IMap<String, String> map = hazelcastInstance.getMap("articles");
        KeyValueSource<String, String> source = KeyValueSource.fromMap(map);

        // Creating a new Job
        Job<String, String> job = jobTracker.newJob(source);

        ICompletableFuture<Long> future = job // returned future
                .mapper(new TokenizerMapper())             // adding a mapper
                .combiner(new WordCountCombinerFactory())  // adding a combiner through the factory
                .reducer(new WordCountReducerFactory())    // adding a reducer through the factory
                .submit(new WordCountCollator());          // submit the task and supply a collator

        // Wait and retrieve the result
        return future.get();
    }

    private static ExecutionCallback<Map<String, Long>> buildCallback() {
        return new ExecutionCallback<Map<String, Long>>() {
            public void onResponse(Map<String, Long> stringLongMap) {
                System.out.println("Calculation finished! :)");
            }

            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }
        };
    }

    private static void fillMapWithData(HazelcastInstance hazelcastInstance,String[] splitArray)
            throws Exception {

        IMap<String, String> map = hazelcastInstance.getMap("articles");
        int var=0;
        StringBuilder sb = new StringBuilder();
      for(int i =0;i<splitArray.length ;i++)
  	{
      	ArrayList<String> str =new ArrayList<String>();
      	if(splitArray[i].equals("filename")&& !(splitArray[++i].equals("perm")))
      	 {
  		
  	    	System.out.println("index list  "+  (splitArray[i])+ "  and attribute index "+AttributeObjects.NAME.getKey(splitArray[i]));
  		     str =  UniqueList.getUnique(AttributeObjects.NAME.getKey(splitArray[i]));
			var=i;
  		    for  (String term : str){System.out.println(str);  sb.append(term).append("\n");}//dictionary.put(term,1);
  		     map.put(splitArray[i-1], sb.toString());
            }
  	
  	      if(splitArray[i].equals("perm")&& !(splitArray[++i].equals("size")))
  	      {
  		
  	      	System.out.println("index list "+AttributeObjects.PERM.getKey(splitArray[i]));
  	
  	      	str =  UniqueList.getUnique(AttributeObjects.PERM.getKey(splitArray[i]));
			var=i;
  		    for  (String term : str){System.out.println(splitArray[var]);  sb.append(term).append("\n");}//dictionary.put(term,1);
 		     map.put(splitArray[i-1], sb.toString());
  	       	 }
  	       if(splitArray[i].equals("size")&& !(splitArray[++i].equals("time")))
  	        {
  		 	    System.out.println("index list  "+  (splitArray[i])+ "  and attribute index "+AttributeObjects.SIZE.getKey(splitArray[i]));
  	        	str =  UniqueList.getUnique(AttributeObjects.NAME.getKey(splitArray[i]));
			var=i;
  		    for  (String term : str) {System.out.println(splitArray[var]);  sb.append(term).append("\n");}//dictionary.put(term,1);
 		     map.put(splitArray[i-1], sb.toString());
                }
  	       if(splitArray[i].equals("time")&& splitArray.length!=++i)
 	        {
 		 	   // System.out.println("index list  "+  (splitArray[i])+ "  and attribute index "+AttributeObjects.SIZE.getKey(splitArray[i]));
 	        	str =  UniqueList.getUnique(AttributeObjects.TIME.getKey(splitArray[i]));
			var=i;
  		    for  (String term : str){System.out.println(splitArray[var]);  sb.append(term).append("\n");}//dictionary.put(term,1);
 		        map.put(splitArray[i-1], sb.toString());//dictionary.put(term,1);
               }
                     	
  	
       	}//END OF ITTERATION OF CLIENT DATA
      
      

        
        /*for (String file : DATA_RESOURCES_TO_LOAD) {
            InputStream is = MapReduceDemo.class.getResourceAsStream("/" + file);
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));

            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            map.put(file, sb.toString());

            is.close();
            reader.close();
        }*/
    }

}
