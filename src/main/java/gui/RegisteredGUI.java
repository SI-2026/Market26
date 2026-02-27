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
	private JButton jButtonProfile = null;
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
		final int frameWidth = 500;
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle") +": " + ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Guest"));
		setAlwaysOnTop(true);
		username = u.getUsername();
		
		
		//Panel hasieraketa
		jContentPane = new JPanel();
		jContentPane.setLayout(null);
		setContentPane(jContentPane);
		
		
		//Titulua
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		jLabelSelectOption.setBounds((frameWidth - 220) / 2, 16, 220, 16);
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		//Kontutik ateratzeko botoia
		jButtonProfile = new JButton();
		jButtonProfile.setBounds(frameWidth - 145, 11, 115, 25);
		jButtonProfile.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisteredGUI.Profile"));
		jButtonProfile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				thisRef.setAlwaysOnTop(false);
				JFrame profileGUI = new ProfileGUI(thisRef, username);
				profileGUI.setVisible(true);
			}
		});
		
		
		//CreateQueryGUI-ra joataeko botoia
		jButtonCreateQuery = new JButton();
		jButtonCreateQuery.setBounds((frameWidth - 240) / 2, 66, 240, 58);
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
		jButtonQueryQueries.setBounds((frameWidth - 240) / 2, 133, 240, 58);
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisteredGUI.QuerySales"));
		jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				thisRef.setAlwaysOnTop(false);
				JFrame querysalesGUI = new QuerySalesGUI(username);
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
		lenguage.setBounds((frameWidth - 160) / 2, 221, 160, 24);
		lenguageMod.addElement("Euskara");
		lenguageMod.addElement("English");
		lenguageMod.addElement("Español");
		lenguageMod.setSelectedItem("\uD83C\uDF10" + ResourceBundle.getBundle("Etiquetas").getString("Lenguage"));
		
		
		//Panelera gehitzeko aginduak
		jContentPane.add(jLabelSelectOption);
		jContentPane.add(jButtonProfile);
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
		jButtonProfile.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisteredGUI.Profile"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("RegisteredGUI.MainTitle") + ": " + username);
	}
	
} // @jve:decl-index=0:visual-constraint="0,0"

