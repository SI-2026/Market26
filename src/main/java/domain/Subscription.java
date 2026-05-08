package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Subscription implements Serializable {
	@Id
	@GeneratedValue
	private Integer subscriptionId;

	private Date startDate;
	private boolean active;

	public Subscription() {
		super();
	}

	public Subscription(Date startDate) {
		this.startDate = startDate;
		this.active = true;
	}

	public Integer getSubscriptionId() {
		return subscriptionId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
