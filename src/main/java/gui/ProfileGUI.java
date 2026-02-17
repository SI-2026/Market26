package gui;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class ProfileGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblUsername;
	private JTextPane descriptionPane;
	private JButton jButtonLogOut;

	public ProfileGUI(JFrame registeredRef, String username) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(1000, 340, 400, 250);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.MainTitle") + ": " + username);
		setAlwaysOnTop(true);
		
		
		//Panel hasieraketa
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//Username title
		lblUsername = new JLabel(username);
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(108, 11, 153, 14);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 13));

		
		
		//Description area
		descriptionPane = new JTextPane();
		descriptionPane.setEditable(false);
		descriptionPane.setBounds(46, 70, 295, 84);
		
		
		jButtonLogOut = new JButton();
		jButtonLogOut.setBounds(119, 164, 126, 36);
		jButtonLogOut.setText(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.LogOut"));
		jButtonLogOut.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame mainGUI = new MainGUI();
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
