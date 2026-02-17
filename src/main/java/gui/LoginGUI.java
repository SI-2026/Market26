package gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import businessLogic.BLFacade;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

import domain.User;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel JLabelUsername = null;
	private JLabel JLabelPassword = null;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private JLabel lblInfo;
	


	public LoginGUI(JFrame mainGUIref) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(600, 300, 500, 300);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.MainTitle"));
		setAlwaysOnTop(true);

		
		//Panel hasieraketa
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//Erabiltzailea sartzeko titulua
		JLabelUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.UsernameLabel"));
		JLabelUsername.setBounds(66, 34, 144, 14);
		JLabelUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
		JLabelUsername.setForeground(Color.BLACK);
		JLabelUsername.setHorizontalAlignment(SwingConstants.LEFT);
		
		
		//Pasahitzak sartzeko tituluak
		JLabelPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.PasswordLabel"));
		JLabelPassword.setBounds(66, 97, 144, 14);
		JLabelPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		JLabelPassword.setForeground(Color.BLACK);
		JLabelPassword.setHorizontalAlignment(SwingConstants.LEFT);
		

		//Erabiltzailea sartzeko hutsunea
		textField = new JTextField();
		textField.setBounds(124, 59, 200, 20);
		textField.setColumns(10);
		
		
		//Pasahitza sartzeko hutsunea
		passwordField = new JPasswordField();
		passwordField.setBounds(124, 122, 200, 20);

		
		//Enter ematerakoan botoiari clik egin
		getRootPane().setDefaultButton(btnLogin);
		passwordField.addActionListener(e -> btnLogin.doClick());

		
		//Informazio textua
		lblInfo = new JLabel("");
		lblInfo.setForeground(new Color(255, 0, 0));
		lblInfo.setBounds(124, 155, 234, 16);
		
		
		//Logeatzeko botoia
		btnLogin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.btnLogin"));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblInfo.setText("");
				BLFacade facade = MainGUI.getBusinessLogic();
				String username = textField.getText().toString();
				String pass =  new String(passwordField.getPassword());
				if (!username.isEmpty() && !pass.isEmpty()) {
					User u = facade.isRegistered(username, pass);
					if (u != null) {
						JFrame registeredGUI = new RegisteredGUI(u);
						registeredGUI.setVisible(true);
						mainGUIref.dispose();
						dispose();
					} else {
						lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.WrongCredentials"));
					}
				} else {
					lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.EmptyFields"));
				}
			}
		});
		btnLogin.setBounds(149, 172, 144, 23);
		
		
		//Panelera gehitzeko aginduak
		contentPane.add(JLabelUsername);
		contentPane.add(JLabelPassword);
		contentPane.add(textField);
		contentPane.add(passwordField);
		contentPane.add(lblInfo);
		contentPane.add(btnLogin);
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainGUIref.setEnabled(true);
			}
		});
	}
}
