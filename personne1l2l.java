import java.awt.Image;

import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class personne1l2l extends Thread {
	Semaphore v4;
	int i;
	String en[] = { "/p gauche/1.png", "/p gauche/2.png", "/p gauche/3.png", "/p gauche/4.png", "/p gauche/5.png" },
			f[] = { "/p haut/1.png", "/p haut/2.png", "/p haut/3.png", "/p haut/4.png", "/p haut/5.png" };

	public personne1l2l(Semaphore v4) {
		this.v4 = v4;
		this.i = new Random().nextInt(en.length);
	}

	public void run() {
		try {
			traversee1();
		} catch (InterruptedException e) {
		}
	}

	private void traversee1() throws InterruptedException {
		int x = 1385, y = 185, w = 30, h = 30;
		JLabel personne = new JLabel();
		personne.setBounds(x, y, w, h);
		ImageIcon pimg = new ImageIcon(getClass().getResource(en[i]));
		Image pim = pimg.getImage();
		pim = pim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
		personne.setIcon(new ImageIcon(pim));
		Carrfour.secondPage.add(personne);

		v4.acquire(); // pour l'entrée de droit haut
		sleep(new Random().nextInt(30000));
		while (x > 1285) {
			sleep(100);
			personne.setBounds(x--, y, w, h);
		}
		v4.release();
		while (x > 910) {
			sleep(100);
			personne.setBounds(x--, y, w, h);
		}

		pimg = new ImageIcon(getClass().getResource(f[i]));
		pim = pimg.getImage();
		pim = pim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
		personne.setIcon(new ImageIcon(pim));
		Carrfour.secondPage.add(personne);
		while (y > -30) {
			sleep(100);
			personne.setBounds(x, y--, w, h);
		}

	}
}
