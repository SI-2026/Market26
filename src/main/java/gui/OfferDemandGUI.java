package gui;

import businessLogic.BLFacade;
import domain.Demand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OfferDemandGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JTextField fieldProduct = new JTextField();
    private final JTextField fieldDescription = new JTextField();
    private final JTextField fieldPrice = new JTextField();
    private final JLabel labelMsg = new JLabel();
    private final Demand demand;
    private final String username;

    public OfferDemandGUI(Demand demand, String username) {
        this.demand = demand;
        this.username = username;
        setTitle("Eskaintza egin eskaera honi");
        setSize(new Dimension(420, 300));
        setLayout(null);

        JLabel labelProduct = new JLabel("Produktua:");
        labelProduct.setBounds(30, 30, 100, 20);
        add(labelProduct);
        fieldProduct.setBounds(140, 30, 220, 24);
        add(fieldProduct);

        JLabel labelDescription = new JLabel("Deskribapena:");
        labelDescription.setBounds(30, 70, 100, 20);
        add(labelDescription);
        fieldDescription.setBounds(140, 70, 220, 24);
        add(fieldDescription);

        JLabel labelPrice = new JLabel("Prezioa (€):");
        labelPrice.setBounds(30, 110, 100, 20);
        add(labelPrice);
        fieldPrice.setBounds(140, 110, 220, 24);
        add(fieldPrice);

        JButton buttonOffer = new JButton("Eskaintza bidali");
        buttonOffer.setBounds(140, 160, 160, 30);
        add(buttonOffer);

        labelMsg.setBounds(30, 210, 350, 20);
        add(labelMsg);

        buttonOffer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                labelMsg.setText("");
                String prod = fieldProduct.getText();
                String desc = fieldDescription.getText();
                String priceText = fieldPrice.getText();
                if (prod == null || prod.isEmpty() || desc == null || desc.isEmpty() || priceText == null || priceText.isEmpty()) {
                    labelMsg.setText("Eremu guztiak bete behar dira");
                    labelMsg.setForeground(Color.RED);
                    return;
                }
                float price = 0;
                try {
                    price = Float.parseFloat(priceText);
                } catch (Exception ex) {
                    labelMsg.setText("Prezioa zenbaki bat izan behar da");
                    labelMsg.setForeground(Color.RED);
                    return;
                }
                BLFacade facade = UserGUI.getBusinessLogic();
                boolean ok = facade.addDemandOffer(demand.getDemandId(), username, prod, desc, price);
                if (ok) {
                    labelMsg.setText("Eskaintza bidalia!");
                    labelMsg.setForeground(new Color(0, 128, 0));
                    fieldProduct.setText("");
                    fieldDescription.setText("");
                    fieldPrice.setText("");
                } else {
                    labelMsg.setText("Errorea eskaintza bidaltzean");
                    labelMsg.setForeground(Color.RED);
                }
            }
        });
    }
}