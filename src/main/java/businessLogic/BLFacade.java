package businessLogic;

import java.io.File;
import java.util.Date;
import java.util.List;

import domain.Sale;
import domain.User;
import domain.Movement;
import domain.Offer;
import domain.Purchase;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.awt.Image;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

	/**
	 * This method creates/adds a product to a seller
	 * 
	 * @param title of the product
	 * @param description of the product
	 * @param status 
	 * @param selling price
	 * @param category of a product
	 * @param publicationDate
	 * @return Sale
	 */
   @WebMethod
	public Sale createSale(String title, String description, int status, float price, Date pubDate, String sellerEmail, File file) throws  FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException;
	
	
	/**
	 * This method retrieves the products that contain desc
	 * 
	 * @param desc the text to search
	 * @return collection of sales that contain desc 
	 */
	@WebMethod public List<Sale> getSales(String desc);
	
	/**
	 * 	 * This method retrieves the products that contain a desc text in a title and the publicationDate today or before
	 * 
	 * @param desc the text to search
	 * @param pubDate the date  of the publication date
	 * @return collection of sales that contain desc and published before pubDate
	 */
	@WebMethod public List<Sale> getPublishedSales(String desc, Date pubDate);

	
	/**
	 * This method calls the data access to initialize the database with some sellers and products.
	 * It is only invoked  when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
		
	@WebMethod public Image downloadImage(String imageName);
	
	@WebMethod public User isRegistered(String log, String pass);

	@WebMethod public User getUser(String username);
	
	@WebMethod public User register(String log, String pass);

	@WebMethod public boolean addFavorites(int saleNumer, String username);
	
	@WebMethod public boolean isInFavorites(int saleNumer, String username);
	
	@WebMethod public void addMoney(float dirua, String username);
	
	@WebMethod public boolean takeOutMoney(float euroKop, String username);
	
	@WebMethod public boolean addOffer(float offer, int salenumber, String buyername);
	
	@WebMethod public boolean acceptOffer(int offerId, int salenumber, String sellername);

	@WebMethod public boolean declinedOffer(int salenumber, int offerId);
	
	@WebMethod public boolean makeClaim(String description, String sellername, String claimername);

	@WebMethod public List<Movement> getMovements(String username);
	
	@WebMethod public boolean addToCart(int saleNumber, String username);

	@WebMethod public double getCartAmount(String username);

	@WebMethod public boolean clearCart(String username);

	@WebMethod public List<Sale> getCartList(String username);

	
}
