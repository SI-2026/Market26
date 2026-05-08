package businessLogic;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import dataAccess.DataAccess;
import domain.Movement;
import domain.Offer;
import domain.Purchase;
import domain.Review;
import domain.Sale;
import domain.User;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;

import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;


/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	 private static final int baseSize = 160;

		private static final String basePath="src/main/resources/images/";
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		dbManager=new DataAccess();		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		dbManager=da;		
	}
    

	/**
	 * {@inheritDoc}
	 */
   @WebMethod
	public Sale createSale(String title, String description,int status, float price, Date pubDate, String sellerEmail, File file) throws  FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException {
		dbManager.open();
		Sale product=dbManager.createSale(title, description, status, price, pubDate, sellerEmail, file);		
		dbManager.close();
		return product;
   };
	
   /**
    * {@inheritDoc}
    */
	@WebMethod 
	public List<Sale> getSales(String desc){
		dbManager.open();
		List<Sale>  rides=dbManager.getSales(desc);
		dbManager.close();
		return rides;
	}
	
	/**
	    * {@inheritDoc}
	    */
		@WebMethod 
		public List<Sale> getPublishedSales(String desc, Date pubDate) {
			dbManager.open();
			List<Sale>  rides=dbManager.getPublishedSales(desc,pubDate);
			dbManager.close();
			return rides;
		}
	/**
	    * {@inheritDoc}
	    */
	@WebMethod public BufferedImage getFile(String fileName) {
		return dbManager.getFile(fileName);
	}

    
	public void close() {
		DataAccess dB4oManager=new DataAccess();
		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}
    /**
	 * {@inheritDoc}
	 */
    @WebMethod public Image downloadImage(String imageName) {
        File image = new File(basePath+imageName);
        try {
            return ImageIO.read(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	@Override
	public User isRegistered(String log, String pass) {
    	dbManager.open();
		User u = dbManager.isRegistered(log, pass);
		dbManager.close();
		return u;
	}

	@Override
	public User register(String log, String pass) {
    	dbManager.open();
		User u = dbManager.register(log, pass);
		dbManager.close();
		return u;
	}

	@Override
	public User getUser(String username) {
		dbManager.open();
		User u = dbManager.getUser(username);
		dbManager.close();
		return u;
	}
	

	@Override
	public boolean addFavorites(int saleNumer, String username) {
		dbManager.open();
		boolean b = dbManager.addFavorites(saleNumer, username);
		dbManager.close();
		return b;
	}
	
	@Override
	public boolean isInFavorites(int saleNumer, String username) {
		dbManager.open();
		boolean b = dbManager.isInFavorites(saleNumer, username);
		dbManager.close();
		return b;
	}
	
	@Override
	public void addMoney(float euro, String username) {
		dbManager.open();
		dbManager.addMoney(euro, username);
		dbManager.close();
	}
	
	@Override
	public boolean takeOutMoney(float euro, String username) {
		dbManager.open();
		boolean b = dbManager.takeOutMoney(euro, username);
		dbManager.close();
		return b;
	}
	
	@Override
	public boolean addOffer(float offer, int salenumber, String buyername) {
		dbManager.open();
		boolean b = dbManager.addOffer(offer, salenumber, buyername);
		dbManager.close();
		return b;
		
	}
	
	@Override
	public boolean acceptOffer(int offerId, int salenumber, String sellername) {
		dbManager.open();
		boolean b = dbManager.acceptOffer(offerId, salenumber, sellername);
		dbManager.close();
		return b;
	}
	
	@Override
	public boolean declinedOffer(int salenumber, int offerId) {
		dbManager.open();
		boolean b = dbManager.declinedOffer(salenumber, offerId);
		dbManager.close();
		return b;
	}
	
	@Override
	public boolean makeClaim(String description, String sellername, String claimername) {
		dbManager.open();
		boolean b = dbManager.makeClaim(description, sellername, claimername);
		dbManager.close();
		return b;
	}

	@Override
	public List<Movement> getMovements(String username) {
		dbManager.open();
		List<Movement> movements = dbManager.getMovements(username);
		dbManager.close();
		return movements;
	}
	@Override
	public boolean addToCart(int saleNumber, String username) {
		dbManager.open();
		boolean b = dbManager.addToCart(saleNumber, username);
		dbManager.close();
		return b;
	}

	@Override
	public double getCartAmount(String username) {
		dbManager.open();
		double amount = dbManager.getCartAmount(username);
		dbManager.close();
		return amount;
	}

	@Override
	public boolean clearCart(String username) {
		dbManager.open();
		boolean b = dbManager.clearCart(username);
		dbManager.close();
		return b;
	}
	@Override
	public List<Sale> getCartList(String username) {
		dbManager.open();
		List<Sale> cartList = dbManager.getCartList(username);
		dbManager.close();
		return cartList;
	}

	@Override
	public boolean addReview(int saleNumber, String buyerUsername, int rating, String comment) {
		dbManager.open();
		boolean b = dbManager.addReview(saleNumber, buyerUsername, rating, comment);
		dbManager.close();
		return b;
	}

	@Override
	public boolean canReview(int saleNumber, String buyerUsername) {
		dbManager.open();
		boolean b = dbManager.canReview(saleNumber, buyerUsername);
		dbManager.close();
		return b;
	}

	@Override
	public List<Review> getReviewsForSale(int saleNumber) {
		dbManager.open();
		List<Review> reviews = dbManager.getReviewsForSale(saleNumber);
		dbManager.close();
		return reviews;
	}

    
}

