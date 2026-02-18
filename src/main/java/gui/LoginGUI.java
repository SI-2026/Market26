package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
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
	


	public LoginGUI(JFrame userGUIref) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(600, 300, 500, 300);
		final int frameWidth = 500;
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.MainTitle"));
		setAlwaysOnTop(true);

		
		//Panel hasieraketa
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//Erabiltzailea sartzeko titulua
		JLabelUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.UsernameLabel"));
		JLabelUsername.setBounds((frameWidth - 260) / 2, 32, 260, 16);
		JLabelUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
		JLabelUsername.setForeground(Color.BLACK);
		JLabelUsername.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		//Pasahitzak sartzeko tituluak
		JLabelPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.PasswordLabel"));
		JLabelPassword.setBounds((frameWidth - 260) / 2, 95, 260, 16);
		JLabelPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		JLabelPassword.setForeground(Color.BLACK);
		JLabelPassword.setHorizontalAlignment(SwingConstants.CENTER);
		

		//Erabiltzailea sartzeko hutsunea
		textField = new JTextField();
		textField.setBounds((frameWidth - 240) / 2, 56, 240, 24);
		textField.setColumns(10);
		
		
		//Pasahitza sartzeko hutsunea
		passwordField = new JPasswordField();
		passwordField.setBounds((frameWidth - 240) / 2, 119, 240, 24);

		
		//Enter ematerakoan botoiari clik egin
		getRootPane().setDefaultButton(btnLogin);
		passwordField.addActionListener(e -> btnLogin.doClick());

		
		//Informazio textua
		lblInfo = new JLabel("");
		lblInfo.setForeground(new Color(255, 0, 0));
		lblInfo.setBounds((frameWidth - 300) / 2, 154, 300, 16);
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		//Logeatzeko botoia
		btnLogin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.btnLogin"));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblInfo.setText("");
				BLFacade facade = UserGUI.getBusinessLogic();
				String username = textField.getText().toString();
				String pass =  new String(passwordField.getPassword());
				if (!username.isEmpty() && !pass.isEmpty()) {
					User u = facade.isRegistered(username, pass);
					if (u != null) {
						JFrame registeredGUI = new RegisteredGUI(u);
						registeredGUI.setVisible(true);
						userGUIref.dispose();
						dispose();
					} else {
						lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.WrongCredentials"));
					}
				} else {
					lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.EmptyFields"));
				}
			}
		});
		btnLogin.setBounds((frameWidth - 160) / 2, 178, 160, 30);
		
		
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
				userGUIref.setEnabled(true);
			}
		});
	}
}
