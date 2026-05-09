package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.Demand;
import domain.DemandOffer;

public class EskaeraOffersGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JList<DemandOffer> offerList = new JList<>();
    private final DefaultListModel<DemandOffer> listModel = new DefaultListModel<>();
    private final JButton onartuButton = new JButton("Onartu");
    private final JButton ezeztatuButton = new JButton("Ezeztatu");
    private final JLabel labelMsg = new JLabel();
    private final Demand demand;
    private final String username;

    public EskaeraOffersGUI(Demand demand, String username) {
        this.demand = demand;
        this.username = username;
        setTitle("Eskaintzak eskaera honetarako");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        offerList.setModel(listModel);
        offerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        offerList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof DemandOffer) {
                    DemandOffer o = (DemandOffer) value;
                    label.setText(o.getProduct() + " - " + o.getDescription() + " (" + o.getPrice() + "€)");
                }
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(offerList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(onartuButton);
        buttonPanel.add(ezeztatuButton);
        add(buttonPanel, BorderLayout.SOUTH);

        labelMsg.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelMsg, BorderLayout.NORTH);

        onartuButton.setEnabled(false);
        ezeztatuButton.setEnabled(false);

        offerList.addListSelectionListener(e -> {
            boolean selected = offerList.getSelectedIndex() != -1;
            onartuButton.setEnabled(selected);
            ezeztatuButton.setEnabled(selected);
        });

        onartuButton.addActionListener(e -> {
            DemandOffer selected = offerList.getSelectedValue();
            if (selected != null) {
                BLFacade facade = UserGUI.getBusinessLogic();
                boolean ok = facade.acceptDemandOffer(demand.getDemandId(), selected.getOfferId(), username);
                if (ok) {
                    labelMsg.setText("Onartua! Dirua mugitu da.");
                    listModel.clear();
                    listModel.addElement(selected);
                } else {
                    labelMsg.setText("Errorea eskaintza onartzean");
                }
            }
        });

        ezeztatuButton.addActionListener(e -> {
            DemandOffer selected = offerList.getSelectedValue();
            if (selected != null) {
                BLFacade facade = UserGUI.getBusinessLogic();
                boolean ok = facade.declineDemandOffer(demand.getDemandId(), selected.getOfferId(), username);
                if (ok) {
                    listModel.removeElement(selected);
                    labelMsg.setText("Ezeztatu da");
                } else {
                    labelMsg.setText("Errorea eskaintza ezeztatzean");
                }
            }
        });

        loadOffers();
    }

    private void loadOffers() {
        listModel.clear();
        List<DemandOffer> offers = demand.getOffers();
        for (DemandOffer o : offers) {
            listModel.addElement(o);
        }
    }
}