package gui;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class ProfileGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblUsername;
	private JTextArea descriptionPane;
	private JButton jButtonLogOut;

	public ProfileGUI(JFrame registeredRef, String username) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(1000, 340, 400, 250);
		final int frameWidth = 400;
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.MainTitle") + ": " + username);
		setAlwaysOnTop(true);
		
		
		//Panel hasieraketa
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//Username title
		lblUsername = new JLabel(username);
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds((frameWidth - 220) / 2, 24, 220, 20);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 13));

		
		
		//Description area
		descriptionPane = new JTextArea();
		descriptionPane.setEditable(false);
		descriptionPane.setText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		descriptionPane.setBounds((frameWidth - 300) / 2, 90, 300, 42);
		
		
		jButtonLogOut = new JButton();
		jButtonLogOut.setBounds((frameWidth - 150) / 2, 160, 150, 36);
		jButtonLogOut.setText(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.LogOut"));
		jButtonLogOut.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame mainGUI = new UserGUI();
				mainGUI.setVisible(true);
				registeredRef.dispose();
				dispose();
			}
		});
		
		
		//Panelera gehitzeko aginduak
		contentPane.add(lblUsername);
		contentPane.add(jButtonLogOut);
		contentPane.add(descriptionPane);

		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				registeredRef.setEnabled(true);
			}
		});
	}
}
