package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
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
import domain.Sale;
import domain.User;

public class CartGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private final String username;
	private final JLabel jLabelTitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CartGUI.Title"));
	private final JLabel jLabelAmount = new JLabel();
	private final JLabel jLabelMsg = new JLabel();
	private final JTable tableCart = new JTable();
	private DefaultTableModel tableModelCart;
	private final JScrollPane scrollPaneCart = new JScrollPane();
	private final JButton jButtonPay = new JButton();
	private final JButton jButtonClear = new JButton();
	private final JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	private final String[] columnNames = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Title"),
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Price"),
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.PublicationDate")
	};

	public CartGUI(String username) {
		this.username = username;
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("CartGUI.Title") + ": " + username);
		setSize(new Dimension(760, 500));
		getContentPane().setLayout(null);

		jLabelTitle.setBounds(30, 20, 300, 20);
		getContentPane().add(jLabelTitle);

		scrollPaneCart.setBounds(30, 55, 680, 260);
		getContentPane().add(scrollPaneCart);

		tableModelCart = new DefaultTableModel(null, columnNames);
		tableCart.setModel(tableModelCart);
		tableCart.setEnabled(false);
		scrollPaneCart.setViewportView(tableCart);

		jLabelAmount.setBounds(30, 330, 420, 20);
		getContentPane().add(jLabelAmount);

		jLabelMsg.setBounds(30, 355, 680, 20);
		getContentPane().add(jLabelMsg);

		jButtonPay.setBounds(30, 390, 150, 30);
		jButtonPay.setText(ResourceBundle.getBundle("Etiquetas").getString("CartGUI.PayCart"));
		jButtonPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				payCart();
			}
		});
		getContentPane().add(jButtonPay);

		jButtonClear.setBounds(195, 390, 150, 30);
		jButtonClear.setText(ResourceBundle.getBundle("Etiquetas").getString("CartGUI.ClearCart"));
		jButtonClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = UserGUI.getBusinessLogic();
				if (facade.clearCart(username)) {
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CartGUI.Cleared"));
				} else {
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CartGUI.EmptyCart"));
				}
				reloadCart();
			}
		});
		getContentPane().add(jButtonClear);

		jButtonClose.setBounds(525, 390, 150, 30);
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		getContentPane().add(jButtonClose);

		reloadCart();
	}

	private void reloadCart() {
		BLFacade facade = UserGUI.getBusinessLogic();
		User user = facade.getUser(username);
		List<Sale> cartList = null;
		if (user != null && user.getCart() != null) {
			cartList = facade.getCartList(username);
		}

		tableModelCart.setRowCount(0);

		if (cartList == null || cartList.isEmpty()) {
			jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CartGUI.EmptyCart"));
		} else {
			jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CartGUI.CartLoaded"));
			for (Sale sale : cartList) {
				Vector<Object> row = new Vector<Object>();
				row.add(sale.getTitle());
				row.add(sale.getPrice());
				row.add(new SimpleDateFormat("dd-MM-yyyy").format(sale.getPublicationDate()));
				tableModelCart.addRow(row);
			}
		}
		jLabelAmount.setText(ResourceBundle.getBundle("Etiquetas").getString("CartGUI.Amount") + ": " + facade.getCartAmount(username));
	}

	private void payCart() {
		BLFacade facade = UserGUI.getBusinessLogic();
		List<Sale> cartList = facade.getCartList(username);
		if (cartList == null || cartList.isEmpty()) {
			jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CartGUI.EmptyCart"));
			return;
		}

		User user = facade.getUser(username);
		if (user == null) {
			jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CartGUI.PayError"));
			return;
		}

		for (Sale sale : cartList) {
			if (sale == null || sale.isSold() || sale.getSeller() == null || username.equals(sale.getSeller().getUsername())) {
				jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CartGUI.PayError"));
				return;
			}
			if (user.getDirua() < sale.getPrice()) {
				jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CartGUI.PayError"));
				return;
			}
			if (!facade.addOffer(sale.getPrice(), sale.getSaleNumber(), username)) {
				jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CartGUI.PayError"));
				return;
			}
		}

		facade.clearCart(username);
		jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CartGUI.PayOk"));
		reloadCart();
	}
}
