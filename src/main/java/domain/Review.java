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
public class Review implements Serializable {
	@Id
	@GeneratedValue
	private Integer reviewId;

	@ManyToOne
	private Sale sale;

	@ManyToOne
	private User buyer;

	private int rating;
	private String comment;
	private Date reviewDate;

	public Review() {
		super();
	}

	public Review(User buyer, Sale sale, int rating, String comment, Date reviewDate) {
		this.buyer = buyer;
		this.sale = sale;
		this.rating = rating;
		this.comment = comment;
		this.reviewDate = reviewDate;
	}

	public Integer getReviewId() {
		return reviewId;
	}

	public Sale getSale() {
		return sale;
	}

	public User getBuyer() {
		return buyer;
	}

	public int getRating() {
		return rating;
	}

	public String getComment() {
		return comment;
	}

	public Date getReviewDate() {
		return reviewDate;
	}
}
