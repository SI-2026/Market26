package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Demand implements Serializable {
	@Id
	@GeneratedValue
	private Integer demandId;

	private String username;
	private String prod;
	private String description;
	private Date pubDate;
	private boolean active;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<DemandOffer> offers = new ArrayList<DemandOffer>();

	public Demand() {
		super();
	}

	public Demand(String username, String prod, String description) {
		this.username = username;
		this.prod = prod;
		this.description = description;
		this.pubDate = new Date();
		this.active = true;
	}

	public Integer getDemandId() {
		return demandId;
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

	public Date getPubDate() {
		return pubDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<DemandOffer> getOffers() {
		return offers;
	}

	public boolean addOffer(DemandOffer offer) {
		return offers.add(offer);
	}

	public boolean removeOffer(DemandOffer offer) {
		return offers.remove(offer);
	}
}
