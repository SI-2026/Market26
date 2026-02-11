package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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


	public LoginGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabelUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.UsernameLabel"));
		JLabelUsername.setBounds(66, 34, 144, 14);
		JLabelUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
		JLabelUsername.setForeground(Color.BLACK);
		JLabelUsername.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabelPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.PasswordLabel"));
		JLabelPassword.setBounds(66, 97, 144, 14);
		JLabelPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		JLabelPassword.setForeground(Color.BLACK);
		JLabelPassword.setHorizontalAlignment(SwingConstants.LEFT);
		
		contentPane.add(JLabelPassword);
		contentPane.add(JLabelUsername);
		
		
		
		textField = new JTextField();
		textField.setBounds(124, 59, 200, 20);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(124, 122, 200, 20);

		contentPane.add(textField);
		contentPane.add(passwordField);

		
		
		btnLogin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.btnLogin")); //$NON-NLS-1$ //$NON-NLS-2$
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblInfo.setText("");
				BLFacade facade = MainGUI.getBusinessLogic();
				String username = textField.getText().toString();
				String pass =  new String(passwordField.getPassword());
				if (!username.isEmpty() || !pass.isEmpty()) {
					User u = facade.isRegistered(username, pass);
					if (u != null) {
						JFrame a = new RegisteredGUI(u);
						a.setVisible(true);
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
		contentPane.add(btnLogin);
		
		lblInfo = new JLabel("");
		lblInfo.setForeground(new Color(255, 0, 0));
		lblInfo.setBounds(124, 155, 234, 16);
		contentPane.add(lblInfo);
		
		
		
		
		
		

	}
}
