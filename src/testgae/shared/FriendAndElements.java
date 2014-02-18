package testgae.shared;
 
import java.util.List; 

import com.google.gwt.user.client.rpc.IsSerializable;

public class FriendAndElements implements IsSerializable {

	public Friend friend;
	public String  status;
	public List<LikeObject> hisLikes;
}
