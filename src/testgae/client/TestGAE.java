package testgae.client;

import java.util.HashMap;  
 
import java.util.LinkedList;
import java.util.List;

import testgae.shared.Friend;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT; 
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler; 
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button; 
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel; 
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;  
import com.google.gwt.user.client.ui.RootPanel; 
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;  
//import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TestGAE implements EntryPoint {
	{

	}
	private final GreetingServiceAsync rpc = GWT.create(GreetingService.class);

	private final Constants constants = GWT.create(Constants.class);
	final String code = com.google.gwt.user.client.Window.Location
			.getParameter("code");
	String key = com.google.gwt.user.client.Window.Location
	.getParameter("key");
	
//	String perm = com.google.gwt.user.client.Window.Location
//	.getParameter("perm");
	
	String messes = com.google.gwt.user.client.Window.Location
	.getParameter("messes");
	
	final VerticalPanel likesPanel = new VerticalPanel();
	
	LinkedList<DisclosurePanel> dListSres = new LinkedList<DisclosurePanel>();
	LinkedList<DisclosurePanel> dListLikes = new LinkedList<DisclosurePanel>();
	
	FlowPanel hpaneltmp = new FlowPanel();
	
	HashMap<String, Friend> chosenFriends = new HashMap<String, Friend>();
	VerticalPanel vPanel = new VerticalPanel();
	final HorizontalPanel hPanel = new HorizontalPanel();
	final VerticalPanel v2Panel = new VerticalPanel();
	int i,j ;
	final MyVPanel spanel = new MyVPanel();
	FlexTable mainFlex = new FlexTable();
	boolean allOpend;
	boolean allOpendSearch;
	final GraphElements gElements = new GraphElements(code,rpc,spanel,dListSres,dListLikes);
	final HTML loader = new HTML("<img src=\"ajax-loader.gif\">");
	int countHor = 0 ;
	int yi = 0;
	boolean noKey;
	String redString = null;
	boolean mess;
	List<Friend> resultG;
//	final HorizontalPanel hTimerPanel = new HorizontalPanel();
//	TextBox htmlTimer = new TextBox();
//	 int timer  = 0;

	 String redirect = "";
	String accToken = "";

	/**
	 * This is the entry point method.
	 */

	public void onModuleLoad() { 
//		if (measure== null)
// 		String measure = "";
//		else 
//			measure="&measure=true";
		  
		 if(Window.Location.getHref().contains("TestGAE2")){
			 redirect = 
				 "https://can-you-be-googeld.appspot.com/TestGAE2.html";
		 }else{
			 redirect = 
				 "https://can-you-be-googeld.appspot.com/TestGAE.html";
		 
		 }
		 
//		 redirect = "http://cl1ckjacked.dyndns.org:8888/TestGAE.html/";
//		 redirect = "https://clickjacked.appspot.com/TestGAE.html";
		 String id = "184025324975756";
//		 String id = "155881654480689";
		 redString = "https://graph.facebook.com/oauth/authorize?client_id="+id+"&redirect_uri="+redirect;

 	
		if(key == null){
			key = "";
		}
		hPanel.add(v2Panel); 
		final TabPanel tabPanel = new TabPanel(); 
		tabPanel.setWidth("710px");
		tabPanel.setStyleName("tabPanel");

		{
			FlowPanel flowpanel1 = new FlowPanel();
			flowpanel1.add(hPanel);
			tabPanel.add(flowpanel1, "Check Account(s)");
			tabPanel.selectTab(0);

			FlowPanel flowpanel2 = new FlowPanel();
			HTML html = new HTML("Verantwortlicher: Iwan Gulenko<br>Facebook: <a href=\"https://www.facebook.com/4fVigerPJrf3TZK28gZX6EGp\">Ivvan Gu</a><br>");
			flowpanel2.add(html);
			tabPanel.add(flowpanel2, "About"); 
		} 

		RootPanel.get("all").add(tabPanel);



		getFriends();

	}


	private void getFriends() {  
		rpc.getAccessTokenRPC(code, redirect,new AsyncCallback<String>() { 
			@Override
			public void onFailure(Throwable caught) {

				Window.Location.replace(redString); //app id change //change to normal	
			}
			@Override
			public void onSuccess(String result) {
				accToken = result; 
				gElements.accToken = result;
	
		rpc.getFriendsTableRPC(accToken, new AsyncCallback<List<Friend>>() {
			public void onFailure(Throwable caught) {
				Window.Location.replace(redString); //app id change //change to normal	
				 
			}
			
			public void onSuccess(final List<Friend> result) {  
				HTML tmp = new HTML(constants.header());
				
				final SimplePanel meContainer = new SimplePanel();
				v2Panel.add(meContainer);  
				v2Panel.add(new HTML("<hr>"));
				tmp = new HTML(constants.first()); 
				tmp.setStyleName("first"); 
				v2Panel.add(tmp);
				v2Panel.add(new HTML("<hr>"));

				  tmp = new HTML(constants.two());
				v2Panel.add(tmp);
				  tmp = new HTML(constants.twoone());
					v2Panel.add(tmp);
				//rights for wall and likes
				Button bu = new Button();
				bu.setText(constants.permWall());
				bu.addClickHandler(new ClickHandler(){
					@Override
					public void onClick(
							ClickEvent event) { 
							Window.Location.replace(redString+URL.encode("&scope=publish_stream"));
					} 
				});
				v2Panel.add(bu);
				v2Panel.add(new HTML("<br/>"));
				  tmp = new HTML(constants.twotwo());
					v2Panel.add(tmp);
				Button bu2 = new Button();
				bu2.setText(constants.permLikes());
				bu2.addClickHandler(new ClickHandler(){
					@Override
					public void onClick(
							ClickEvent event) { 
							Window.Location.replace(redString+URL.encode("&scope=user_likes,friends_likes"));
					} 
				});
				v2Panel.add(bu2);
				v2Panel.add(new HTML("<br/>"));
				  tmp = new HTML(constants.twothree());
					v2Panel.add(tmp);
				
				v2Panel.add(new HTML("<hr>"));
				v2Panel.add(new HTML(constants.three()));
				
				tmp.setStyleName("");  
 //start	
 
				if (result == null || result.size()==0){
					Window.Location.replace(redString); //app id change //change to normal
				}
				 
				final Friend friendMeCall = result.get(0);
				
				if(messes!=null)
					friendMeCall.firma = true; 
				 
				 resultG = result;
//Window.alert("kjsdlf!");
				rpc.setPrivacyStateMeAll(accToken,key, friendMeCall, new AsyncCallback<Friend>() {
					public void onFailure(Throwable caught) {
//						rpc.setPrivacyStateMeAll(accToken, friendMeCall, this);
						Window.Location.replace(redString); //app id change //change to normal
					}

					public void onSuccess(final Friend friendMeResp) { 
						meContainer.add(gElements.personSearchAll(friendMeResp));
						
						if (result != null)
							result.remove(0);
						final int noFriends = result.size() - 1;
//						v2Panel.add(gElements.personSearchAll(friendMeResp));  
//						HTML tmp = new HTML(constants.first()); 
//						tmp.setStyleName("first"); 
//						v2Panel.add(tmp);
//						v2Panel.add(new HTML("<hr>"));
//
//						  tmp = new HTML(constants.two());
//						v2Panel.add(tmp);
//						v2Panel.add(new HTML("<hr>"));
//						v2Panel.add(new HTML(constants.three()));
						
						Button bu = new Button();
						bu.setText("Go!");
						final TextBox box = new TextBox ();
						if(key.equals(""))
							box.setText("Your Google Search Key");
						else 
							box.setText(key);
						box.addFocusHandler(new FocusHandler(){
							@Override
							public void onFocus(FocusEvent event) {
								box.setText("");
							}
						});
						bu.addClickHandler(new ClickHandler(){
							@Override
							public void onClick(
									ClickEvent event) {
								Window.Location.replace(Window.Location.getHref() + "&key=" + box.getText()); 
							} 
						});
						v2Panel.add(box);
						v2Panel.add(bu);

						v2Panel.add(new HTML("<hr>"));
						v2Panel.add(new HTML ( constants.seconds()));
//						v2Panel.add(hTimerPanel);

						final Button bExpLikes = new Button();
//						bExpLikes .setVisible(false);
						bExpLikes.setText(constants.showLikes());
						bExpLikes.addClickHandler(new ClickHandler(){
							@Override
							public void onClick(ClickEvent event) {
								for(DisclosurePanel p : dListLikes){
									if(allOpend)
										p.setOpen(false);
									else
										p.setOpen(true);
								}
								allOpend = !allOpend;
							}

						});
						v2Panel.add(bExpLikes);
						v2Panel.add(new HTML(constants.sl()));
						final Button bExpSearch = new Button(); 
						if(!key.isEmpty()){  
							bExpSearch.setText(constants.showSres());
							bExpSearch.addClickHandler(new ClickHandler(){
								@Override
								public void onClick(ClickEvent event) {
									for(DisclosurePanel p : dListSres){
										if(allOpendSearch)
											p.setOpen(false);
										else
											p.setOpen(true);
									} 
									allOpendSearch = !allOpendSearch;
								} 
							});
							v2Panel.add(bExpSearch);
						}
						
						
						
						final VerticalPanel warnPanel = new VerticalPanel();
						final VerticalPanel warnPanel2 = new VerticalPanel();						
						warnPanel2.add(warnPanel);				
						v2Panel.add(warnPanel2);	
						v2Panel.add(mainFlex); 
						{ 
							for (   final Friend f : result) {
								Timer t = new Timer() { 
									  public void run() { 
//											timer += 500; 
//											htmlTimer.setText(String.valueOf(timer/1000));
									  } 
									}; 
									// delay running for 2 seconds 
									if (yi % 10 == 0 && (yi!=0)){
										t.schedule(800);

									}
//						 

									AsyncCallback<Friend>	cb = new AsyncCallback<Friend>() {
									public void onFailure(Throwable caught) {
//										hPanel.add(new HTML(
//												"failure Friendfind"));
										GWT.log("friend find error with " + f.firstName + " " + f.id);
//										if(!f.resend){
//											f.resend = true;
//											rpc.setPrivacyState(accToken,key, f, this);
//										}
										
									}
									public void onSuccess(Friend fRest) {  
											mainFlex.setWidget(i, j, gElements.personSearch(fRest)); 
											
										if((j%3 == 0) && (j!=0)){
											i++;
											j=0;
										}else{
											j++;		
										}										
										yi++;
										if (yi >= noFriends) {
											loader.setVisible(false); 
											double exactDouble = Double.valueOf(spanel.daFriends) / Double.valueOf(noFriends);
											double d = 100*(Math.round(exactDouble * 100) / 100.0);
											String txt = spanel.daFriends + " / " + noFriends + " (" + d + "%) " + constants.ofMyFriendsCanBeFound() + " " + constants.postOnMyWallTxt();
											String txtFbook = spanel.daFriends + " / " + noFriends + " (" + d + "%) " +  " " + constants.ofMyFriendsCanBeFoundFbook() + " " + constants.postOnMyWallTxt();
											 
											if(bExpSearch!= null)
												bExpSearch.setVisible(true);
											
											rpc.saveRatioRPC(Long.valueOf(friendMeResp.id), exactDouble, new AsyncCallback<Boolean>() {
												public void onFailure(Throwable caught) {
													hPanel.add(new HTML("rpc ratio error"));
													// Show the RPC error message to the user
												}  
												@Override
												public void onSuccess(Boolean result) {
													// TODO Auto-generated method stub 
												}
											}
												);
											if(warnPanel.getWidgetCount() == 0 && (!noKey)){
												HTML htmlTmp = new HTML(txt);
												warnPanel.setStyleName("postWall");
												warnPanel.add(htmlTmp);
												warnPanel.add(gElements.postOnWallButtonMe(friendMeResp.id, constants.postOnMyWall(), txtFbook));
 
											}
										}
										
									} 
								};
//								rpc.setPrivacyState(code, f, cb);
								rpc.setPrivacyState(accToken,key, f, cb);
								
							} 
							v2Panel.add(loader);
							v2Panel.add(new HTML("<hr>"));
							HTML tmp = new HTML(constants.four());
							v2Panel.add(tmp);
							v2Panel.add(new HTML("<br><center><a target=\"_blank\" href=\"deinstall.png\"><img style=\"height:200px\" src=\"deinstall.png\"></a></center>"));
							v2Panel.add(new HTML("<hr>"));
							tmp = new HTML(constants.five());
							v2Panel.add(tmp); 
						}//

					}
				});
				


			}
		});
			} 
	});
 }

}
