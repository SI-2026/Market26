package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
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
import domain.Movement;

public class MovementsGUI extends JFrame {

	private String username;
	private JFrame thisFrame;
	private JTable tableMovements = new JTable();
	private DefaultTableModel tableModelMovements;
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
		List<Movement> movements = facade.getMovements(username);
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
