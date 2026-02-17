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



	public RegisterGUI(JFrame mainGUIref) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(600, 300, 500, 300);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.MainTitle"));
		setAlwaysOnTop(true);

		
		//Panel hasieraketa
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//Erabiltzailea sartzeko titulua
		JLabelUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.UsernameLabel"));
		JLabelUsername.setBounds(66, 11, 144, 14);
		JLabelUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
		JLabelUsername.setForeground(Color.BLACK);
		JLabelUsername.setHorizontalAlignment(SwingConstants.LEFT);
		
		
		//Pasahitzak sartzeko tituluak
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
		
		
		//Erabiltzailea sartzeko hutsunea
		textField = new JTextField();
		textField.setBounds(124, 36, 200, 20);
		textField.setColumns(10);
		
		
		//Pasahitzak sartzeko hutsuneak
		passwordField = new JPasswordField();
		passwordField.setBounds(124, 92, 200, 20);
		
		passwordFieldRepeat = new JPasswordField();
		passwordFieldRepeat.setBounds(124, 141, 200, 20);

		
		//Enter ematerakoan botoiari clik egin
		getRootPane().setDefaultButton(btnSignUp);
		passwordFieldRepeat.addActionListener(e -> btnSignUp.doClick());
		
		
		//Informazio textua
		lblInfo = new JLabel("");
		lblInfo.setForeground(new Color(255, 0, 0));
		lblInfo.setBounds(124, 174, 234, 16);
		
		
		//Erregistratzeko botoia
		btnSignUp = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.btnSignUp"));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = textField.getText().toString();
				String pass =  new String(passwordField.getPassword());
				String passr =  new String(passwordFieldRepeat.getPassword());
				
				if (!pass.isEmpty() && !passr.isEmpty() && !username.isEmpty()) {
					if (pass.equals(passr)) {
						BLFacade facade = MainGUI.getBusinessLogic();
						User u = facade.register(username, pass);
						if (u != null) {
							JFrame registeredGUI = new RegisteredGUI(u);
							registeredGUI.setVisible(true);
							mainGUIref.dispose();
							dispose();
						} else {
							lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.UserAlreadyExists"));
						}
					} else {
						lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.DiferentPasswords"));
					}
				} else {
					lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.EmptyFields"));
				}
				
			}
		});
		btnSignUp.setBounds(149, 191, 144, 23);
		
		
		//Panelera gehitzeko aginduak
		contentPane.add(JLabelUsername);
		contentPane.add(JLabelPassword);
		contentPane.add(JLabelPasswordRepeat);
		contentPane.add(textField);
		contentPane.add(passwordField);
		contentPane.add(passwordFieldRepeat);
		contentPane.add(lblInfo);
		contentPane.add(btnSignUp);
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainGUIref.setEnabled(true);
			}
		});
	}
}
