package domain;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.persistence.*;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Offer implements Serializable {
	@Id
	@GeneratedValue
	private int offerId;

	@ManyToOne
	private Sale sale;

	private Date offerDate;

	@ManyToOne
	private User buyer;
	private float offer;

	public Offer() {
		super();
	}

	public Offer(User buyer, float euro, Date offerDate, Sale sale) {
		this.buyer = buyer;
		this.offer = euro;
		this.offerDate = offerDate;
		this.sale = sale;

	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public Date getOfferDate() {
		return offerDate;
	}

	public void setOfferDate(Date offerDate) {
		this.offerDate = offerDate;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public float getOffer() {
		return offer;
	}

	public void setOffer(float euro) {
		this.offer = euro;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

}
