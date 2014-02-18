package testgae.client;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class MyHorizontalPanel extends HorizontalPanel implements Comparable{
 int likesNo;
	@Override
	public int compareTo(Object arg0) {
		if(this.likesNo > ((MyHorizontalPanel) arg0).likesNo)
			return 1;
		else
			return -1;
	}

	
}
