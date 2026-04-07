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
import domain.Claim;
import domain.Offer;
import domain.Sale;
import domain.User;

public class SellerOffersGUI extends JFrame {

	private String username;
	private JFrame thisFrame;
	private JTable tableOffers = new JTable();
	private DefaultTableModel tableModelOffers;
	private JTable tableClaims = new JTable();
	private DefaultTableModel tableModelClaims;
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelOffers = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Offers"));
	private JLabel jLabelClaims = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Claims"));

	private String[] columnNames = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Title"),
			ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Buyer"),
			ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Amount"),
			ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Date") };

	private String[] claimColumnNames = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Buyer"),
			ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.ClaimDescription"),
			ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Date") };

	public SellerOffersGUI(String username) {
		this.username = username;
		this.thisFrame = this;

		setSize(new Dimension(760, 640));
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.Title"));
		getContentPane().setLayout(null);

		jLabelOffers.setBounds(32, 26, 420, 16);
		getContentPane().add(jLabelOffers);

		JScrollPane scrollPaneOffers = new JScrollPane();
		scrollPaneOffers.setBounds(new Rectangle(32, 57, 690, 230));
		getContentPane().add(scrollPaneOffers);

		tableModelOffers = new DefaultTableModel(null, columnNames);
		tableOffers.setModel(tableModelOffers);
		tableOffers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		scrollPaneOffers.setViewportView(tableOffers);

		tableModelOffers.setColumnCount(6);
		tableOffers.getColumnModel().removeColumn(tableOffers.getColumnModel().getColumn(5));
		tableOffers.getColumnModel().removeColumn(tableOffers.getColumnModel().getColumn(4));

		jLabelClaims.setBounds(32, 308, 420, 16);
		getContentPane().add(jLabelClaims);

		JScrollPane scrollPaneClaims = new JScrollPane();
		scrollPaneClaims.setBounds(new Rectangle(32, 335, 690, 180));
		getContentPane().add(scrollPaneClaims);

		tableModelClaims = new DefaultTableModel(null, claimColumnNames);
		tableClaims.setModel(tableModelClaims);
		tableClaims.setEnabled(false);
		scrollPaneClaims.setViewportView(tableClaims);

		JButton jButtonAccept = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.AcceptOffer"));
		jButtonAccept.setBounds(32, 532, 170, 30);
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
				boolean ok = facade.acceptOffer(offer.getOfferId(), sale.getSaleNumber(), username);
				if (ok) {
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.OfferAccepted"));
					refresh();
				} else {
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.AcceptOfferError"));
				}
			}
		});
		getContentPane().add(jButtonAccept);

		JButton jButtonDecline = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.DeclineOffer"));
		jButtonDecline.setBounds(214, 532, 170, 30);
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
				boolean ok = facade.declinedOffer(sale.getSaleNumber(), offer.getOfferId());
				if (ok) {
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.OfferDeclined"));
					refresh();
				} else {
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.DeclineOfferError"));
				}
			}
		});
		getContentPane().add(jButtonDecline);

		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setBounds(396, 532, 130, 30);
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);
			}
		});
		getContentPane().add(jButtonClose);

		jLabelMsg.setBounds(32, 572, 680, 20);
		jLabelMsg.setForeground(Color.RED);
		getContentPane().add(jLabelMsg);

		refresh();


	}
	public void refresh() {
		BLFacade facade = UserGUI.getBusinessLogic();
		User seller = facade.getUser(username);

        		int kont = 0;
		for (Sale sale : seller.getSales()) {
			if (sale.isSold()) {
				continue;
			}
			List<Offer> offers = sale.getOfferList();
			if (offers.isEmpty()) {
				continue;
			}
			for (Offer offer : offers) {
				Vector<Object> row = new Vector<Object>();
				row.add(sale.getTitle());
				row.add(offer.getBuyer() != null ? offer.getBuyer().getUsername() : "-");
				row.add(offer.getOffer());
				row.add(offer.getOfferDate());
				row.add(sale);
				row.add(offer);
				tableModelOffers.addRow(row);
				kont++;
			}
		}

        if(seller.getClaims().isEmpty()) {
			jLabelClaims.setText(ResourceBundle.getBundle("Etiquetas").getString("SellerOffersGUI.NoClaims"));
		} else {
            kont = 0;
		    for (Claim claim : seller.getClaims()) {
                Vector<Object> row = new Vector<Object>();
                row.add(claim.getBuyer());
                row.add(claim.getDescription());
                row.add(claim.getDate());
                tableModelClaims.addRow(row);
                kont++;
		    }
        }
	}
}
