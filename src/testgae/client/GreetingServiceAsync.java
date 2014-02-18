package testgae.client;

import java.util.List;  

import testgae.shared.Friend;
import testgae.shared.FriendAndElements; 
 

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void getFriendsTableRPC(String code, AsyncCallback<List<Friend>> callback)
			throws IllegalArgumentException;

//	void getLikes(String code, AsyncCallback<List<LikeObject>> asyncCallback)	throws IllegalArgumentException;

//	void getFriendsAndElementsRPC(String code, Friend chosenFriend,
//			AsyncCallback<FriendAndElements> asyncCallback);
 

	void setPrivacyStateMeAll(String code, String key, Friend f,
			AsyncCallback<Friend> callback);

	void postOnWallRPC(String code, boolean notPublicForSearchEngines, String id,String txt, AsyncCallback<Boolean> callback);

//	void publishFriendListsRPC(String code, AsyncCallback<Boolean> cb);

	void saveRatioRPC(Long id, double d, AsyncCallback<Boolean> asyncCallback);

	 
	void getAccessTokenRPC(String code, String redirect, AsyncCallback<String> asyncCallback);

	void setPrivacyState(String accToken, String key, Friend f,
			AsyncCallback<Friend> callback);
}
