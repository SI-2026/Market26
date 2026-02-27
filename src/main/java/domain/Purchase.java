package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Purchase implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    private User buyer;
    @OneToOne
    private Sale sale;
    private Date purchaseDate;


    public Purchase() {
        super();
    }

    public Purchase(User buyer, Sale sale, Date purchaseDate) {
        this.buyer = buyer;
        this.sale = sale;
        this.purchaseDate = purchaseDate;
    }

    public Integer getId() {
        return id;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}