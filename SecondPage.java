import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class SecondPage extends JPanel {

	private static final long serialVersionUID = 1L;

	public SecondPage() {
		this.setPreferredSize(new Dimension(1345, 690));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Image ig = new ImageIcon(getClass().getResource("/img/Carrfour.png")).getImage();
		super.paintComponent(g);
		g.drawImage(ig, 0, 0, null);
	}

}
