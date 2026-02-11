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

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel JLabelUsername = null;
	private JLabel JLabelPassword = null;
	private JLabel JLabelPasswordRepeat = null;

	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldRepeat;
	private JButton btnSignUp;
	private JLabel lblInfo;



	public RegisterGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabelUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.UsernameLabel"));
		JLabelUsername.setBounds(66, 11, 144, 14);
		JLabelUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
		JLabelUsername.setForeground(Color.BLACK);
		JLabelUsername.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabelPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.PasswordLabel"));
		JLabelPassword.setBounds(66, 67, 144, 14);
		JLabelPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		JLabelPassword.setForeground(Color.BLACK);
		JLabelPassword.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabelPasswordRepeat = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.PasswordLabelRepeat"));
		JLabelPasswordRepeat.setBounds(66, 116, 200, 14);
		JLabelPasswordRepeat.setFont(new Font("Tahoma", Font.BOLD, 13));
		JLabelPasswordRepeat.setForeground(Color.BLACK);
		JLabelPasswordRepeat.setHorizontalAlignment(SwingConstants.LEFT);
		
		
		contentPane.add(JLabelUsername);
		contentPane.add(JLabelPassword);
		contentPane.add(JLabelPasswordRepeat);

		
		
		
		textField = new JTextField();
		textField.setBounds(124, 36, 200, 20);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(124, 92, 200, 20);
		
		passwordFieldRepeat = new JPasswordField();
		passwordFieldRepeat.setBounds(124, 141, 200, 20);

		contentPane.add(textField);
		contentPane.add(passwordField);
		contentPane.add(passwordFieldRepeat);

		lblInfo = new JLabel("");
		lblInfo.setForeground(new Color(255, 0, 0));
		lblInfo.setBounds(124, 174, 234, 16);
		contentPane.add(lblInfo);
		
		
		btnSignUp = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.btnSignUp")); //$NON-NLS-1$ //$NON-NLS-2$
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = textField.getText().toString();
				String pass =  new String(passwordField.getPassword());
				String passr =  new String(passwordFieldRepeat.getPassword());
				
				if (pass != null && passr != null && pass.equals(passr)) {
					BLFacade facade = MainGUI.getBusinessLogic();

					if (!username.isEmpty() || !pass.isEmpty()) {
						User u = facade.register(username, pass);
						if (u != null) {
							JFrame a = new RegisteredGUI(u);
							a.setVisible(true);
							dispose();
						} else {
							lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.UserAlreadyExists"));
						}
					} else {
						lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.EmptyFields"));
					}

				} else {
					lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.DiferentPasswords"));
				}
				
			}
		});
		btnSignUp.setBounds(149, 191, 144, 23);
		contentPane.add(btnSignUp);
		
	}
}
