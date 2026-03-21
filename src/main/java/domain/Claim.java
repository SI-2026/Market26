package domain;

import java.util.Date;

public class Claim {
	private User buyer;
	private Date date;
	private String description;
	private boolean status;
	
	public Claim() {
		super();
	}
	
	public Claim(User buyer, Date date, String description, boolean status) {
		this.buyer = buyer;
		this.date = date;
		this.description = description;
		this.status = status;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
