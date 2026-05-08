package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Demand;
import domain.DemandOffer;

public class ShowDemandGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private Demand demand;
	private final int demandId;
	private final String username;
	private final JLabel labelMsg = new JLabel();
	private final JTable tableOffers = new JTable();
	private DefaultTableModel tableModelOffers;

	private final JTextField fieldOfferProduct = new JTextField();
	private final JTextField fieldOfferDescription = new JTextField();
	private final JTextField fieldOfferPrice = new JTextField();

	private final String[] columnNamesOffers = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Product"),
			ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Description"),
			ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Buyer"),
			ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Amount"),
			ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Date")
	};

	public ShowDemandGUI(Demand demand, String username) {
		this.demand = demand;
		this.demandId = demand.getDemandId();
		this.username = username;

		setSize(new Dimension(760, 520));
		getContentPane().setLayout(null);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.Title"));

		JLabel labelProduct = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Product"));
		labelProduct.setBounds(new Rectangle(24, 20, 110, 20));
		getContentPane().add(labelProduct);

		JLabel labelDescription = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Description"));
		labelDescription.setBounds(new Rectangle(24, 48, 110, 20));
		getContentPane().add(labelDescription);

		JLabel labelOwner = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Owner"));
		labelOwner.setBounds(new Rectangle(24, 76, 110, 20));
		getContentPane().add(labelOwner);

		JLabel labelDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Date"));
		labelDate.setBounds(new Rectangle(24, 104, 110, 20));
		getContentPane().add(labelDate);

		JLabel valueProduct = new JLabel(demand.getProd());
		valueProduct.setBounds(new Rectangle(140, 20, 560, 20));
		getContentPane().add(valueProduct);

		JLabel valueDescription = new JLabel(demand.getDescription());
		valueDescription.setBounds(new Rectangle(140, 48, 560, 20));
		getContentPane().add(valueDescription);

		JLabel valueOwner = new JLabel(demand.getUsername());
		valueOwner.setBounds(new Rectangle(140, 76, 560, 20));
		getContentPane().add(valueOwner);

		JLabel valueDate = new JLabel(new SimpleDateFormat("dd-MM-yyyy").format(demand.getPubDate()));
		valueDate.setBounds(new Rectangle(140, 104, 560, 20));
		getContentPane().add(valueDate);

		labelMsg.setBounds(new Rectangle(24, 440, 690, 20));
		labelMsg.setForeground(Color.RED);
		getContentPane().add(labelMsg);

		buildOwnerSection();
		buildOfferSection();

		reloadDemand();
	}

	private void buildOwnerSection() {
		JScrollPane scrollPaneOffers = new JScrollPane();
		scrollPaneOffers.setBounds(new Rectangle(24, 150, 700, 200));
		getContentPane().add(scrollPaneOffers);

		tableModelOffers = new DefaultTableModel(null, columnNamesOffers);
		tableOffers.setModel(tableModelOffers);
		tableModelOffers.setColumnCount(6);
		if (tableOffers.getColumnModel().getColumnCount() > 5) {
			tableOffers.getColumnModel().removeColumn(tableOffers.getColumnModel().getColumn(5));
		}
		scrollPaneOffers.setViewportView(tableOffers);

		JButton buttonAccept = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.Accept"));
		buttonAccept.setBounds(new Rectangle(24, 360, 160, 30));
		buttonAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableOffers.getSelectedRow();
				if (row < 0) {
					labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.SelectOffer"));
					return;
				}
				DemandOffer offer = (DemandOffer) tableModelOffers.getValueAt(row, 5);
				BLFacade facade = UserGUI.getBusinessLogic();
				boolean ok = facade.acceptDemandOffer(demandId, offer.getOfferId(), username);
				if (ok) {
					labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.OfferAccepted"));
					reloadDemand();
				} else {
					labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.AcceptError"));
				}
			}
		});
		getContentPane().add(buttonAccept);

		JButton buttonDecline = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.Decline"));
		buttonDecline.setBounds(new Rectangle(194, 360, 160, 30));
		buttonDecline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableOffers.getSelectedRow();
				if (row < 0) {
					labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.SelectOffer"));
					return;
				}
				DemandOffer offer = (DemandOffer) tableModelOffers.getValueAt(row, 5);
				BLFacade facade = UserGUI.getBusinessLogic();
				boolean ok = facade.declineDemandOffer(demandId, offer.getOfferId(), username);
				if (ok) {
					labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.OfferDeclined"));
					reloadDemand();
				} else {
					labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.DeclineError"));
				}
			}
		});
		getContentPane().add(buttonDecline);

		JButton buttonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		buttonClose.setBounds(new Rectangle(564, 360, 160, 30));
		buttonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		getContentPane().add(buttonClose);
	}

	private void buildOfferSection() {
		JLabel labelOfferProduct = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.OfferProduct"));
		labelOfferProduct.setBounds(new Rectangle(24, 360, 110, 20));
		getContentPane().add(labelOfferProduct);

		fieldOfferProduct.setBounds(new Rectangle(140, 360, 220, 24));
		getContentPane().add(fieldOfferProduct);

		JLabel labelOfferDescription = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.OfferDescription"));
		labelOfferDescription.setBounds(new Rectangle(24, 392, 120, 20));
		getContentPane().add(labelOfferDescription);

		fieldOfferDescription.setBounds(new Rectangle(140, 392, 220, 24));
		getContentPane().add(fieldOfferDescription);

		JLabel labelOfferPrice = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.OfferPrice"));
		labelOfferPrice.setBounds(new Rectangle(24, 424, 110, 20));
		getContentPane().add(labelOfferPrice);

		fieldOfferPrice.setBounds(new Rectangle(140, 424, 220, 24));
		getContentPane().add(fieldOfferPrice);

		JButton buttonOffer = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.MakeOffer"));
		buttonOffer.setBounds(new Rectangle(380, 392, 160, 30));
		buttonOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelMsg.setText("");
				String prod = fieldOfferProduct.getText();
				String desc = fieldOfferDescription.getText();
				String priceText = fieldOfferPrice.getText();
				if (prod == null || prod.isEmpty() || desc == null || desc.isEmpty() || priceText == null || priceText.isEmpty()) {
					labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.InvalidOffer"));
					return;
				}
				float price = 0;
				try {
					price = Float.parseFloat(priceText);
				} catch (Exception ex) {
					labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.InvalidOffer"));
					return;
				}
				if (price <= 0) {
					labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.InvalidOffer"));
					return;
				}
				BLFacade facade = UserGUI.getBusinessLogic();
				boolean ok = facade.addDemandOffer(demandId, username, prod, desc, price);
				if (ok) {
					labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.OfferCreated"));
					fieldOfferProduct.setText("");
					fieldOfferDescription.setText("");
					fieldOfferPrice.setText("");
				} else {
					labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.OfferError"));
				}
			}
		});
		getContentPane().add(buttonOffer);
	}

	private void reloadDemand() {
		BLFacade facade = UserGUI.getBusinessLogic();
		demand = facade.getDemand(demandId);
		if (demand == null) {
			labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.DemandClosed"));
			return;
		}
		if (!demand.isActive()) {
			labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowDemandGUI.DemandClosed"));
		}

		boolean isGuest = "Guest".equals(username);
		boolean isOwner = username.equals(demand.getUsername());

		fieldOfferProduct.setEnabled(!isGuest && !isOwner && demand.isActive());
		fieldOfferDescription.setEnabled(!isGuest && !isOwner && demand.isActive());
		fieldOfferPrice.setEnabled(!isGuest && !isOwner && demand.isActive());

		if (isOwner) {
			loadOffers();
		} else {
			tableModelOffers.setRowCount(0);
		}
	}

	private void loadOffers() {
		tableModelOffers.setRowCount(0);
		if (!demand.isActive()) {
			return;
		}
		List<DemandOffer> subscribed = new ArrayList<DemandOffer>();
		List<DemandOffer> regular = new ArrayList<DemandOffer>();
		for (DemandOffer offer : demand.getOffers()) {
			if (offer != null && offer.getSeller() != null && offer.getSeller().isSubscribed()) {
				subscribed.add(offer);
			} else if (offer != null) {
				regular.add(offer);
			}
		}
		List<DemandOffer> ordered = new ArrayList<DemandOffer>();
		ordered.addAll(subscribed);
		ordered.addAll(regular);

		for (DemandOffer offer : ordered) {
			Vector<Object> row = new Vector<Object>();
			row.add(offer.getProduct());
			row.add(offer.getDescription());
			String sellerName = offer.getSeller() != null ? offer.getSeller().getUsername() : "-";
			if (offer.getSeller() != null && offer.getSeller().isSubscribed()) {
				sellerName = "+ " + sellerName;
			}
			row.add(sellerName);
			row.add(offer.getPrice());
			row.add(offer.getOfferDate());
			row.add(offer);
			tableModelOffers.addRow(row);
		}
	}
}
