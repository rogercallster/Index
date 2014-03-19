package com.noctarius.castmapr.example;

import java.util.ArrayList;

public class Buffer {
	public static ArrayList<String> buffer = new ArrayList<String>();
	
	//other method to call map reduce after serializing data 
	public static void printbuffer()
	{
		for (String print : buffer)
			System.out.println(print);
		
	}
	//for map reduce input
	public static void serializeBuffer ()
	{
		
	}
	                }
