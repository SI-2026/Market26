package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import businessLogic.BLFacade;

public class DemandsGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private final String username;
	private final JTextField fieldProduct = new JTextField();
	private final JTextField fieldDescription = new JTextField();
	private final JLabel labelMsg = new JLabel();

	public DemandsGUI(String username) {
		this.username = username;
		setSize(new Dimension(520, 260));
		getContentPane().setLayout(null);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Title"));

		JLabel labelProduct = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Product"));
		labelProduct.setBounds(new Rectangle(20, 24, 100, 20));
		getContentPane().add(labelProduct);

		fieldProduct.setBounds(new Rectangle(130, 22, 340, 24));
		getContentPane().add(fieldProduct);

		JLabel labelDescription = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Description"));
		labelDescription.setBounds(new Rectangle(20, 60, 100, 20));
		getContentPane().add(labelDescription);

		fieldDescription.setBounds(new Rectangle(130, 58, 340, 24));
		getContentPane().add(fieldDescription);

		JButton buttonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Create"));
		buttonCreate.setBounds(new Rectangle(130, 105, 160, 30));
		buttonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelMsg.setText("");
				String product = fieldProduct.getText();
				String description = fieldDescription.getText();
				if (product == null || product.isEmpty() || description == null || description.isEmpty()) {
					labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Invalid"));
					labelMsg.setForeground(Color.RED);
					return;
				}
				BLFacade facade = UserGUI.getBusinessLogic();
				if (facade.createDemand(username, product, description) != null) {
					labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Created"));
					labelMsg.setForeground(new Color(0, 128, 0));
					fieldProduct.setText("");
					fieldDescription.setText("");
				} else {
					labelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("DemandsGUI.Error"));
					labelMsg.setForeground(Color.RED);
				}
			}
		});
		getContentPane().add(buttonCreate);

		JButton buttonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		buttonClose.setBounds(new Rectangle(310, 105, 160, 30));
		buttonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		getContentPane().add(buttonClose);

		labelMsg.setBounds(new Rectangle(20, 155, 450, 20));
		getContentPane().add(labelMsg);
	}
}