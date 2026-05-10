package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Movement;

public class MovementsGUI extends JFrame {

	private String username;
	private JFrame thisFrame;
	private JTable tableMovements = new JTable();
	private DefaultTableModel tableModelMovements;
	private List<Movement> movements;
	private JLabel jLabelMovements = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MovementsGUI.Movements"));
	private JLabel jLabelMsg = new JLabel();

	private String[] columnNames = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("MovementsGUI.From"),
			ResourceBundle.getBundle("Etiquetas").getString("MovementsGUI.To"),
			ResourceBundle.getBundle("Etiquetas").getString("MovementsGUI.Amount"),
			ResourceBundle.getBundle("Etiquetas").getString("MovementsGUI.Date") };

	public MovementsGUI(String username) {
		this.username = username;
		this.thisFrame = this;
		setSize(new Dimension(760, 460));
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MovementsGUI.Title"));
		getContentPane().setLayout(null);

		jLabelMovements.setBounds(32, 26, 420, 16);
		getContentPane().add(jLabelMovements);

		JScrollPane scrollPaneMovements = new JScrollPane();
		scrollPaneMovements.setBounds(new Rectangle(32, 57, 690, 280));
		getContentPane().add(scrollPaneMovements);

		tableModelMovements = new DefaultTableModel(null, columnNames);
		tableMovements.setModel(tableModelMovements);
		tableMovements.setEnabled(false);
		scrollPaneMovements.setViewportView(tableMovements);

		tableMovements.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2) {
					int row = tableMovements.rowAtPoint(mouseEvent.getPoint());
					if (row < 0 || movements == null || row >= movements.size()) {
						return;
					}
					Movement movement = movements.get(row);
					if (movement == null) {
						return;
					}
					String buyer = movement.getNondik();
					String seller = movement.getNora();
					if (!username.equals(buyer) || seller == null || "SYSTEM_FUNDS".equals(seller) || "BANK".equals(seller)) {
						jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.ClaimError"));
						return;
					}
					String description = JOptionPane.showInputDialog(
							MovementsGUI.this,
							ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.ClaimPrompt"),
							ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.MakeClaim"),
							JOptionPane.PLAIN_MESSAGE);
					if (description == null || description.isEmpty()) {
						jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.InvalidClaim"));
						return;
					}
					BLFacade facade = UserGUI.getBusinessLogic();
					boolean ok = facade.makeClaim(description, seller, username);
					if (ok) {
						jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.ClaimCreated"));
						jLabelMsg.setForeground(new Color(0, 128, 0));
					} else {
						jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.ClaimError"));
						jLabelMsg.setForeground(Color.RED);
					}
				}
			}
		});

		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setBounds(32, 356, 130, 30);
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);
			}
		});
		getContentPane().add(jButtonClose);

		jLabelMsg.setBounds(182, 361, 540, 20);
		jLabelMsg.setForeground(Color.RED);
		getContentPane().add(jLabelMsg);

		BLFacade facade = UserGUI.getBusinessLogic();
		movements = facade.getMovements(username);
		if (movements == null || movements.isEmpty()) {
			jLabelMovements.setText(ResourceBundle.getBundle("Etiquetas").getString("MovementsGUI.NoMovements"));
			return;
		}
	        for (Movement movement : movements) {
			Vector<Object> row = new Vector<Object>();
			row.add(movement.getNondik());
			row.add(movement.getNora());
			row.add(movement.getKopurua());
			row.add(movement.getDate());
			tableModelMovements.addRow(row);
		}
	}
}
