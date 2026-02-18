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



	public RegisterGUI(JFrame userGUIref) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(600, 300, 500, 300);
		final int frameWidth = 500;
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.MainTitle"));
		setAlwaysOnTop(true);

		
		//Panel hasieraketa
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//Erabiltzailea sartzeko titulua
		JLabelUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.UsernameLabel"));
		JLabelUsername.setBounds((frameWidth - 280) / 2, 10, 280, 16);
		JLabelUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
		JLabelUsername.setForeground(Color.BLACK);
		JLabelUsername.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		//Pasahitzak sartzeko tituluak
		JLabelPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.PasswordLabel"));
		JLabelPassword.setBounds((frameWidth - 280) / 2, 63, 280, 16);
		JLabelPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		JLabelPassword.setForeground(Color.BLACK);
		JLabelPassword.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabelPasswordRepeat = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.PasswordLabelRepeat"));
		JLabelPasswordRepeat.setBounds((frameWidth - 280) / 2, 116, 280, 16);
		JLabelPasswordRepeat.setFont(new Font("Tahoma", Font.BOLD, 13));
		JLabelPasswordRepeat.setForeground(Color.BLACK);
		JLabelPasswordRepeat.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		//Erabiltzailea sartzeko hutsunea
		textField = new JTextField();
		textField.setBounds((frameWidth - 240) / 2, 33, 240, 24);
		textField.setColumns(10);
		
		
		//Pasahitzak sartzeko hutsuneak
		passwordField = new JPasswordField();
		passwordField.setBounds((frameWidth - 240) / 2, 86, 240, 24);
		
		passwordFieldRepeat = new JPasswordField();
		passwordFieldRepeat.setBounds((frameWidth - 240) / 2, 139, 240, 24);

		
		//Enter ematerakoan botoiari clik egin
		getRootPane().setDefaultButton(btnSignUp);
		passwordFieldRepeat.addActionListener(e -> btnSignUp.doClick());
		
		
		//Informazio textua
		lblInfo = new JLabel("");
		lblInfo.setForeground(new Color(255, 0, 0));
		lblInfo.setBounds((frameWidth - 300) / 2, 172, 300, 16);
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		//Erregistratzeko botoia
		btnSignUp = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.btnSignUp"));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = textField.getText().toString();
				String pass =  new String(passwordField.getPassword());
				String passr =  new String(passwordFieldRepeat.getPassword());
				
				if (!pass.isEmpty() && !passr.isEmpty() && !username.isEmpty()) {
					if (pass.equals(passr)) {
						BLFacade facade = UserGUI.getBusinessLogic();
						User u = facade.register(username, pass);
						if (u != null) {
							JFrame registeredGUI = new RegisteredGUI(u);
							registeredGUI.setVisible(true);
							userGUIref.dispose();
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
		btnSignUp.setBounds((frameWidth - 160) / 2, 194, 160, 30);
		
		
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
				userGUIref.setEnabled(true);
			}
		});
	}
}
