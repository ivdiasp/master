package testgae.server;

import java.io.BufferedReader;   
import java.io.IOException; 
import java.io.InputStreamReader;  
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection; 
import java.net.URLDecoder; 
import java.net.URLEncoder;
import java.util.LinkedList; 

import testgae.shared.Sresult;
 
 
 
public class TestGoogleAPI {
	TestGoogleAPI(String s){
		 if (s == null || s.equals("")){
			 try {
				key  =URLEncoder.encode("AIzaSyBAQC_8z5Q3RM1NJh--Qxzh7W0GpqhAxSg","UTF8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }else{
				key = s;		 
		 }
	
	}
 
		
//	static String key = "&key=AIzaSyBAQC_8z5Q3RM1NJh--Qxzh7W0GpqhAxSg";
//static String key = "AIzaSyBAQC_8z5Q3RM1NJh--Qxzh7W0GpqhAxSg"; // max
static String key   ; //sadfhrffdsre@yahoo.de
	
//	private static final Logger log = Logger
//	.getLogger(GreetingServiceImpl.class.getName());
	public   LinkedList<Sresult> getRandLinks(String s1) {
		String s = s1.replace(" ", "%20");
//		String fs = "%22"+s+"%22";
		String fs =s;
		URL url = null;
//		String [] arr = null;
//		LinkedList<String> res =   new LinkedList<String>();
 
		try { 
			String s11 = "https://www.googleapis.com/customsearch/v1?key="+key+"&cx=013036536707430787589:_pqjad5hr1a&q="+fs+"&alt=json";
			url = new URL(s11);
			System.out.println(s11);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean start = false;
		Sresult resTmp = new Sresult();
		LinkedList<Sresult> Sres = new LinkedList<Sresult>();  
		URLConnection connection = null;
		try {
			connection = url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		connection.addRequestProperty("Referer",
//				"http://clickjacked.appspot.com");
		// .getResultSetSize()
		String line; 
		BufferedReader reader = null;
		try {
//			reader = new BufferedReader(new InputStreamReader(
//					connection.getInputStream(), "UTF-8"));
		    reader  = new BufferedReader(
		            new InputStreamReader(connection.getInputStream(), "UTF8"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while ((line = reader.readLine()) != null) {
//				builder.append(line);//del
				String str = line;System.out.println(line);
				if(str.contains("\"items\": [")){
					start = true;
				}
				if (start){
					if (str.contains("\"title\": \"")){ 
						 String [] tmp  = str.split("\"");
						 resTmp.name = URLDecoder.decode(tmp[3],"UTF8");
						  
					}
				    if (str.contains("\"link\": \"")){
				    	String [] tmp  = str.split("\"");
						 resTmp.link = tmp[3];
						 Sres.add(resTmp);
						 resTmp = new Sresult();
					} 	
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return Sres ;

	}
	

	/**
	 * @param args
	 * @throws JSONException
	 */
	// This example request includes an optional API key which you will need to
	// remove or replace with your own key.
	// Read more about why it's useful to have an API key.
	// The request also includes the userip parameter which provides the end
	// user's IP address. Doing so will help distinguish this legitimate
	// server-side traffic from traffic which doesn't come from an end-user.
/*	
	public   String getGoogleNo(String s1) {
		String s = s1.replace(" ", "%20");
		URL url = null;  
	 
		try {
			url = new URL(
					"http://google.com/search?"
							+ "q=" + s  );
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		URLConnection connection = null;
		try {
			connection = url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 ;
		// .getResultSetSize()
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				if (line.contains("<div id=resultStats>")){
					int i = line.indexOf("<div id=resultStats>");
					line.substring(i, i+30);
				}
	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		 log.info("api result: " + builder);
			 
	 
		return "" ;
	}
		*/
	 
}
