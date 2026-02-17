package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.User;

public class RegisteredGUI extends JFrame {
	
    private String username;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonLogOut = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	private JLabel jLabelSelectOption;
	private DefaultComboBoxModel<String> lenguageMod = new DefaultComboBoxModel<String>();
	private JFrame thisRef = this;
	
    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade facade){
		appFacadeInterface=facade;
	}


	
	/**
	 * This is the default constructor
	 */
	public RegisteredGUI(User u) {
		super();
		setBounds(580, 280, 500, 300);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle") +": " + ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Guest"));
		setAlwaysOnTop(true);
		username = u.getUsername();
		
		
		//Panel hasieraketa
		jContentPane = new JPanel();
		jContentPane.setLayout(null);
		setContentPane(jContentPane);
		
		
		//Titulua
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		jLabelSelectOption.setBounds(30, 13, 125, 16);
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		//Kontutik ateratzeko botoia
		jButtonLogOut = new JButton();
		jButtonLogOut.setBounds(344, 11, 125, 23);
		jButtonLogOut.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisteredGUI.LogOut"));
		jButtonLogOut.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame mainGUI = new MainGUI();
				mainGUI.setVisible(true);
				dispose();
			}
		});
		
		
		//CreateQueryGUI-ra joataeko botoia
		jButtonCreateQuery = new JButton();
		jButtonCreateQuery.setSize(217, 64);
		jButtonCreateQuery.setLocation(118, 75);
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisteredGUI.CreateSale"));
		jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				thisRef.setAlwaysOnTop(false);
				JFrame createsaleGUI = new CreateSaleGUI(username);
				createsaleGUI.setVisible(true);
			}
		});
		
		
		//QueryQueries pantailara joateko botoia
		jButtonQueryQueries = new JButton();
		jButtonQueryQueries.setBounds(118, 147, 217, 64);
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisteredGUI.QuerySales"));
		jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				thisRef.setAlwaysOnTop(false);
				JFrame querysalesGUI = new QuerySalesGUI();
				querysalesGUI.setVisible(true);
			}
		});
		
		
		//Lengoaiak aukeratzeko combobox-a
		JComboBox<String> lenguage = new JComboBox<String>(lenguageMod);
		lenguage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch (lenguage.getSelectedItem().toString()) {
				case "Euskara":
					Locale.setDefault(new Locale("eus"));
					paintAgain();
					break;
				case "English":
					Locale.setDefault(new Locale("en"));
					paintAgain();
					break;
				case "Español":
					Locale.setDefault(new Locale("es"));
					paintAgain();
					break;
				}
			}
		});
		lenguage.setBounds(349, 228, 125, 22);
		lenguageMod.addElement("Euskara");
		lenguageMod.addElement("English");
		lenguageMod.addElement("Español");
		lenguageMod.setSelectedItem("\uD83C\uDF10" + ResourceBundle.getBundle("Etiquetas").getString("Lenguage"));
		
		
		//Panelera gehitzeko aginduak
		jContentPane.add(jLabelSelectOption);
		jContentPane.add(jButtonLogOut);
		jContentPane.add(jButtonCreateQuery);
		jContentPane.add(jButtonQueryQueries);
		jContentPane.add(lenguage);

		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}
	
	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisteredGUI.QuerySales"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisteredGUI.CreateSale"));
		jButtonLogOut.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisteredGUI.LogOut"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("RegisteredGUI.MainTitle") + ": " + username);
	}
	
} // @jve:decl-index=0:visual-constraint="0,0"

