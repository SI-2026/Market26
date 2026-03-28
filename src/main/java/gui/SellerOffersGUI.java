package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Offer;
import domain.Sale;
import domain.User;

public class SellerOffersGUI extends JFrame {

	private String username;
	private JFrame thisFrame;
	private JTable tableOffers = new JTable();
	private DefaultTableModel tableModelOffers;
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelOffers = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Offers"));

	private final String[] columnNames = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Title"),
			ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Buyer"),
			ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Amount"),
			ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Date") };

	public SellerOffersGUI(String username) {
		this.username = username;
		this.thisFrame = this;

		setSize(new Dimension(760, 460));
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Title"));
		getContentPane().setLayout(null);

		jLabelOffers.setBounds(32, 26, 420, 16);
		getContentPane().add(jLabelOffers);

		JScrollPane scrollPaneOffers = new JScrollPane();
		scrollPaneOffers.setBounds(new Rectangle(32, 57, 690, 260));
		getContentPane().add(scrollPaneOffers);

		tableModelOffers = new DefaultTableModel(null, columnNames);
		tableOffers.setModel(tableModelOffers);
		tableOffers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		scrollPaneOffers.setViewportView(tableOffers);

		tableModelOffers.setColumnCount(6);
		tableOffers.getColumnModel().removeColumn(tableOffers.getColumnModel().getColumn(5));
		tableOffers.getColumnModel().removeColumn(tableOffers.getColumnModel().getColumn(4));

		JButton jButtonAccept = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.AcceptOffer"));
		jButtonAccept.setBounds(32, 339, 170, 30);
		jButtonAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableOffers.getSelectedRow();
				if (row < 0) {
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.SelectOffer"));
					return;
				}

				Sale sale = (Sale) tableModelOffers.getValueAt(row, 4);
				Offer offer = (Offer) tableModelOffers.getValueAt(row, 5);
				BLFacade facade = UserGUI.getBusinessLogic();
				boolean ok = facade.acceptOffer(offer, sale.getSaleNumber(), username);
				if (ok) {
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.OfferAccepted"));
					loadOffers();
				} else {
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.AcceptOfferError"));
				}
			}
		});
		getContentPane().add(jButtonAccept);

		JButton jButtonDecline = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.DeclineOffer"));
		jButtonDecline.setBounds(214, 339, 170, 30);
		jButtonDecline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableOffers.getSelectedRow();
				if (row < 0) {
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.SelectOffer"));
					return;
				}

				Sale sale = (Sale) tableModelOffers.getValueAt(row, 4);
				Offer offer = (Offer) tableModelOffers.getValueAt(row, 5);
				BLFacade facade = UserGUI.getBusinessLogic();
				boolean ok = facade.declinedOffer(sale.getSaleNumber(), offer);
				if (ok) {
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.OfferDeclined"));
					loadOffers();
				} else {
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.DeclineOfferError"));
				}
			}
		});
		getContentPane().add(jButtonDecline);

		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setBounds(396, 339, 130, 30);
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);
			}
		});
		getContentPane().add(jButtonClose);

		jLabelMsg.setBounds(32, 384, 620, 20);
		jLabelMsg.setForeground(Color.RED);
		getContentPane().add(jLabelMsg);

		loadOffers();
	}

	private void loadOffers() {
		tableModelOffers.setDataVector(null, columnNames);
		tableModelOffers.setColumnCount(6);
		BLFacade facade = UserGUI.getBusinessLogic();
		User seller = facade.getUser(username);
		if (seller == null || seller.getSales() == null) {
			jLabelOffers.setText(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.NoOffers"));
			removeHiddenColumns();
			return;
		}

		int rows = 0;
		for (Sale sale : seller.getSales()) {
			if (sale == null || sale.isSold()) {
				continue;
			}
			List<Offer> offers = sale.getOfferList();
			if (offers == null || offers.isEmpty()) {
				continue;
			}
			for (Offer offer : offers) {
				if (offer == null) {
					continue;
				}
				Vector<Object> row = new Vector<Object>();
				row.add(sale.getTitle());
				row.add(offer.getBuyer() != null ? offer.getBuyer().getUsername() : "-");
				row.add(offer.getOffer());
				Date offerDate = offer.getOfferDate();
				row.add(offerDate != null ? new SimpleDateFormat("dd-MM-yyyy").format(offerDate) : "-");
				row.add(sale);
				row.add(offer);
				tableModelOffers.addRow(row);
				rows++;
			}
		}

		if (rows == 0) {
			jLabelOffers.setText(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.NoOffers"));
		} else {
			jLabelOffers.setText(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Offers"));
		}

		removeHiddenColumns();
	}

	private void removeHiddenColumns() {
		if (tableOffers.getColumnCount() == 6) {
			tableOffers.getColumnModel().removeColumn(tableOffers.getColumnModel().getColumn(5));
			tableOffers.getColumnModel().removeColumn(tableOffers.getColumnModel().getColumn(4));
		}
	}
}
