package testgae.server;

import java.io.BufferedReader;   
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;  

import javax.jdo.PersistenceManager;

import testgae.client.GreetingService;
import testgae.shared.Friend; 
import testgae.shared.LikeObject;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;  
import com.restfb.types.Page;
import com.restfb.types.User;
import com.restfb.Connection;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")

public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService { 
//	final static String REDIRECT = "https://can-you-be-googeld.appspot.com/TestGAE.html"; // app change//change to normal
	static String REDIRECT ;
//	final static String REDIRECT = "http://clickjacked.appspot.com/"; // app change	
//	final static String REDIRECT = "http://cl1ckjacked.dyndns.org:8888/"; // app change

	final static String CLIENT_SECRET = "b3a990b976e2c6c2bd0af268b3c8d610";
//	final static String CLIENT_SECRET = "b8a3bf4e13e62d472ee5bfc1c62c2a7a";
	
	final static String CLIENT_ID = "184025324975756";
//	final static String CLIENT_ID = "155881654480689";


	@Override
	public Friend setPrivacyStateMeAll(String accToken, String key, Friend f) {
//		String accToken = getAccessToken(code);

		f.notPublicForSearchEngines = notPublicForSearchEngine(f.id); 
		 f.hisLikes = getLikesOfUser(accToken,"me"); 			
		  
			TestGoogleAPI tga = new TestGoogleAPI(key);		
			 String str = f.firstName + "%20" + f.name;
			f.randLinks = tga.getRandLinks(str);
		 
		{// make persistent
		    PersistenceManager pm = PMF.get().getPersistenceManager(); 
	        UserJDO e = new UserJDO();
	        e.setTimestamp();
	        e.setFirma(f.firma);
	        e.setId(Long.valueOf(f.id));
	        e.setName(f.firstName + " " + f.name);
	        e.setNotPublicForSearchEngines(f.notPublicForSearchEngines);
	        e.setLink("http://www.facebook.com/profile.php?id=" + f.id);
	        if(f.doubleFriends != null)
	        	e.setDoubleFriends(f.doubleFriends);
	        try {
	            pm.makePersistent(e);
	        } finally {
	            pm.close();
	        }
		}

		return f;
	}
	@Override
	public Friend setPrivacyState(String accToken,String key, Friend f) {
		f.notPublicForSearchEngines = notPublicForSearchEngine(f.id);
//		f.hisLikes  = getLikesOfUser(code,f.id);
//		 f.publicOnlyForfriends = isItPublicOnlyForFriends(f.id);
//		log.info(f.firstName + " " + f.name);
 
		if((key != null) && !key.isEmpty()){
			TestGoogleAPI tga = new TestGoogleAPI(key);		
			 String str = f.firstName + "%20" + f.name;
			f.randLinks = tga.getRandLinks(str);
		}
		if(key == null || key.isEmpty()){
		 f.hisLikes = getLikesOfUser(accToken,f.id); //todo: send code from client
	}
//		 System.err.println("aaaaaaaaaaaaa" + f.hisLikes.get(0).name);
		// problem: needs further permissions
		return f;
	}



	private List<LikeObject> getLikesOfUser(String accessToken, String userId) {
//		String accessToken = getAccessToken(code);
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
		List<LikeObject> likeListRes = new LinkedList<LikeObject>();
 try{
		Connection<User> userConn = facebookClient.fetchConnection(userId
				+ "/likes", User.class);
		
		if(userConn==null)
			return likeListRes;
		
		List<User> currUsersLikeList = userConn.getData();

		for (User u : currUsersLikeList) {
			String pageId = u.getId(); // id der page
			// get page details
			Page p = facebookClient.fetchObject(pageId, Page.class);
			if(p == null)
				return likeListRes;
			
			String link = p.getLink();

			LikeObject l = new LikeObject();
			l.id = pageId;
			l.link = link;
			l.name = u.getName();

			likeListRes.add(l);
		}
 }catch (Exception e){
	 return likeListRes;
 }
return likeListRes; 
}
	public List<Friend> getFriendsTableRPC(String accessToken)
			throws IllegalArgumentException {

//		String accessToken = getAccessToken(code);
		
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
		Connection<User> myFriends = facebookClient.fetchConnection(
				"me/friends", User.class);
		List<User> friendList = myFriends.getData();
		// friendList.add(0, user);
		List<Friend> res = new LinkedList<Friend>();
		
		User u1 = facebookClient.fetchObject("me", User.class);
		Friend f1 = new Friend();
		// no log log.warning(u1.getLocale());
		{
			// // no log log.warning("ich : " + u1.getId().toString());
			f1.username = getUsernameFromMe(accessToken, u1.getId());
			String[] arr1 = u1.getName().split(" "); // geth nicht anders?!
			f1.id = u1.getId();
//			// no log log.info("userstring: " + u1.toString());
			
			if (arr1.length == 3) {
				f1.firstName = arr1[0] + " " + arr1[1];
				f1.name = arr1[2];
			} else // länge == 2
			{
				f1.name = arr1[1];
				f1.firstName = arr1[0];
			}
		}
		
		HashSet<String> test = new HashSet<String>();
		
		for (User u : friendList) {
			// list: id,name
			Friend f = new Friend();
			f.id = u.getId(); 
			String[] arr = u.getName().split(" "); // geth nicht anders?!
			if (arr.length == 3) {
				f.firstName = arr[0] + " "+ arr[1];
				f.name = arr[2];
			} else // länge == 2
			{
				f.name = arr[1];
				f.firstName = arr[0];
			}
			// f.publicForSearchEngines = isItPublicForSearchEngine(f.id);
			res.add(f);
			
			if(!test.contains(f.firstName + f.name))
				test.add(f.firstName + f.name);
			else{
				if(f1.doubleFriends==null)
					f1.doubleFriends = new LinkedList<String>();
				f1.doubleFriends.add(f.firstName + " " +f.name);
			}
				
		}
		res.add(0, f1); 
	
		return res; 
	}

	private String getUsernameFromMe(String accessToken, String id) {
		String endpoint = "https://graph.facebook.com/"+id+"?access_token="+accessToken; // url
		 
		try { 
			String urlStr = endpoint;

			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
 			conn.connect();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
	  		String line;
			while ((line = rd.readLine()) != null) {
				// sb.append(line);

				if (line.contains("username")) {
					// no log log.warning("username zeile: " + line);
					String[] arr = line.split("username");
					String[] arr2 = arr[1].split("\"");
					return arr2[2];
				}
			}
			rd.close();
	 	} catch (Exception e) {
			e.printStackTrace();
		}
		return "noUsername";
	}


//	private static final Logger log = Logger
//			.getLogger(GreetingServiceImpl.class.getName());

	private boolean notPublicForSearchEngine(String id) {
		String endpoint = "http://www.facebook.com/profile.php?id=" + id; // url
		// Send a GET request to the servlet
		try {
			// Send data
			String urlStr = endpoint;

			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
 
			String result = "";
			//
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
				// no log log.info("html111: " + line);
			}
			rd.close();
			result = sb.toString();
//			System.out.println("asdf1" +result);
			// // no log log.warning("header: " + str);
			String uri = conn.getURL().toString();
			// no log log.warning(id + " rennpferd: " + uri);
//			System.out.println("RESULT"  + uri);
			if (uri.equals("http://www.facebook.com/"))
				return true; // gut

			if (uri.contains("profile"))
				return true; // gut

			return false; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; // schlecht 
	}



	private static String getAccessToken(String code) {
		URL url = null; 
		try {
			url = new URL(
					"https://graph.facebook.com/oauth/access_token?client_id="
							+ CLIENT_ID + "&" + "redirect_uri=" + REDIRECT
							+ "&client_secret=" + CLIENT_SECRET + "&code="
							+ code);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		URLConnection u = null;
		try {
			u = url.openConnection();
			DataInputStream in;
			in = new DataInputStream(u.getInputStream());
			@SuppressWarnings("deprecation")
			String s = in.readLine();
//			return s.substring(13);
			// no log log.info("acc token : " + s);
			//fix 28.06
			String res = "";
			try{
				res = s.substring(13).split("&expires")[0];
			}catch(ArrayIndexOutOfBoundsException e){
				System.err.println("noactoken");
				return "noAccToken";
			}
			return URLEncoder.encode(res, "UTF-8");
			// 28.06
//			return s.substring(13);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ErrorNoAccesTokenFound";
	}




 
	@Override
	public Boolean postOnWallRPC(String accessToken, boolean notPublicForSearchEngines,String id, String txt) {
//		String accessToken = getAccessToken(code);
//		String tmp = "";
//			TestGoogleAPI tga = new TestGoogleAPI();		
//			String [] arr = f.randLinks = tga.getRandLinks(friend.firstname + "%20" + friend.name);
//			try{
//				tmp = " Google Hit: " + arr[11];
//			}catch(ArrayIndexOutOfBoundsException e){			
//			}
		String txt2 = txt;
		if (txt.contains("Spam")){
			txt2 = txt + " Remove Spam: " + "http://can-you-be-googeld.appspot.com/removeSpammyPage.png";
		}
		
		
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
		// facebookClient.publish(id + "/feed", "String",FacebookType.class,
		// Parameter.with("message", "RestFB test"));
		String pic = null;
		if(notPublicForSearchEngines)
			pic = "http://can-you-be-googeld.appspot.com/goodTUM.jpg";
		else{
			pic = "http://can-you-be-googeld.appspot.com/badTUM.jpg";
			txt2 += " Facebook Settings: " + "https://can-you-be-googeld.appspot.com/noPubSearch.png";
		}
		
		facebookClient.publish(id + "/feed", FacebookType.class,  Parameter
				.with("message", txt2),Parameter.with("picture", pic));

		return true;
	}


	 

	@Override
	public boolean saveRatioRPC(Long id, double d) {
		{// make persistent
		    PersistenceManager pm = PMF.get().getPersistenceManager(); 
		    Percentage e = new Percentage(); 
	        e.setD(d);
	        e.setFbookId(id);  
	        e.setLink("http://www.facebook.com/profile.php?id=" + id);
	        try {
	            pm.makePersistent(e);
	        } finally {
	            pm.close();
	        }
		} 
		return false;
	}

	@Override
	public String getAccessTokenRPC(String code, String redirectaaa) {
		REDIRECT = redirectaaa;
		return getAccessToken(code);
	}
}
