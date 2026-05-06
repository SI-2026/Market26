package gui;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ReviewGUI extends JFrame{
	private JTextField textField;
	private JButton[] heartButtons;
	private ImageIcon emptyHeart;
	private ImageIcon fullHeart;
	private int rating;
	public ReviewGUI() {
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Review");
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 16));
		lblNewLabel.setBounds(324, 44, 75, 24);
		getContentPane().add(lblNewLabel);
		
		// 1) Irudiak kargatu (bihotz hutsa eta betea) resources-etik.
		int heartSize = 100;
		emptyHeart = loadIcon("/images/corazon.png", heartSize, heartSize);
		fullHeart = loadIcon("/images/corazon_lleno.png", heartSize, heartSize);
		// 2) Bihotzak lerroan kokatzeko posizioak definitu.
		int y = 120;
		int gap = 10;
		int totalWidth = 5 * heartSize + 4 * gap;
		int startX = (720 - totalWidth) / 2;
		// 3) 5 botoiak gordetzeko arraya sortu.
		heartButtons = new JButton[5];
		for (int i = 0; i < 5; i++) {
			int index = i;
			JButton heartButton = new JButton(emptyHeart);
			heartButton.setBounds(startX + i * (heartSize + gap), y, heartSize, heartSize);
			heartButton.setBorderPainted(false);
			heartButton.setContentAreaFilled(false);
			heartButton.setFocusPainted(false);
			heartButton.setOpaque(false);
			// 4) Klik egitean puntuazioa ezarri eta bihotz guztiak eguneratu.
			heartButton.addActionListener(e -> setRating(index + 1));
			heartButtons[i] = heartButton;
			getContentPane().add(heartButton);
		}
		// 5) Hasierako egoera: dena hutsik.
		setRating(0);
		
		textField = new JTextField();
		textField.setBounds(170, 274, 392, 90);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Iritzia");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(342, 248, 46, 14);
		getContentPane().add(lblNewLabel_1);
	}
	
	private ImageIcon loadIcon(String path, int width, int height) {
		java.net.URL url = getClass().getResource(path);
		if (url == null) {
			return new ImageIcon();
		}
		Image image = new ImageIcon(url).getImage();
		Image scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(scaled);
	}
	
	private void setRating(int newRating) {
		// 6) Puntuazioa gorde eta bihotzak bete/hustu posizioaren arabera.
		rating = newRating;
		for (int i = 0; i < heartButtons.length; i++) {
			ImageIcon icon = i < rating ? fullHeart : emptyHeart;
			heartButtons[i].setIcon(icon);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ReviewGUI frame = new ReviewGUI();
			frame.setTitle("ReviewGUI");
			frame.setSize(720, 480);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
}
