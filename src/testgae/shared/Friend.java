package testgae.shared;
 

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Friend implements IsSerializable {
	public Friend(){
		this.likeTxt = "";
	}
	public boolean firma;
	public String name;  
	public String firstName;
	public String username;
	public String id;
//	public String status;
	public List<String> friendlists;
//	public boolean publicOnlyForfriends;
//	public boolean profilePublic;
	public boolean notPublicForSearchEngines;
	public List<LikeObject> hisLikes;
//	public String htmlCodepublic;
	public LinkedList<Sresult> randLinks;
//	public List<String> installedStuff;
	public List<String> doubleFriends;
	public String likeTxt;
	public boolean resend;
 
	 
}
