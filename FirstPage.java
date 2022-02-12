import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FirstPage extends JPanel {

	private static final long serialVersionUID = 1L;

	public FirstPage() {
		this.setPreferredSize(new Dimension(1345, 690));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Image ig = new ImageIcon(getClass().getResource("/img/Carrfour.png")).getImage();
		super.paintComponent(g);
		g.drawImage(ig, 0, 0, null);

		Graphics2D p = (Graphics2D) g;
		p.setPaint(Color.black);
		p.fillRect(415, 215, 510, 260);
		p.setPaint(Color.white);
		p.fillRect(420, 220, 500, 250);

		p.setPaint(Color.black);

		p.setFont(new Font("Inj Free", Font.BOLD, 24));
		p.drawString("Mini Projet", 620, 245);

		p.setFont(new Font("Inj Free", Font.BOLD, 24));
		p.drawString("Gestion d'un Carrefour", 545, 295);

		p.setFont(new Font("Inj Free", Font.BOLD, 24));
		p.drawString("BENABDESSADOK Safa Groupe 4", 490, 345);

		p.setFont(new Font("Inj Free", Font.BOLD, 24));
		p.drawString("AD Badr Eddine Groupe 2", 520, 395);

		p.setFont(new Font("Inj Free", Font.BOLD, 24));
		p.drawString("BENDJAMAA Heitham Groupe 2", 495, 445);

	}

}