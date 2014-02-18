package testgae.server;

import java.util.List;

import com.google.appengine.api.datastore.Key;
 
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class UserJDO {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    private Long id;
    private	String name;
    private	String link; 
    private	Boolean notPublicForSearchEngines; 
    private	Long timestamp;
 

	private boolean firma;

	private List<String> doubleFriends; 
    
	 
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLink() {
		return link;
	}
	public void setNotPublicForSearchEngines(Boolean notPublicForSearchEngines) {
		this.notPublicForSearchEngines = notPublicForSearchEngines;
	}
	public Boolean getNotPublicForSearchEngines() {
		return notPublicForSearchEngines;
	}
	public void setTimestamp() {
		this.timestamp = System.currentTimeMillis() / 1000; 
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setFirma(boolean firma) {
		this.firma = firma;
	}
	public boolean isFirma() {
		return firma;
	}
	public void setDoubleFriends(List<String> doubleFriends) {
		this.doubleFriends = doubleFriends;
	}
	public List<String> getDoubleFriends() {
		return doubleFriends;
	}
 
	 
}
