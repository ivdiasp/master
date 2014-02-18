package testgae.server;

import com.google.appengine.api.datastore.Key;
 
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Percentage {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    private Double d;

    private String link;

    private Long fbookId;
    
	public void setD(Double d) {
		this.d = d;
	}

	public Double getD() {
		return d;
	}

	public void setFbookId(Long fbookId) {
		this.fbookId = fbookId;
	}

	public Long getFbookId() {
		return fbookId;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
	}
}
