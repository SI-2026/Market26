package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Cart implements Serializable {

    @Id
    @GeneratedValue
    private Integer cartId;

    private String username;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Sale> cartList;

    private double totalAmount;

	public Cart() {
        super();
        this.cartList = new ArrayList<Sale>();
    }

    public Cart(String username) {
        this();
        this.username = username;
    }

    public Cart(String username, List<Sale> cartList) {
        this.username = username;
        this.cartList = (cartList != null) ? new ArrayList<Sale>(cartList) : new ArrayList<Sale>();
        recalculateAmount();
    }

    public Integer getCartId() {
        return cartId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Sale> getCartList() {
        return cartList;
    }

    public boolean addToCart(Sale sale) {
        if (sale == null || cartList.contains(sale)) {
            return false;
        }
        boolean added = cartList.add(sale);
        if (added) {
            totalAmount += sale.getPrice();
        }
        return added;
    }

    public boolean clearCart() {
        boolean cleared = cartList.clear();
        totalAmount = 0;
        return cleared;
    }

    public double getAmount() {
        recalculateAmount();
        return totalAmount;
    }

    private void recalculateAmount() {
        double amount = 0;
        for (Sale sale : cartList) {
            if (sale != null) {
                amount += sale.getPrice();
            }
        }
        this.totalAmount = amount;
    }
	

}
