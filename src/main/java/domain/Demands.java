package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Demands {
	@Id
	@GeneratedValue
	private Integer demandID;
	
	@OneToMany
    private String username;
    private String prod;
    private String description;
    private List<Sale> prodOffer;
    private Date pubDate;

    
	public Demands (String prod, String description, String username){
        this.description = description;
        this.prod = prod; 
        this.username = username;
        this.prodOffer = new ArrayList<Sale>();
        this.pubDate = new Date();
    }

    public List<Sale> getProdOffer() {
		return prodOffer;
	}

	public void setProdOffer(List<Sale> prodOffer) {
		this.prodOffer = prodOffer;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProd() {
		return prod;
	}

	public void setProd(String prod) {
		this.prod = prod;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}
