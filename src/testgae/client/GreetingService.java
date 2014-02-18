package testgae.client;

import java.util.List;   

import testgae.shared.Friend; 
 

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	List<Friend> getFriendsTableRPC(String code) throws IllegalArgumentException;

//	List<LikeObject> getLikes(String code);

//	FriendAndElements getFriendsAndElementsRPC(String code, Friend chosenFriend);
 

	Friend setPrivacyStateMeAll(String code,String key, Friend f);
 

//	boolean publishFriendListsRPC(String code);
 

	boolean saveRatioRPC(Long id, double d);

	Boolean postOnWallRPC(String code, boolean notPublicForSearchEngines,
			String id, String txt);

	String getAccessTokenRPC(String code, String redirect);

	Friend setPrivacyState(String accToken, String key, Friend f);
}
