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


public class UserGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonLogin = null;
	private JButton jButtonRegister = null;
	private JButton jButtonQueryQueries = null;
	protected JLabel jLabelSelectOption;
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
	public UserGUI() {
		super();
		setBounds(580, 280, 500, 300);
		final int frameWidth = 500;
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle") +": " + ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Guest"));
		setAlwaysOnTop(true);
		
		
		//Panel hasieraketa
		jContentPane = new JPanel();
		jContentPane.setLayout(null);
		setContentPane(jContentPane);

		
		//Titulua
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		jLabelSelectOption.setBounds(30, 16, 220, 16);
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		//Login pantailara joateko botoia
		jButtonLogin = new JButton();
		jButtonLogin.setBounds(frameWidth - 272, 11, 115, 25);
		jButtonLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Login"));
		jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				thisRef.setAlwaysOnTop(false);
				thisRef.setEnabled(false);
				JFrame loginGUI = new LoginGUI(thisRef);
				loginGUI.setVisible(true);
			}
		});
		
		
		//Register pantailara joateko botoia
		jButtonRegister = new JButton();
		jButtonRegister.setBounds(frameWidth - 145, 11, 115, 25);
		jButtonRegister.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Register"));
		jButtonRegister.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				thisRef.setAlwaysOnTop(false);
				thisRef.setEnabled(false);
				JFrame registerGUI = new RegisterGUI(thisRef);
				registerGUI.setVisible(true);
			}
		});
		
		
		//QueryQueries pantailara joateko botoia
		jButtonQueryQueries = new JButton();
		jButtonQueryQueries.setBounds((frameWidth - 240) / 2, 96, 240, 64);
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QuerySales"));
		jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				thisRef.setAlwaysOnTop(false);
				JFrame a = new QuerySalesGUI("Guest");
				a.setVisible(true);
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
		jContentPane.add(jButtonLogin);
		jContentPane.add(jButtonRegister);
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
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QuerySales"));
		jButtonLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Login"));
		jButtonRegister.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Register"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ ": " + ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Guest"));
	}
} // @jve:decl-index=0:visual-constraint="0,0"

