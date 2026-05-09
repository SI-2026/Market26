package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import businessLogic.BLFacade;
import domain.Demand;
import domain.User;

public class NireEskaerakGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JList<Demand> eskaeraList = new JList<>();
    private final DefaultListModel<Demand> listModel = new DefaultListModel<>();
    private final String username;

    public NireEskaerakGUI(String username) {
        this.username = username;
        setTitle("Nire Eskaerak");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        eskaeraList.setModel(listModel);
        eskaeraList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eskaeraList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Demand) {
                    Demand d = (Demand) value;
                    label.setText(d.getProd() + " - " + d.getDescription());
                }
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(eskaeraList);
        add(scrollPane, BorderLayout.CENTER);

        eskaeraList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Demand selected = eskaeraList.getSelectedValue();
                    if (selected != null) {
                        JFrame eskaeraOffers = new EskaeraOffersGUI(selected, username);
                        eskaeraOffers.setVisible(true);
                    }
                }
            }
        });

        loadDemands();
    }

    private void loadDemands() {
        BLFacade facade = UserGUI.getBusinessLogic();
        User user = facade.getUser(username);
        listModel.clear();
        if (user != null) {
            List<Demand> demands = user.getDemands();
            for (Demand d : demands) {
                listModel.addElement(d);
            }
        }
    }
}