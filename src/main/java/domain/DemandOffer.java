package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class DemandOffer implements Serializable {
	@Id
	@GeneratedValue
	private Integer offerId;

	@ManyToOne
	private User seller;

	private String product;
	private String description;
	private float price;
	private Date offerDate;

	public DemandOffer() {
		super();
	}

	public DemandOffer(User seller, String product, String description, float price, Date offerDate) {
		this.seller = seller;
		this.product = product;
		this.description = description;
		this.price = price;
		this.offerDate = offerDate;
	}

	public Integer getOfferId() {
		return offerId;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getOfferDate() {
		return offerDate;
	}

	public void setOfferDate(Date offerDate) {
		this.offerDate = offerDate;
	}
}
