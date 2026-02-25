
package domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.objectdb.o.CLN.p;

@Entity
public class Purchase {
    
    @Id
    @GeneratedValue
    private Integer purchaseNumber;
    private Date purchaseDate;
    private float price;
    private String buyerMail;

    @ManyToOne
    private User buyer;
    @ManyToOne
    private Sale product; 

    //eraikitzaile hutsa, DB-ak eta JPA-k behar dutelako
    public Purchase() {
        super();
    }

    public Purchase(String buyerMail, Date purchaseDate, float price, User buyer, Sale product, Integer purchaseNumber) {
        super();
        this.buyerMail = buyerMail;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.buyer = buyer;
        this.product = product;
        this.purchaseNumber = purchaseNumber;
    }

    //getter eta setter

    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }   

    public String getBuyerMail() {
        return buyerMail;
    }   

    public void setBuyerMail(String buyerMail) {
        this.buyerMail = buyerMail;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Sale getProduct() {
        return product;
    }   

    public void setProduct(Sale product) {
        this.product = product;
    }

    

}
