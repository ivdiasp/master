package testgae.client;
 
import java.util.LinkedList;
import java.util.List; 

import testgae.shared.Friend;
import testgae.shared.LikeObject;
import testgae.shared.Sresult;

import com.google.gwt.core.client.GWT; 
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration; 
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable; 
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;  
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GraphElements {
	private final Constants constants = GWT.create(Constants.class);
	int i, j;
	HandlerRegistration reg = null;
	private MyVPanel spanel;
	HTML tmp = new HTML("test");
	public String accToken;
	private LinkedList<DisclosurePanel> dListSres;
	private LinkedList<DisclosurePanel> dListLikes;
//	HashMap<String,TextBox> map  = new HashMap<String,TextBox>();   
	public GraphElements(String code, GreetingServiceAsync rpc, MyVPanel spanel, LinkedList<DisclosurePanel> dListSres, LinkedList<DisclosurePanel> dListLikes) {
		this.rpc = rpc;
//		this.code = code;
		this.spanel = spanel;  
		this.dListSres = dListSres;
		this.dListLikes = dListLikes;
//		this.accToken=  accToken;
	} 
//	public String code;
	public GreetingServiceAsync rpc;
	String txt = "";
	Button gb = null;
	
	public HorizontalPanel personSearch(Friend friend) {	 
		VerticalPanel vpanel = new VerticalPanel();
		vpanel.setWidth("150px");
		Widget likeDPanel = null;
		if((friend.hisLikes != null) && (friend.hisLikes.size()> 0)){ //UNTEN NOCH MAL DAS GLEICHE IF!!
			likeDPanel = like2FlexTable(friend.hisLikes, friend);  
		}
		HorizontalPanel hpanelr = new HorizontalPanel();
		hpanelr.setBorderWidth(1);	
		HorizontalPanel hpanel = new HorizontalPanel();
		HorizontalPanel hpanel2 = new HorizontalPanel();
		hpanel.add(new HTML("<a href=\"http://www.facebook.com/profile.php?id="
				+ friend.id + "\"><img src=\"http://graph.facebook.com/"
				+ friend.id + "/picture\"></img></a>"));
		hpanel.add(new HTML(
				"<a target=\"_blank\" href=\"http://www.facebook.com/profile.php?id="
						+ friend.id + "\">" + friend.firstName + " "
						+ friend.name + "</a>")); 
		vpanel.add(hpanel);
		{
			String pub = "";
			if (friend.notPublicForSearchEngines)
				pub = "<img width=\"20px\" src=\"/good.png\"></img>";
			else
				pub = "<img width=\"20px\" src=\"/bad.png\"></img>";
			
			hpanel2.add(new HTML50(pub, "20px")); //smilie
		}
		if (friend.notPublicForSearchEngines){ 
			hpanel2.add(postOnWallButton(friend.id,friend.notPublicForSearchEngines, constants.praise(),
					friend.firstName + ", " + constants.postOnWall() + " "  + friend.likeTxt +" - "+ personSearchLinks(friend)));
		}else { 
			hpanel2.add(postOnWallButton(friend.id,friend.notPublicForSearchEngines, constants.warn(),
					friend.firstName + ", " + constants.postOnWallBad() + " "  + friend.likeTxt + " - "+ ". "+ personSearchLinks(friend)));
			spanel.daFriends += 1.0; // spanel.daFriends / spanel.noFriends
		}
		vpanel.add(hpanel2);
		vpanel.add(getSE(friend)); // getSe gibt disc panel aus
		if(friend.randLinks!=null && friend.randLinks.size()>0){  
			VerticalPanel retB = new VerticalPanel();
			HTML h1 = new HTML("<b>Google Hits</b>:");
			h1.setStyleName("padding");
 			HTML h = new HTML("<a  target=\"_blank\" href=\""+friend.randLinks.get(0).link+"\">"+friend.randLinks.get(0).name.substring(0,30)+"...</a>");
 			h.setStyleName("padding");
 			h.setStyleName("padding");
 			friend.randLinks.remove(0);
			DisclosurePanel p = null;
			p = new DisclosurePanel("more Hits...");
			dListSres.add(p); 
			VerticalPanel vvv = new VerticalPanel();
			vvv.setWidth("150px"); 
			for (Sresult sRes : friend.randLinks){
//				vvv.add(new HTML("<a  target=\"_blank\" href=\""+str+"\">"+str.substring(7, 23)+"...</a>"));
				vvv.add(new HTML("<a  target=\"_blank\" href=\""+sRes.link+"\">"+sRes.name+"</a>"));
			} 
			retB.add(h1); //
			retB.add(h); 
			p.add(vvv);
			retB.add(p);
			retB.setStyleName("border");
			vpanel.add(retB); 
		}
		if((friend.hisLikes != null)  && (friend.hisLikes.size()> 0)){
			vpanel.add(likeDPanel);  
		}
//		DisclosurePanel seDiscPanel = getSE(friend);
//		vpanel.add(seDiscPanel);

//		if(friend.randLinks!=null){
//			final DisclosurePanel dpanel = new DisclosurePanel("Google Hits");
//			for (String str : friend.randLinks)
//				dpanel.add(new HTML("<a href=\""+str+"\">"+str.substring(7, 28)+"</a>"));
//			vpanel.add(dpanel);
//		}
		hpanelr.setStyleName("People Search");
		hpanelr.add(vpanel); 
		return hpanelr;  
	}
	
	 
	protected Widget like2FlexTable(List<LikeObject> res, Friend friend) { 
		HorizontalPanel h = new HorizontalPanel();
		int spamCount = 0;
		FlexTable f = new FlexTable();
		int i = 1;
		// f.setWidget(0, 0, new HTML("id"));
		f.setWidget(0, 0, new HTML("Name"));
		f.setWidget(0, 1, new HTML("Link"));
		for (LikeObject l : res) { 
			String link = l.link;
			String name = l.name;
			f.setWidget(i, 0, new HTML("<a target=\"_blank\" href=\"" + link
					+ "\">" + name + "</a>"));
			if (link.contains("facebook.com")) {	
				f.setWidget(i, 1, new HTML("ok"));
			} else {
				f.setWidget(i, 1, new HTML("spam?"));
				spamCount++;
			} 
			i++;
		} 
		
		DisclosurePanel p = null;
		String likeTxt = "";
			if (spamCount == 0){
				h.add(new HTML("<img width=\"15px\" src=\"/good.png\"></img>"));
				likeTxt = constants.bound() + " " + String.valueOf(res.size()) + " Likes";
				p = new DisclosurePanel (likeTxt);
			}
 			else{
 				h.add(new HTML("<img width=\"15px\" src=\"/bad.png\"></img>"));
 				likeTxt = constants.bound() + " " + String.valueOf(res.size()) + " Likes, " + spamCount + " x Spam?";
 				p = new DisclosurePanel(likeTxt);
			} 
			dListLikes.add(p);
			p.add(f); 	 
			p.setWidth("140px");
		h.add(p);
		friend.likeTxt = likeTxt;
		return h;
	}
	
	
	public Widget personSearchAll(Friend friend) {//Window.alert("sdf" + friend.hisLikes);
		VerticalPanel vpanel = new VerticalPanel();
		vpanel.setWidth("410px");
		HorizontalPanel hpanelr = new HorizontalPanel();
		hpanelr.setStyleName("paddingThis");
		hpanelr.setBorderWidth(1);
		HorizontalPanel hpanel = new HorizontalPanel();	
		HorizontalPanel hpanel2 = new HorizontalPanel();
		hpanel.add(new HTML("<a href=\"http://www.facebook.com/profile.php?id="
				+ friend.id + "\"><img src=\"http://graph.facebook.com/"
				+ friend.id + "/picture\"></img></a>"));
		hpanel.add(new HTML(
				"<a target=\"_blank\" href=\"http://www.facebook.com/profile.php?id="
						+ friend.id + "\">" + friend.firstName + " "
						+ friend.name + "</a>"));
		vpanel.add(hpanel);
		{
			String pub = "";
			if (friend.notPublicForSearchEngines)
				pub = "<img width=\"20px\" src=\"/good.png\"></img>";
			else
				pub = "<img width=\"20px\" src=\"/bad.png\"></img>";
			hpanel2.add(new HTML50(pub, "20px"));

		}

		vpanel.add(hpanel2); // smilie
		hpanelr.add(vpanel);
		vpanel.add(getSE(friend));
		if(friend.randLinks!=null && friend.randLinks.size()>0){  
			VerticalPanel retB = new VerticalPanel();
			HTML h1 = new HTML("<b>Google Hits</b>:");
			h1.setStyleName("padding");
 			HTML h = new HTML("<a  target=\"_blank\" href=\""+friend.randLinks.get(0).link+"\">"+friend.randLinks.get(0).name+"</a>");
 			h.setStyleName("padding");

			DisclosurePanel p = null;
			p = new DisclosurePanel("more Hits...");  
//			p.setWidth("140px"); 
			friend.randLinks.remove(0);
			VerticalPanel vvv = new VerticalPanel();
			vvv.setWidth("450px"); 
			for (Sresult sRes : friend.randLinks){
//				vvv.add(new HTML("<a  target=\"_blank\" href=\""+str+"\">"+str.substring(7, 23)+"...</a>"));
				vvv.add(new HTML("<a  target=\"_blank\" href=\""+sRes.link+"\">"+sRes.name+"</a>"));
			} 
			retB.add(h1); //
			retB.add(h); 
			p.add(vvv);
			retB.add(p);
			retB.setStyleName("border");
			vpanel.add(retB); 
		}
		if((friend.hisLikes != null) && (friend.hisLikes.size()> 0)){
			vpanel.add(like2FlexTable(friend.hisLikes,friend));  
		}
		hpanelr.setStyleName("personSearch"); 

		VerticalPanel vpanelr = new VerticalPanel(); 
		vpanelr.add(hpanelr);
		
		if(friend.doubleFriends!=null){
			vpanelr.add(new HTML(constants.dFriends()));
			for (String s : friend.doubleFriends)
				vpanelr.add(new HTML("<b>"+s+"</b>"));
		}else
			vpanelr.add(new HTML(constants.NoDFriends()));

		if (friend.username.equals("noUsername"))
			vpanelr.add(new HTML( constants.prev() + 
					" <a target=\"_blank\" href=\"http://www.facebook.com/profile.php?viewas=100000686899395\">"
							+ "Show" + "</a>"));
		else
			vpanelr.add(new HTML( constants.prev() + 
					" <a target=\"_blank\" href=\"http://www.facebook.com/"
							+ friend.username + "?viewas=100000686899395\">"
							+ "Show" + "</a>"));
		
		vpanelr.setStyleName("centerThis2");
		

		return vpanelr;
	}

 
	private HTML getSE(Friend friend) { 
		String s = "";
//		s += constants.useSeHTML();

		s += "<a target=\"_blank\" href=\"http://www.google.com/search?as_q=%22"
						+ friend.firstName
						+ "+"
						+ friend.name
						+ "%22" + "\">"  + "Google" + "</a>";
		
		
		s += ", <a target=\"_blank\" href=\"http://images.google.com/images?q=%22"+ friend.firstName + "%20" + friend.name + "%22&imgtype=face\">" + "Google faces" + "</a>";

		s += ", <a target=\"_blank\" href=\"http://www.123people.com/s/"
						+ friend.firstName + "+" + friend.name + "/germany\">"
						+ " 123people.de" + "</a>";

		s += ", <a target=\"_blank\" href=\"http://www.yasni.de/"
						+ friend.firstName + "+" + friend.name
						+ "/person+information?sh\">" + "yasni.de" + "</a>";

		s += ", <a target=\"_blank\" href=\"http://wink.com/people/nm/"
						+ friend.firstName + "%20" + friend.name + "\">"
						+ " wink.com" + "</a>";

		s += ", <a target=\"_blank\" href=\"http://www.isearch.com/people-search/name:"
						+ friend.firstName + "+" + friend.name
						+ "/listings.html\">" + " isearch.com" + "</a>";
//		seHTML.setWidth("90px");
		HTML h = new HTML(s);
		h.setStyleName("paddingThis");
//		final DisclosurePanel p = new DisclosurePanel(constants.useSeHTML()); 
//		p.add(h);
//		p.setTitle("What else?");
//		p.setWidth("140px"); 
		return h;
	}

	private Widget postOnWallButton(final String id,final boolean notPublicForSearchEngines, String bTxt,
			final String txt) {
		final AsyncCallback<Boolean> cb = new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				gb.setText(constants.pressFail());
			}

			public void onSuccess(Boolean b) {
				gb.setText(constants.checkWall());
			}
		};
		final Button b = new Button();
		b.setText(bTxt);
		if (notPublicForSearchEngines)
			b.setStyleName("fb_buttonLoben");
		else
			b.setStyleName("fb_button");
		final ClickHandler handler2 = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open("http://www.facebook.com/profile.php?id=" + id
						+ "&sk=wall", "_blank", "");
			}
		};
		final ClickHandler handler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(!b.getText().equals(constants.checkWall())){
					b.setText("send...");
					rpc.postOnWallRPC(accToken,notPublicForSearchEngines, id, txt, cb);
					gb = b;
					b.setStyleName("fb_buttonLobenPinnwand");
					reg.removeHandler();
					b.addClickHandler(handler2);
				}
			}
		};

		reg = b.addClickHandler(handler);
		return b;
	}
	
	public Widget postOnWallButtonMe(final String id, String bTxt,
			final String txt) {
		final AsyncCallback<Boolean> cb = new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
//				gb.setText(constants.chWallBad());
			}

			public void onSuccess(Boolean b) {
				gb.setText(constants.checkWall());
			}
		};
		final Button b = new Button();
		b.setText(bTxt);
		b.setStyleName("fb_buttonLoben");

		final ClickHandler h2 = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open("http://www.facebook.com/profile.php?id=" + id
						+ "&sk=wall", "_blank", "");
			}
		};
		final ClickHandler handler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!b.getStyleName().equals("fb_buttonLobenPinnwand")) {
					rpc.postOnWallRPC(accToken,true, id, txt, cb);
				} 
				b.setText("sending...");
				gb = b; 
				b.setStyleName("fb_buttonLobenPinnwand");
				reg.removeHandler();
				b.addClickHandler(h2);
			}
		};

		 reg = b.addClickHandler(handler);
		return b;
	}
	
	private String personSearchLinks(Friend friend) { 

		if (friend.firstName.contains(" ")){
			friend.firstName = friend.firstName.replace(" ", "%20");
		}
//		String[] arr = new String[5];
		String s = "";
		
		s = "http://www.google.com/search?q=" + friend.firstName + "%20" + friend.name;
		
		s += " http://images.google.com/images?q="+ friend.firstName + "%20" + friend.name + "&imgtype=face";

		s += " http://www.123people.com/s/" + friend.firstName + "+"
				+ friend.name + "/germany\"";

		s += " http://www.yasni.de/" + friend.firstName + "+" + friend.name
				+ "/person+information?sh\"";

		s += " http://wink.com/people/nm/" + friend.firstName + "%20"
				+ friend.name + "\"";

//		s += "http://www.isearch.com/people-search/name:"
//				+ friend.firstName + "+" + friend.name + "/listings.html\"";
		
		return s;
	}

	
	
	
}
