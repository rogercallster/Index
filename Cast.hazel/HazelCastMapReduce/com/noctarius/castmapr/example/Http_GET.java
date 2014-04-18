package com.noctarius.castmapr.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import stork.ad.*;
import stork.feather.Stat;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

public class Http_GET {
	 
    private static final String USER_AGENT = "Mozilla/5.0";
    
    public static Ad  itterator(String path) throws ClientProtocolException, IOException, URISyntaxException{
  //  public static void  main(String[] args) throws ClientProtocolException, IOException, URISyntaxException{
    	
    String url = "http://didclab-ws2:8080/DirectoryListingService/rest/dls/list";
    //String url = "http://ec2-184-73-223-158.compute-1.amazonaws.com:8080/DirectoryListingService/rest/dls/list";
    //String url = "http://didclab-ws2.cse.buffalo.edu:8080/DirectoryListingService/rest/dls/list";

    HttpClient client = HttpClientBuilder.create().build();
    //HttpGet request = new HttpGet(url);
    //String path = "";
    //String PATH= "gsiftp://oasis-dm.sdsc.xsede.org/home//gxmap/gx-ca-update"+path;
    String PATH = "gsiftp://osg-xsede.grid.iu.edu/"+path;
    System.out.println(PATH);
   
    URI uri = new URIBuilder(url).addParameter("URI", PATH)
    		.addParameter("proxyCertificate", "/home/hduser/cred/x509up_u1000").build();
    
    HttpGet request = new HttpGet(uri);
    
    // add request header
    request.addHeader("User-Agent", USER_AGENT);
    HttpResponse response = client.execute(request);
   
    System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());
//     
//        BufferedReader rd = new BufferedReader(
//            new InputStreamReader(response.getEntity().getContent()));
 
   
    Ad info = Ad.parse(response.getEntity().getContent());
   
    Stat stat = info.unmarshalAs(Stat.class);

    System.out.println(Ad.marshal(stat));
   
    //Ad ret =null;
    return Ad.marshal(stat);
    }
}