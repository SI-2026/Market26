package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
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
import domain.Review;
import domain.Sale;

public class ReviewsGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JTable tableReviews = new JTable();
	private DefaultTableModel tableModelReviews;
	private final JLabel labelTitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ReviewsGUI.Title"));
	private final JLabel labelMsg = new JLabel();
	private final JFrame thisFrame;

	private final String[] columnNames = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("ReviewsGUI.Buyer"),
			ResourceBundle.getBundle("Etiquetas").getString("ReviewsGUI.Rating"),
			ResourceBundle.getBundle("Etiquetas").getString("ReviewsGUI.Comment"),
			ResourceBundle.getBundle("Etiquetas").getString("ReviewsGUI.Date") };

	public ReviewsGUI(Sale sale) {
		thisFrame = this;
		setSize(new Dimension(620, 380));
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("ReviewsGUI.Title"));
		getContentPane().setLayout(null);

		labelTitle.setBounds(20, 20, 520, 16);
		getContentPane().add(labelTitle);

		JScrollPane scrollPaneReviews = new JScrollPane();
		scrollPaneReviews.setBounds(new Rectangle(20, 50, 570, 220));
		getContentPane().add(scrollPaneReviews);

		tableModelReviews = new DefaultTableModel(null, columnNames);
		tableReviews.setModel(tableModelReviews);
		tableReviews.setEnabled(false);
		scrollPaneReviews.setViewportView(tableReviews);

		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setBounds(20, 290, 130, 30);
		jButtonClose.addActionListener(e -> thisFrame.setVisible(false));
		getContentPane().add(jButtonClose);

		labelMsg.setBounds(170, 295, 420, 20);
		labelMsg.setForeground(Color.RED);
		getContentPane().add(labelMsg);

		loadReviews(sale);
	}

	private void loadReviews(Sale sale) {
		if (sale == null) {
			labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ReviewsGUI.NoReviews"));
			return;
		}

		BLFacade facade = UserGUI.getBusinessLogic();
		List<Review> reviews = facade.getReviewsForSale(sale.getSaleNumber());
		if (reviews == null || reviews.isEmpty()) {
			labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ReviewsGUI.NoReviews"));
			return;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		for (Review review : reviews) {
			Vector<Object> row = new Vector<Object>();
			row.add(review.getBuyer() != null ? review.getBuyer().getUsername() : "-");
			row.add(review.getRating());
			row.add(review.getComment());
			row.add(review.getReviewDate() != null ? dateFormat.format(review.getReviewDate()) : "-");
			tableModelReviews.addRow(row);
		}
	}
}
