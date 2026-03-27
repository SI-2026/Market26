package gui;

import java.util.*;
import java.util.List;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.awt.image.BufferedImage;

import businessLogic.BLFacade;
import domain.Offer;
import domain.Sale;


public class ShowSaleGUI extends JFrame {
	
    File targetFile;
    BufferedImage targetImg;
    public JPanel panel_1;
    private static final int baseSize = 160;
	private static final String basePath="src/main/resources/images/";
	
	private static final long serialVersionUID = 1L;

	private JTextField fieldTitle=new JTextField();
	private JTextField fieldDescription=new JTextField();
	
	JLabel labelStatus = new JLabel(); 

	private JLabel jLabelTitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.Title"));
	private JLabel jLabelDescription = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Description")); 
	private JLabel jLabelProductStatus = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Status"));
	private JLabel jLabelPrice = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Price"));
	private JTextField fieldPrice = new JTextField();
	private File selectedFile;
    private String irudia;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	DefaultComboBoxModel<String> statusOptions = new DefaultComboBoxModel<String>();
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	private JLabel statusField=new JLabel();
	private JFrame thisFrame;
	private final JButton jButtonFavorites = new JButton((String) null);
	
	public ShowSaleGUI(Sale sale, String username) { 
		final boolean isGuest = "Guest".equals(username);
		final boolean isSeller = sale.getSeller() != null && username.equals(sale.getSeller().getUsername());
		thisFrame=this; 
		this.setVisible(true);
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		//this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.CreateProduct"));

		fieldTitle.setText(sale.getTitle());
		fieldDescription.setText(sale.getDescription());

		fieldPrice.setText(Float.toString(sale.getPrice()));		
		
		labelStatus.setText(new SimpleDateFormat("dd-MM-yyyy").format(sale.getPublicationDate()));
		
		jLabelTitle.setBounds(new Rectangle(6, 56, 92, 20));
		
		jLabelPrice.setBounds(new Rectangle(6, 166, 101, 20));
		fieldPrice.setEditable(false);
		fieldPrice.setBounds(new Rectangle(137, 166, 60, 20));

		
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		jButtonClose.setBounds(new Rectangle(16, 268, 114, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);			}
		});

		jLabelMsg.setBounds(new Rectangle(275, 214, 305, 20));
		jLabelMsg.setForeground(Color.red);

		jLabelError.setBounds(new Rectangle(6, 231, 320, 20));
		jLabelError.setForeground(Color.red);
		

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jLabelTitle, null);
		
		
		this.getContentPane().add(jLabelPrice, null);
		this.getContentPane().add(fieldPrice, null);
		
		jLabelProductStatus.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelProductStatus.setBounds(6, 187, 140, 25);
		getContentPane().add(jLabelProductStatus);
		
		jLabelDescription.setBounds(6, 81, 109, 16);
		getContentPane().add(jLabelDescription);
		fieldTitle.setEditable(false);
		
		
		fieldTitle.setBounds(128, 53, 370, 26);
		getContentPane().add(fieldTitle);
		fieldTitle.setColumns(10);
		fieldDescription.setEditable(false);
		
		
		fieldDescription.setBounds(127, 81, 371, 73);
		getContentPane().add(fieldDescription);
		fieldDescription.setColumns(10);
		
		panel_1 = new JPanel();
		panel_1.setBounds(318, 166, 180, 160);
		getContentPane().add(panel_1);
		
		labelStatus.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		labelStatus.setBounds(37, 231, 289, 16);
		getContentPane().add(labelStatus);
		
		
		BLFacade facade = UserGUI.getBusinessLogic();
		String file=sale.getFile();
		if (file!=null) {
			Image img=facade.downloadImage(file);
			targetImg = rescale((BufferedImage)img);
			panel_1.setLayout(new BorderLayout(0, 0));
			panel_1.add(new JLabel(new ImageIcon(targetImg))); 
		}
		System.out.println("status: "+sale.getStatus());
		statusField = new JLabel(Utils.getStatus(sale.getStatus())); 
		statusField.setBounds(137, 191, 92, 16);
		getContentPane().add(statusField);
		
		JButton jButtonOffer = new JButton((String) null);
		jButtonOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String amountInput = JOptionPane.showInputDialog(
						ShowSaleGUI.this,
						ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.OfferPrompt"),
						ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.MakeOffer"),
						JOptionPane.PLAIN_MESSAGE);
				if (amountInput == null) {
					return;
				}
				try {
					float amount = Float.parseFloat(amountInput);
					if (amount <= 0) {
						jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.InvalidOffer"));
						return;
					}
					boolean added = facade.addOffer(amount, sale.getSaleNumber(), username);
					if (added) {
						jLabelError.setText("");
						jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.OfferCreated"));
					} else {
						jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.OfferError"));
					}
				} catch (NumberFormatException ex) {
					jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.InvalidOffer"));
				}
			}
		});
		jButtonOffer.setBounds(new Rectangle(16, 268, 114, 30));
		jButtonOffer.setBounds(164, 268, 114, 30);
		jButtonOffer.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.MakeOffer"));
		if (isGuest) {
			jButtonOffer.setEnabled(false);
			jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.OnlyRegisteredOffer"));
		} else if (sale.isSold()) {
			jButtonOffer.setEnabled(false);
			jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.SaleSold"));
		} else if (sale.getSeller() != null && username.equals(sale.getSeller().getUsername())) {
			jButtonOffer.setEnabled(false);
			jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.YourOwnSale"));
		}
		getContentPane().add(jButtonOffer);

		JButton jButtonAcceptOffer = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.AcceptOffer"));
		jButtonAcceptOffer.setBounds(16, 231, 130, 30);
		jButtonAcceptOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Offer> offers = sale.getOfferList();
				if (offers == null || offers.isEmpty()) {
					jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.NoOffers"));
					return;
				}

				String[] offerOptions = new String[offers.size()];
				for (int i = 0; i < offers.size(); i++) {
					Offer current = offers.get(i);
					offerOptions[i] = "#" + (i + 1) + " - " + current.getBuyer().getUsername() + " - "
							+ current.getOffer() + " EUR";
				}

				Object selected = JOptionPane.showInputDialog(
						ShowSaleGUI.this,
						ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.SelectOffer"),
						ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.AcceptOffer"),
						JOptionPane.PLAIN_MESSAGE,
						null,
						offerOptions,
						offerOptions[0]);

				if (selected == null) {
					return;
				}

				int idx = Arrays.asList(offerOptions).indexOf(selected.toString());
				if (idx < 0) {
					jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.OfferError"));
					return;
				}

				Offer selectedOffer = offers.get(idx);
				boolean ok = facade.acceptOffer(selectedOffer, sale.getSaleNumber(), username);
				if (ok) {
					sale.setSold(true);
					jButtonAcceptOffer.setEnabled(false);
					jButtonOffer.setEnabled(false);
					jButtonFavorites.setEnabled(false);
					jLabelError.setText("");
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.OfferAccepted"));
				} else {
					jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.AcceptOfferError"));
				}
			}
		});

		if (!isSeller || sale.isSold()) {
			jButtonAcceptOffer.setEnabled(false);
		}
		getContentPane().add(jButtonAcceptOffer);
		
		jButtonFavorites.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (facade.addFavorites(sale.getSaleNumber(), username)) {
					jButtonFavorites.setEnabled(false);
				}
			}
		});
		jButtonFavorites.setBounds(new Rectangle(16, 268, 114, 30));
		jButtonFavorites.setBounds(164, 231, 114, 30);
		jButtonFavorites.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.AddtoFav"));
		if (isGuest) {
			jButtonFavorites.setEnabled(false);
			jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.OnlyRegisteredFav"));
		} else if (facade.isInFavorites(sale.getSaleNumber(), username)) {
			jButtonFavorites.setEnabled(false);
		}
		
		getContentPane().add(jButtonFavorites);

		
	}	 
	public BufferedImage rescale(BufferedImage originalImage)
    {
        BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
        g.dispose();
        return resizedImage;
    }
}

