package com.noctarius.castmapr.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import stork.ad.Ad;
import stork.module.FileTree;
import stork.module.ftp.FTPSession;
import stork.scheduler.EndPoint;

/*
 * Manager tries to update the changes in the system
 * 
 * */
public class Manager {
	
	
	public static void printTree(FileTree ft) {
		printTree("", ft);
	} public static void printTree(String prefix, FileTree ft) {
		System.out.println(prefix+"/"+ft.name);
		if (ft.files != null) for (FileTree f : ft.files)
			printTree(prefix+"/"+ft.name, f);
	}
	
	public static void manager (String string)throws Exception{
		//TODO Auto-generated method stub
		//BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		//
		
		 EndPoint e = new EndPoint(string);
		   FTPSession s= FTPSession.connect(e).sync();
		  //  System.out.println (Ad.marshal((s.list("/").sync())));
		   FileTree rt=  s.list("/").sync();
		   String path ="/";
		   itterate(rt,path);
		  // ft.path();
           
           //ft.files //list the file in current dir
		  // System.out.println(Ad.marshal(root));
		
		   
	}
		   
		   public static void itterate (FileTree root,String path){
			   
			   int i=0;
				  while(i<root.files.length)
					  {
					  if(root.files[i].perm.charAt(0) == 'd')
						 // 
					  { //System.out.println("found dir");
					 // itterate(root.files[i]);
					  }
						else {  
							int addr= UniqueList.addUnqiueList("path is " + path+root.files[i].name );
							long st = root.files[i].time;
							
							
							
							//System.out.println("time = "+st);
							String name = root.files[i].name;
							//System.out.println("name = "+name);
				   
							String perm = root.files[i].perm;
						  // System.out.println("permision  = "+perm);
						   long size =root.files[i].size;
						  // System.out.println("size = "+size);
						   //long count = root.files[i].count();
						  // System.out.println("count = "+count);
						   AttributeObjects.TIME.putKey(Long.toString(st),addr);
						   AttributeObjects.NAME.putKey(name,addr);
						   AttributeObjects.PERM.putKey(perm,addr);
						   AttributeObjects.SIZE.putKey(Long.toString(size),addr);
				   
						   	System.out.println();
					  }
					  i++;
					  }
				  
				//    server();
				   
				  //System.out.println("name of file is "+UniqueList.arrayList.get((AttributeHash.AttrHash.get("doc2").get(0))));
		   }
		   
		   
		   

		  

	}


