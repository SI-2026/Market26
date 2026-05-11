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
	private JLabel lblSubscription;
	private JLabel lblInfo;
	private JTextField txtAmount;
	private JButton jButtonAddMoney;
	private JButton jButtonWithdrawMoney;
	private JButton jButtonSubscribe;
	private JButton jButtonCancel;
	private JButton jButtonLogOut;
	private final String username;
	private JButton jButtonMyDemands;

	public ProfileGUI(JFrame registeredRef, String username) {
		this.username = username;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(1000, 300, 460, 490);
		final int frameWidth = 460;
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.MainTitle") + ": " + username);
		setAlwaysOnTop(true);
		
		
		   //Panel hasieraketa
		   contentPane = new JPanel();
		   setContentPane(contentPane);
		   contentPane.setLayout(null);

		   // Botón para ver demandas propias
		jButtonMyDemands = new JButton("Nire Eskaerak");
		jButtonMyDemands.setBounds(120, 360, 220, 28);
		   jButtonMyDemands.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
				   JFrame nireEskaerakGUI = new NireEskaerakGUI(username);
				   nireEskaerakGUI.setVisible(true);
			   }
		   });
		
		
		//Username title
		lblUsername = new JLabel(username);
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds((frameWidth - 220) / 2, 24, 220, 20);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 13));

		
		
		lblBalance = new JLabel();
		lblBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblBalance.setBounds(72, 43, 320, 20);
		lblBalance.setFont(new Font("Tahoma", Font.BOLD, 14));

		lblSubscription = new JLabel();
		lblSubscription.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubscription.setBounds(72, 66, 320, 20);
		lblSubscription.setFont(new Font("Tahoma", Font.PLAIN, 12));

		txtAmount = new JTextField();
		txtAmount.setBounds(120, 94, 220, 28);
		txtAmount.setColumns(10);

		jButtonAddMoney = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.AddMoney"));
		jButtonAddMoney.setBounds(120, 135, 220, 32);
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
				refreshBalance();
				lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.AddedMoney"));
				lblInfo.setForeground(new Color(0, 128, 0));
			}
		});

		jButtonWithdrawMoney = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.WithdrawMoney"));
		jButtonWithdrawMoney.setBounds(120, 180, 220, 32);
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
				refreshBalance();
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
		lblInfo.setBounds(50, 392, 360, 20);
		
		
		jButtonLogOut = new JButton();
		jButtonLogOut.setBounds((frameWidth - 170) / 2, 420, 170, 32);
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
		JButtonMovements.setBounds(120, 225, 220, 33);

		jButtonSubscribe = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.Subscribe"));
		jButtonSubscribe.setBounds(120, 270, 220, 28);
		jButtonSubscribe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = UserGUI.getBusinessLogic();
				boolean ok = facade.buySubscription(username);
				refreshBalance();
				if (ok) {
					lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.SubscriptionBought"));
					lblInfo.setForeground(new Color(0, 128, 0));
				} else {
					lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.SubscriptionError"));
					lblInfo.setForeground(Color.RED);
				}
			}
		});

		jButtonCancel = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.CancelSubscription"));
		jButtonCancel.setBounds(120, 315, 220, 28);
		jButtonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = UserGUI.getBusinessLogic();
				boolean ok = facade.cancelSubscription(username);
				refreshBalance();
				if (ok) {
					lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.SubscriptionCanceled"));
					lblInfo.setForeground(new Color(0, 128, 0));
				} else {
					lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.SubscriptionError"));
					lblInfo.setForeground(Color.RED);
				}
			}
		});
		
		
		//Panelera gehitzeko aginduak
		contentPane.add(lblUsername);
		contentPane.add(lblBalance);
		contentPane.add(lblSubscription);
		contentPane.add(txtAmount);
		contentPane.add(jButtonAddMoney);
		contentPane.add(jButtonWithdrawMoney);
		contentPane.add(lblInfo);
		contentPane.add(jButtonLogOut);
		contentPane.add(JButtonMovements);
		contentPane.add(jButtonSubscribe);
		contentPane.add(jButtonCancel);
		contentPane.add(jButtonMyDemands);
	
		

		refreshBalance();

		
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

	private void refreshBalance() {
		BLFacade facade = UserGUI.getBusinessLogic();
		User user = facade.getUser(username);

		float balance = 0;
		if (user != null) {
			balance = user.getDirua();
		}

		String balanceLabel = ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.Balance");
		lblBalance.setText(balanceLabel + ": " + balance + " EUR");
		if (user != null && user.isSubscribed()) {
			lblSubscription.setText(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.SubscriptionActive"));
			jButtonSubscribe.setEnabled(false);
			jButtonCancel.setEnabled(true);
		} else {
			lblSubscription.setText(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.SubscriptionInactive"));
			jButtonSubscribe.setEnabled(user != null);
			jButtonCancel.setEnabled(false);
		}
	}
}
