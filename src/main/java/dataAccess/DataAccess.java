package dataAccess;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.User;
import domain.Claim;
import domain.Demand;
import domain.DemandOffer;
import domain.Movement;
import domain.Offer;
import domain.Purchase;
import domain.Sale;
import domain.Subscription;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	private  EntityManager  db;
	private  EntityManagerFactory emf;
    private static final int baseSize = 160;
	private final boolean initialize = false;
	private static final float SUBSCRIPTION_PRICE = 200;
	private static final float PURCHASE_FEE_RATE = (float) 0.10;
	private static final float CASHBACK_RATE = (float) 0.05;
	private static final int DAILY_OFFER_LIMIT = 4;
	private static final int CLAIM_DAYS_FREE = 7;
	private static final int CLAIM_DAYS_SUBSCRIBED = 30;
	private static final int SUBSCRIPTION_CANCEL_DAYS = 30;
	private static final String SYSTEM_FUNDS_ID = "SYSTEM_FUNDS";
	private static float systemFundsBalance = 0;

	private static final String basePath="src/main/resources/images/";



	ConfigXML c=ConfigXML.getInstance();

     public DataAccess()  {
		if (initialize) {
			String fileName=c.getDbFilename();

			File fileToDelete= new File(fileName);
			if(fileToDelete.delete()){
				File fileToDeleteTemp= new File(fileName+"$");
				fileToDeleteTemp.delete();
				System.out.println("File deleted");
			 } else {
				 System.out.println("Operation failed");
				}
		}
		open();
		if  (initialize) 
			initializeDB();
		System.out.println("DataAccess created => isDatabaseLocal: "+c.isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());

		close();

	}
     
    public DataAccess(EntityManager db) {
    	this.db=db;
    }

	
	
	/**
	 * This method  initializes the database with some products and sellers.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();

		try { 
	       
		    //Create sellers 
			User user1=new User("Aitor Fernandez","111");
			User user2=new User("Ane Gaztañaga","222");
			User user3=new User("jon","111");
			User villa=new User("villa","111");

			
			//Create products
			Date today = UtilDate.trim(new Date());
		
			
			user1.addSale("futbol baloia", "oso polita, gutxi erabilita", 10, 2,  today, null);
			user1.addSale("salomon mendiko botak", "44 zenbakia, 3 ateraldi",20,  2,  today, null);
			user1.addSale("samsung 42\" telebista", "berria, erabili gabe", 175, 1,  today, null);


			user2.addSale("imac 27", "7 urte, dena ondo dabil", 1, 200,today, null);
			user2.addSale("iphone 17", "oso gutxi erabilita", 2, 400, today, null);
			user2.addSale("orbea mendiko bizikleta", "29\" 10 urte, mantenua behar du", 3,225, today, null);
			user2.addSale("polar kilor erlojua", "Vantage M, ondo dago", 3, 30, today, null);

			user3.addSale("sukaldeko mahaia", "1.8*0.8, 4 aulkiekin. Prezio finkoa", 3,45, today, null);

			
			db.persist(user1);
			db.persist(user2);
			db.persist(user3);
			db.persist(villa);

	
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	public User isRegistered(String log, String pass) {
		User r = null;
	    User u = db.find(User.class, log);
	    if (u != null) {
	    	boolean b = u.isConectedPassword(pass);
	    	if (b) {
		    	r = u;
	    	}
	    }
		return r;
	}
	
	public User register(String log, String pass) {
		User r = null;
	    User u = db.find(User.class, log);
	    if (u == null) {
		    db.getTransaction().begin();
			r = new User(log, pass);
			db.persist(r);
			db.getTransaction().commit();
	    }
		return r;
	}

	public User getUser(String username) {
		return db.find(User.class, username);
	}
	
	public boolean addFavorites(int saleNumer, String username) {
		boolean b = false;
		Sale s = db.find(Sale.class, saleNumer);
		User u = db.find(User.class, username);
		if (s != null && u != null) {
		    db.getTransaction().begin();
			b = u.addFavorites(s);
			db.persist(u);
			db.getTransaction().commit();
		}
		return b;
	}
	
	public boolean isInFavorites(int saleNumer, String username) {
		boolean b = false;
		Sale s = db.find(Sale.class, saleNumer);
		User u = db.find(User.class, username);
		if (s != null && u != null) {
			b = u.isInFavorites(s);
		}
		return b;
	}
	
	public void addMoney(float euro, String username) {
		if (euro <= 0) {
			return;
		}
		User u = db.find(User.class, username);
		if(u != null) {
		    db.getTransaction().begin();
			u.addMoney(euro);
			u.addMovement("BANK", username, euro);
			db.persist(u);
			db.getTransaction().commit();
		}

	}
	public boolean takeOutMoney(float euroKop, String username) {
		boolean b =false;
		if (euroKop <= 0) {
			return b;
		}
		User u = db.find(User.class, username);
		if(u!=null) {
			db.getTransaction().begin();
			b = u.takeOutMoney(euroKop);
			if (b) {
				u.addMovement(username, "BANK", euroKop);
			}
			db.persist(u);
			db.getTransaction().commit();
		}
		return b;
		
	}
	
	public boolean addOffer(float offer, int salenumber, String buyername) {
		if (offer <= 0) {
			return false;
		}
		boolean b = false;
		Sale s = db.find(Sale.class, salenumber);
		User buyer = db.find(User.class, buyername);
		if(s != null && buyer != null && !s.isSold() && s.getSeller() != null && !buyername.equals(s.getSeller().getUsername())) {
			Date now = new Date();
			if (!canMakeOffer(buyer, now)) {
				return false;
			}
			float total = offer + (offer * PURCHASE_FEE_RATE);
			if (buyer.getDirua() < total) {
				return false;
			}
		    db.getTransaction().begin();
			b = s.addOffer(buyer, offer, now, s);
			if (b) {
				registerOffer(buyer, now);
			}
			db.persist(buyer);
			db.persist(s);
			db.getTransaction().commit();
		}

		return b;
	}
	
	public boolean acceptOffer(int offerId, int salenumber, String sellername) {
		boolean b = false;
		Sale s = db.find(Sale.class, salenumber);
		User seller = db.find(User.class, sellername);
		Offer offer = db.find(Offer.class, offerId);
		if (offer == null) {
			return false;
		}
		User buyer = db.find(User.class, offer.getBuyer().getUsername());
		if(seller != null && buyer != null && s != null && !s.isSold()) {
			float base = offer.getOffer();
			float fee = base * PURCHASE_FEE_RATE;
			float total = base + fee;
			if (buyer.getDirua() < total) {
				return false;
			}
		    db.getTransaction().begin();
			b = seller.acceptOffer(base, s, buyer.getUsername(), seller.getUsername());
			if (b) {
				buyer.setDirua(buyer.getDirua() - total);
				buyer.addPurchase(s);
				buyer.addMovement(buyer.getUsername(), sellername, base);
				buyer.addMovement(buyer.getUsername(), SYSTEM_FUNDS_ID, fee);
				addSystemFunds(fee);
				if (buyer.isSubscribed()) {
					float cashback = base * CASHBACK_RATE;
					if (removeSystemFunds(cashback)) {
						buyer.addMoney(cashback);
						buyer.addMovement(SYSTEM_FUNDS_ID, buyer.getUsername(), cashback);
					}
				}
				s.setSold(true);
			}
			db.persist(buyer);
			db.persist(seller);
			db.getTransaction().commit();
		}
		
		return b;
	}
	
	public boolean declinedOffer(int salenumber, int offerId) {
		boolean b = false;
		Sale s = db.find(Sale.class, salenumber);
		Offer offer = db.find(Offer.class, offerId);
		if(s != null && offer != null && !s.isSold()) {
			db.getTransaction().begin();
			s.OfferDeclined(offer);
			db.persist(s);
			db.getTransaction().commit();
		}
		return b;
	}
	
	public boolean makeClaim(String description, String sellername, String claimername) {
		boolean b = false;
		User seller = db.find(User.class, sellername);
		User claimer = db.find(User.class, claimername);
		if(seller != null && claimer != null) {
			Date now = new Date();
			if (!canMakeClaim(claimer, seller, now)) {
				return false;
			}
			db.getTransaction().begin();
			b =  seller.addClaim(claimername, new Date(), description, false);
			db.persist(seller);
			db.getTransaction().commit();
		}
		return b;
	}

	public List<Movement> getMovements(String username) {
		User user = db.find(User.class, username);
		if (user == null || user.getMovements() == null) {
			return new ArrayList<Movement>();
		}
		return new ArrayList<Movement>(user.getMovements());
	}

	public Demand createDemand(String username, String prod, String description) {
		User user = db.find(User.class, username);
		if (user == null || prod == null || prod.isEmpty() || description == null || description.isEmpty()) {
			return null;
		}
		db.getTransaction().begin();
		Demand demand = user.addDemand(prod, description);
		db.persist(user);
		db.persist(demand);
		db.getTransaction().commit();
		return demand;
	}

	public List<Demand> getDemands(String search) {
		String text = (search == null) ? "" : search;
		TypedQuery<Demand> query = db.createQuery(
				"SELECT d FROM Demand d WHERE d.active = true AND (d.prod LIKE ?1 OR d.description LIKE ?1)",
				Demand.class);
		query.setParameter(1, "%" + text + "%");
		List<Demand> demands = query.getResultList();
		return new ArrayList<Demand>(demands);
	}

	public Demand getDemand(int demandId) {
		return db.find(Demand.class, demandId);
	}

	public boolean addDemandOffer(int demandId, String sellerUsername, String product, String description, float price) {
		if (price <= 0) {
			return false;
		}
		if (product == null || product.isEmpty() || description == null || description.isEmpty()) {
			return false;
		}
		Demand demand = db.find(Demand.class, demandId);
		User seller = db.find(User.class, sellerUsername);
		if (demand == null || seller == null || !demand.isActive()) {
			return false;
		}
		if (sellerUsername.equals(demand.getUsername())) {
			return false;
		}
		Date now = new Date();
		if (!canMakeOffer(seller, now)) {
			return false;
		}
		db.getTransaction().begin();
		DemandOffer offer = new DemandOffer(seller, product, description, price, now);
		boolean added = demand.addOffer(offer);
		if (added) {
			registerOffer(seller, now);
			db.persist(offer);
			db.persist(demand);
			db.persist(seller);
		}
		db.getTransaction().commit();
		return added;
	}

	public boolean acceptDemandOffer(int demandId, int offerId, String ownerUsername) {
		Demand demand = db.find(Demand.class, demandId);
		if (demand == null || !demand.isActive() || !ownerUsername.equals(demand.getUsername())) {
			return false;
		}
		DemandOffer selected = null;
		for (DemandOffer offer : demand.getOffers()) {
			if (offer != null && offerId == offer.getOfferId()) {
				selected = offer;
				break;
			}
		}
		if (selected == null) {
			return false;
		}

		User buyer = db.find(User.class, ownerUsername);
		User seller = selected.getSeller();
		if (buyer == null || seller == null) {
			return false;
		}
		float base = selected.getPrice();
		float fee = base * PURCHASE_FEE_RATE;
		float total = base + fee;
		if (buyer.getDirua() < total) {
			return false;
		}
		db.getTransaction().begin();
		buyer.setDirua(buyer.getDirua() - total);
		buyer.addMovement(buyer.getUsername(), seller.getUsername(), base);
		buyer.addMovement(buyer.getUsername(), SYSTEM_FUNDS_ID, fee);
		addSystemFunds(fee);
		seller.addMoney(base);
		seller.addMovement(buyer.getUsername(), seller.getUsername(), base);
		if (buyer.isSubscribed()) {
			float cashback = base * CASHBACK_RATE;
			if (removeSystemFunds(cashback)) {
				buyer.addMoney(cashback);
				buyer.addMovement(SYSTEM_FUNDS_ID, buyer.getUsername(), cashback);
			}
		}
		demand.setActive(false);
		demand.getOffers().clear();
		db.persist(buyer);
		db.persist(seller);
		db.persist(demand);
		db.getTransaction().commit();
		return true;
	}

	public boolean declineDemandOffer(int demandId, int offerId, String ownerUsername) {
		Demand demand = db.find(Demand.class, demandId);
		if (demand == null || !demand.isActive() || !ownerUsername.equals(demand.getUsername())) {
			return false;
		}
		DemandOffer selected = null;
		for (DemandOffer offer : demand.getOffers()) {
			if (offer != null && offerId == offer.getOfferId()) {
				selected = offer;
				break;
			}
		}
		if (selected == null) {
			return false;
		}
		db.getTransaction().begin();
		boolean removed = demand.removeOffer(selected);
		db.persist(demand);
		db.getTransaction().commit();
		return removed;
	}

	public boolean buySubscription(String username) {
		User user = db.find(User.class, username);
		if (user == null || user.isSubscribed()) {
			return false;
		}
		if (user.getDirua() < SUBSCRIPTION_PRICE) {
			return false;
		}
		db.getTransaction().begin();
		Subscription subscription = user.getSubscription();
		if (subscription == null) {
			subscription = new Subscription(new Date());
			user.setSubscription(subscription);
		} else {
			subscription.setActive(true);
			subscription.setStartDate(new Date());
		}
		user.setDirua(user.getDirua() - SUBSCRIPTION_PRICE);
		user.addMovement(user.getUsername(), SYSTEM_FUNDS_ID, SUBSCRIPTION_PRICE);
		addSystemFunds(SUBSCRIPTION_PRICE);
		db.persist(subscription);
		db.persist(user);
		db.getTransaction().commit();
		return true;
	}

	public boolean cancelSubscription(String username) {
		User user = db.find(User.class, username);
		Subscription subscription = (user == null) ? null : user.getSubscription();
		if (user == null || subscription == null || !subscription.isActive() || subscription.getStartDate() == null) {
			return false;
		}
		Date now = new Date();
		if (!isWithinDays(subscription.getStartDate(), now, SUBSCRIPTION_CANCEL_DAYS)) {
			return false;
		}
		if (!canRemoveSystemFunds(SUBSCRIPTION_PRICE)) {
			return false;
		}
		db.getTransaction().begin();
		subscription.setActive(false);
		user.addMoney(SUBSCRIPTION_PRICE);
		user.addMovement(SYSTEM_FUNDS_ID, user.getUsername(), SUBSCRIPTION_PRICE);
		removeSystemFunds(SUBSCRIPTION_PRICE);
		db.persist(subscription);
		db.persist(user);
		db.getTransaction().commit();
		return true;
	}
	
	
	
	
	/**
	 * This method creates/adds a product to a seller
	 * 
	 * @param title of the product
	 * @param description of the product
	 * @param status 
	 * @param selling price
	 * @param category of a product
	 * @param publicationDate
	 * @return Product
 	 * @throws SaleAlreadyExistException if the same product already exists for the seller
	 */
	public Sale createSale(String title, String description, int status, float price,  Date pubDate, String sellerUsername, File file) throws  FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException {
		

		System.out.println(">> DataAccess: createProduct=> title= "+title+" seller="+sellerUsername);
		try {
		

			if(pubDate.before(UtilDate.trim(new Date()))) {
				throw new MustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ErrorSaleMustBeLaterThanToday"));
			}
			if (file==null)
				throw new FileNotUploadedException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ErrorFileNotUploadedException"));

			db.getTransaction().begin();
			
			User user = db.find(User.class, sellerUsername);
			if (user.doesSaleExist(title)) {
				db.getTransaction().commit();
				throw new SaleAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.SaleAlreadyExist"));
			}

			Sale sale = user.addSale(title, description, status, price, pubDate, file);
			//next instruction can be obviated

			db.persist(user); 
			db.getTransaction().commit();
			 System.out.println("sale stored "+sale+ " "+user);

			

			   System.out.println("hasta aqui");

			return sale;
		} catch (NullPointerException e) {
			   e.printStackTrace();
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
		
		
	}
	
	/**
	 * This method retrieves all the products that contain a desc text in a title
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc in a title
	 */
	public List<Sale> getSales(String desc) {
		System.out.println(">> DataAccess: getProducts=> from= "+desc);

		List<Sale> res = new ArrayList<Sale>();	
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1",Sale.class);   
		query.setParameter(1, "%"+desc+"%");
		
		List<Sale> sales = query.getResultList();
	 	 for (Sale sale:sales){
		   res.add(sale);
		  }
	 	return res;
	}
	
	/**
	 * This method retrieves the products that contain a desc text in a title and the publicationDate today or before
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc in a title
	 */
	public List<Sale> getPublishedSales(String desc, Date pubDate) {
		System.out.println(">> DataAccess: getProducts=> from= "+desc);

		List<Sale> res = new ArrayList<Sale>();	
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1 AND s.pubDate <=?2",Sale.class);   
		query.setParameter(1, "%"+desc+"%");
		query.setParameter(2,pubDate);
		
		List<Sale> sales = query.getResultList();
	 	 for (Sale sale:sales){
		   res.add(sale);
		  }
	 	return res;
	}

	public void open(){
		
		String fileName=c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);
			  db = emf.createEntityManager();
    	   }
		System.out.println("DataAccess opened => isDatabaseLocal: "+c.isDatabaseLocal());

		
	}

	public BufferedImage getFile(String fileName) {
		File file=new File(basePath+fileName);
		BufferedImage targetImg=null;
		try {
             targetImg = rescale(ImageIO.read(file));
        } catch (IOException ex) {
            //Logger.getLogger(MainAppFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
		return targetImg;

	}
	
	public BufferedImage rescale(BufferedImage originalImage)
    {
		System.out.println("rescale "+originalImage);
        BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
        g.dispose();
        return resizedImage;
    }

	public boolean addToCart(int saleNumber, String username) {
		User u = db.find(User.class, username);
		Sale s = db.find(Sale.class, saleNumber);
		db.getTransaction().begin();
		boolean b = false;
		if (!u.getCart().getCartList().contains(s)){
			b = u.addToCart(s);
			db.persist(u);
			db.getTransaction().commit();
		}
		return b;
	}

	public double getCartAmount(String username) {
		User u = db.find(User.class, username);
		return u.getCart().getAmount();
	}

	public boolean clearCart(String username) {
		User u = db.find(User.class, username);
		db.getTransaction().begin();
		boolean b = u.getCart().clearCart();
		db.persist(u);
		db.getTransaction().commit();
		return b;
	}

	public List<Sale> getCartList(String username) {
		User u = db.find(User.class, username);
		return u.getCart().getCartList();
	}

	private void addSystemFunds(float amount) {
		systemFundsBalance += amount;
	}

	private boolean canRemoveSystemFunds(float amount) {
		return amount <= systemFundsBalance;
	}

	private boolean removeSystemFunds(float amount) {
		if (amount > systemFundsBalance) {
			return false;
		}
		systemFundsBalance -= amount;
		return true;
	}

	private boolean canMakeOffer(User user, Date now) {
		if (user.isSubscribed()) {
			return true;
		}
		Date today = UtilDate.trim(now);
		Date last = user.getDailyOfferDate();
		if (last == null) {
			return true;
		}
		Date lastDay = UtilDate.trim(last);
		if (!today.equals(lastDay)) {
			return true;
		}
		return user.getDailyOfferCount() < DAILY_OFFER_LIMIT;
	}

	private void registerOffer(User user, Date now) {
		Date today = UtilDate.trim(now);
		Date last = user.getDailyOfferDate();
		if (last == null || !today.equals(UtilDate.trim(last))) {
			user.setDailyOfferDate(today);
			user.setDailyOfferCount(1);
		} else {
			user.setDailyOfferCount(user.getDailyOfferCount() + 1);
		}
	}

	private boolean canMakeClaim(User claimer, User seller, Date now) {
		if (claimer.getPurchases() == null || claimer.getPurchases().isEmpty()) {
			return false;
		}
		int daysLimit = claimer.isSubscribed() ? CLAIM_DAYS_SUBSCRIBED : CLAIM_DAYS_FREE;
		long limitMs = TimeUnit.DAYS.toMillis(daysLimit);
		for (Purchase purchase : claimer.getPurchases()) {
			if (purchase == null || purchase.getSale() == null || purchase.getSale().getSeller() == null) {
				continue;
			}
			if (!seller.getUsername().equals(purchase.getSale().getSeller().getUsername())) {
				continue;
			}
			Date purchaseDate = purchase.getPurchaseDate();
			if (purchaseDate == null) {
				continue;
			}
			long diff = now.getTime() - purchaseDate.getTime();
			if (diff >= 0 && diff <= limitMs) {
				return true;
			}
		}
		return false;
	}

	private boolean isWithinDays(Date start, Date now, int days) {
		long diff = now.getTime() - start.getTime();
		return diff >= 0 && diff <= TimeUnit.DAYS.toMillis(days);
	}

	public void close(){
		db.close();
		System.out.println("DataAcess closed");
	}
	
}
