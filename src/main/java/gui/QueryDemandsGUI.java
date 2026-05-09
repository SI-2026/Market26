package gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
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

public class QueryDemandsGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private final String username;
	private final JLabel jLabelDemands = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QueryDemandsGUI.Demands"));
	private final JButton jButtonSearch = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryDemandsGUI.Search"));
	private final JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private final JButton jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryDemandsGUI.Create"));
	private final JScrollPane scrollPanelDemands = new JScrollPane();
	private final JTable tableDemands = new JTable();
	private DefaultTableModel tableModelDemands;
	private final JTextField jTextFieldSearch = new JTextField();
	private final JFrame thisFrame;

	private final String[] columnNamesDemands = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Product"),
			ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Description"),
			ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Date"),
			ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Owner")
	};

	public QueryDemandsGUI(String username) {
		this.username = username;
		thisFrame = this;
		tableDemands.setEnabled(true);
		getContentPane().setLayout(null);
		setSize(new Dimension(760, 520));
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryDemandsGUI.Title"));

		jLabelDemands.setBounds(52, 108, 520, 16);
		getContentPane().add(jLabelDemands);

		jButtonClose.setBounds(new Rectangle(240, 420, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);
			}
		});
		getContentPane().add(jButtonClose);

		jButtonCreate.setBounds(new Rectangle(390, 420, 160, 30));
		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!"Guest".equals(QueryDemandsGUI.this.username)) {
					JFrame create = new DemandsGUI(QueryDemandsGUI.this.username);
					create.setVisible(true);
				}
			}
		});
		if ("Guest".equals(username)) {
			jButtonCreate.setEnabled(false);
		}
		getContentPane().add(jButtonCreate);

		scrollPanelDemands.setBounds(new Rectangle(52, 137, 650, 230));
		scrollPanelDemands.setViewportView(tableDemands);
		tableModelDemands = new DefaultTableModel(null, columnNamesDemands) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableDemands.setModel(tableModelDemands);
		tableModelDemands.setDataVector(null, columnNamesDemands);
		tableModelDemands.setColumnCount(5);
		if (tableDemands.getColumnModel().getColumnCount() > 4) {
			tableDemands.getColumnModel().removeColumn(tableDemands.getColumnModel().getColumn(4));
		}
		getContentPane().add(scrollPanelDemands);

		jTextFieldSearch.setBounds(52, 56, 357, 26);
		getContentPane().add(jTextFieldSearch);

		jButtonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reloadDemands();
			}
		});
		jButtonSearch.setBounds(427, 56, 117, 29);
		getContentPane().add(jButtonSearch);

		tableDemands.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2) {
					JTable table = (JTable) mouseEvent.getSource();
					int row = table.rowAtPoint(mouseEvent.getPoint());
					Demand demand = (Demand) tableModelDemands.getValueAt(row, 4);
					JFrame offerDemand = new OfferDemandGUI(demand, username);
					offerDemand.setVisible(true);
				}
			}
		});

		reloadDemands();
	}

	private void reloadDemands() {
		tableModelDemands.setDataVector(null, columnNamesDemands);
		tableModelDemands.setColumnCount(5);
		if (tableDemands.getColumnModel().getColumnCount() > 4) {
			tableDemands.getColumnModel().removeColumn(tableDemands.getColumnModel().getColumn(4));
		}

		BLFacade facade = UserGUI.getBusinessLogic();
		List<Demand> demands = facade.getDemands(jTextFieldSearch.getText());
		if (demands == null || demands.isEmpty()) {
			jLabelDemands.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryDemandsGUI.NoDemands"));
		} else {
			jLabelDemands.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryDemandsGUI.Demands"));
			for (Demand demand : demands) {
				Vector<Object> row = new Vector<Object>();
				row.add(demand.getProd());
				row.add(demand.getDescription());
				row.add(new SimpleDateFormat("dd-MM-yyyy").format(demand.getPubDate()));
				row.add(demand.getUsername());
				row.add(demand);
				tableModelDemands.addRow(row);
			}
		}

		if (tableDemands.getColumnModel().getColumnCount() > 4) {
			tableDemands.getColumnModel().removeColumn(tableDemands.getColumnModel().getColumn(4));
		}
	}
}
