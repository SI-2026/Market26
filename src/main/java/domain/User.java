package domain;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlID
	@Id
	private String username;
	private String password;
	private float euro;
	@XmlIDREF
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Sale> sales = new ArrayList<Sale>();
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Purchase> purchases = new ArrayList<Purchase>();
	@XmlIDREF
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Sale> favorites = new ArrayList<Sale>();
	
	@XmlID
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Claim> claims = new ArrayList<Claim>();
	
	@XmlID
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Movement> movements = new ArrayList<Movement>();

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Cart cart;

	public User() {
		super();
	}

	public User(String username, String name) {
		this.username = username;
		this.password = name;
		this.euro = 0;
		this.cart = new Cart(username);
	}

	public float getDirua() {
		return this.euro;
	}

	public void setDirua(float dirua) {
		this.euro = dirua;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		if (this.cart != null) {
			this.cart.setUsername(username);
		}
	}

	public String getpassword() {
		return password;
	}

	public void setpassword(String pass) {
		this.password = pass;
	}

	public String toString() {
		return username + ";" + password + sales;
	}

	/**
	 * This method creates/adds a sale to a seller
	 * 
	 * @param title           of the sale
	 * @param description     of the sale
	 * @param status
	 * @param selling         price
	 * @param publicationDate
	 * @return Sale
	 */

	public Sale addSale(String title, String description, int status, float price, Date pubDate, File file) {

		Sale sale = new Sale(title, description, status, price, pubDate, file, this);
		sales.add(sale);
		return sale;
	}

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location
	 * @param to   the destination location
	 * @param date the date of the ride
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesSaleExist(String title) {
		for (Sale s : sales)
			if (s.getTitle().compareTo(title) == 0)
				return true;
		return false;
	}

	public Purchase addPurchase(Sale sale) {
		Purchase purchase = new Purchase(this, sale, new Date());
		purchases.add(purchase);
		return purchase;
	}

	public boolean addFavorites(Sale sale) {
		if (favorites.contains(sale)) {
			return false;
		}
		return favorites.add(sale);
	}

	public List<Sale> getSales() {
		return sales;
	}
	
	public List<Sale> getFavorites() {
		return favorites;
	}

	public boolean isInFavorites(Sale sale) {
		return favorites.contains(sale);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username != other.username)
			return false;
		return true;
	}

	public Boolean isConectedPassword(String pass) {
		return this.getpassword().equals(pass);
	}

	public void addMoney(float dirua) {
		this.euro += dirua;
	}

	public boolean takeOutMoney(float euroKop) {
		if (euroKop > this.euro) {
			return false;
		}

		this.euro -= euroKop;
		return true;
	}


	public boolean acceptOffer(float prezioa, Sale s, String nondik, String nora){
		euro += prezioa;
		addMovement(nondik, nora, prezioa);
		return  sales.remove(s);
		
	}
	
	public boolean addClaim(String buyer, Date date, String description, boolean status) {
		Claim c = new Claim(buyer, date, description, status);
		return (!claims.contains(c)) ? claims.add(c) : false;
	}
	
	public List<Claim> getClaims() {
		return claims;
	}

	public List<Movement> getMovements() {
		return movements;
	}
	

	public boolean addMovement(String nondik, String nora, float kopurua) {
		Movement m = new Movement(kopurua, nondik,nora,new Date());
		return (movements.contains(m) ? false : movements.add(m));
		
	}

	public boolean addToCart(Sale sale) {
		return cart.addToCart(sale);
	}	

	public Cart getCart() {
		return cart;
	}

	
	
	

}
