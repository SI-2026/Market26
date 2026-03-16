package domain;


import java.util.Date;

import javax.persistence.*;



public class Offer {
@Id
@GeneratedValue
private int offerId;

private Date offerDate;

@ManyToOne
private User buyer;
private float offer;

public Offer() {
	super();
}

public Offer(User buyer, float euro, Date offerDate) {
	this.buyer = buyer;
	this.offer = euro;
	this.offerDate = offerDate;
	
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


}
