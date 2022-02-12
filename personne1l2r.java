import java.awt.Image;

import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class personne1l2r extends Thread {
	Semaphore v2;

	private String en[] = { "/p gauche/1.png", "/p gauche/2.png", "/p gauche/3.png", "/p gauche/4.png", "/p gauche/5.png" },
			f[] = { "/p bus/1.png", "/p bus/2.png", "/p bus/3.png", "/p bus/4.png", "/p bus/5.png" };

	int i;

	public personne1l2r(Semaphore v2) {
		this.v2 = v2;
		this.i = new Random().nextInt(en.length);
	}

	public void run() {
		try {
			traversee1();
		} catch (InterruptedException e) {
		}
	}

	private void traversee1() throws InterruptedException {
		int x = 1385, y = 490, w = 30, h = 30;

		JLabel personne = new JLabel();
		personne.setBounds(x, y, w, h);
		ImageIcon pimg = new ImageIcon(getClass().getResource(en[i]));
		Image pim = pimg.getImage();
		pim = pim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
		personne.setIcon(new ImageIcon(pim));
		Carrfour.secondPage.add(personne);

		v2.acquire(); // pour l'entrée de droit bas
		sleep(new Random().nextInt(30000));
		while (x > 1285) {
			sleep(100);
			personne.setBounds(x--, y, w, h);
		}
		v2.release();
		while (x > 910) {
			sleep(100);
			personne.setBounds(x--, y, w, h);
		}

		pimg = new ImageIcon(getClass().getResource(f[i]));
		pim = pimg.getImage();
		pim = pim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
		personne.setIcon(new ImageIcon(pim));
		Carrfour.secondPage.add(personne);
		while (y < 700) {
			sleep(100);
			personne.setBounds(x, y++, w, h);
		}
	}

}
