package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.User;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProfileGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblUsername;
	private JLabel lblBalance;
	private JLabel lblInfo;
	private JTextField txtAmount;
	private JButton jButtonAddMoney;
	private JButton jButtonWithdrawMoney;
	private JButton jButtonLogOut;
	private final String username;

	public ProfileGUI(JFrame registeredRef, String username) {
		this.username = username;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(920, 300, 460, 340);
		final int frameWidth = 460;
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

		
		
		lblBalance = new JLabel();
		lblBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblBalance.setBounds(72, 43, 320, 20);
		lblBalance.setFont(new Font("Tahoma", Font.BOLD, 14));

		txtAmount = new JTextField();
		txtAmount.setBounds(120, 81, 220, 28);
		txtAmount.setColumns(10);

		jButtonAddMoney = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.AddMoney"));
		jButtonAddMoney.setBounds(120, 120, 220, 32);
		jButtonAddMoney.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Float amount = parseAmount();
				if (amount == null || amount <= 0) {
					lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.InvalidAmount"));
					lblInfo.setForeground(Color.RED);
					return;
				}
				BLFacade facade = UserGUI.getBusinessLogic();
				facade.addMoney(amount, username);
				refreshBalance(username);
				lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.AddedMoney"));
				lblInfo.setForeground(new Color(0, 128, 0));
			}
		});

		jButtonWithdrawMoney = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.WithdrawMoney"));
		jButtonWithdrawMoney.setBounds(120, 163, 220, 32);
		jButtonWithdrawMoney.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Float amount = parseAmount();
				if (amount == null || amount <= 0) {
					lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.InvalidAmount"));
					lblInfo.setForeground(Color.RED);
					return;
				}
				BLFacade facade = UserGUI.getBusinessLogic();
				boolean b = facade.takeOutMoney(amount, username);
				refreshBalance(username);
				if (b) {
					lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.WithdrawOk"));
					lblInfo.setForeground(new Color(0, 128, 0));
				} else {
					lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.WithdrawError"));
					lblInfo.setForeground(Color.RED);
				}
			}
		});

		lblInfo = new JLabel("");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setBounds(50, 238, 360, 20);
		
		
		jButtonLogOut = new JButton();
		jButtonLogOut.setBounds((frameWidth - 170) / 2, 265, 170, 32);
		jButtonLogOut.setText(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.LogOut"));
		jButtonLogOut.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame userGUI = new UserGUI();
				userGUI.setVisible(true);
				registeredRef.dispose();
				dispose();
			}
		});
		
		JButton JButtonMovements = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.Movements"));
		JButtonMovements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame movementsGUI = new MovementsGUI(username);
				movementsGUI.setVisible(true);
			}
		});
		JButtonMovements.setBounds(120, 206, 220, 33);
		
		
		//Panelera gehitzeko aginduak
		contentPane.add(lblUsername);
		contentPane.add(lblBalance);
		contentPane.add(txtAmount);
		contentPane.add(jButtonAddMoney);
		contentPane.add(jButtonWithdrawMoney);
		contentPane.add(lblInfo);
		contentPane.add(jButtonLogOut);
		contentPane.add(JButtonMovements);
	
		

		refreshBalance(username);

		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				registeredRef.setEnabled(true);
			}
		});
	}

	private Float parseAmount() {
		String text = txtAmount.getText();
		if (text == null) {
			return null;
		}

		if (text.isEmpty()) {
			return null;
		}

		try {
			return Float.parseFloat(text);
		} catch (Exception e) {
			return null;
		}
	}

	private void refreshBalance(String username) {
		BLFacade facade = UserGUI.getBusinessLogic();
		User user = facade.getUser(username);
		float balance = 0;
		if (user != null) {
			balance = user.getDirua();
		}

		String balanceLabel = ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.Balance");
		lblBalance.setText(balanceLabel + ": " + balance + " EUR");
	}
}
