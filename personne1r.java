import java.awt.Image;

import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class personne1r extends Thread {
	private Semaphore waitBus, v3;
	private String direction[] = { "busR", "Non" };
	private String myDirection;
	private String en[] = { "/p droite/1.png", "/p droite/2.png", "/p droite/3.png", "/p droite/4.png", "/p droite/5.png" },
			f[] = { "/p bus/1.png", "/p bus/2.png", "/p bus/3.png", "/p bus/4.png", "/p bus/5.png" },
			b[] = { "/p haut/1.png", "/p haut/2.png", "/p haut/3.png", "/p haut/4.png", "/p haut/5.png" };

	int i;

	public personne1r(Semaphore waitBus, Semaphore v3) {
		this.waitBus = waitBus;
		this.v3 = v3;
		this.myDirection = direction[new Random().nextInt(direction.length)];
		this.i = new Random().nextInt(en.length);
	}

	public void run() {
		try {
			traversee1();
		} catch (InterruptedException e) {
		}
	}

	private void traversee1() throws InterruptedException {
		int x = -30, y = 490, w = 30, h = 30;

		JLabel personne = new JLabel();
		personne.setBounds(x, y, w, h);
		ImageIcon pimg = new ImageIcon(getClass().getResource(en[i]));
		Image pim = pimg.getImage();
		pim = pim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
		personne.setIcon(new ImageIcon(pim));
		Carrfour.secondPage.add(personne);

		v3.acquire(); // pour l'entrée de gauche bas
		sleep(new Random().nextInt(30000));
		while (x < 100) {
			sleep(100);
			personne.setBounds(x++, y, w, h);
		}
		v3.release();
		while (x < 135) {
			sleep(100);
			personne.setBounds(x++, y, w, h);
		}
		switch (myDirection) {
		case "busR":
			pimg = new ImageIcon(getClass().getResource(f[i]));
			pim = pimg.getImage();
			pim = pim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			personne.setIcon(new ImageIcon(pim));
			Carrfour.secondPage.add(personne);

			while (y < 540) {
				sleep(100);
				personne.setBounds(x, y++, w, h);
			}
			pimg = new ImageIcon(getClass().getResource(en[i]));
			pim = pimg.getImage();
			pim = pim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			personne.setIcon(new ImageIcon(pim));
			Carrfour.secondPage.add(personne);
			while (x < 210) {
				sleep(100);
				personne.setBounds(x++, y, w, h);
			}
			waitBus.acquire(); // pour l'attendre de bus
			pimg = new ImageIcon(getClass().getResource(b[i]));
			pim = pimg.getImage();
			pim = pim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			personne.setIcon(new ImageIcon(pim));
			Carrfour.secondPage.add(personne);
			while (y > 500) {
				sleep(30);
				personne.setBounds(x, y--, w, h);
			}
			personne.setBounds(-50, -50, w, h);
			break;
		case "Non":
			pimg = new ImageIcon(getClass().getResource(f[i]));
			pim = pimg.getImage();
			pim = pim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			personne.setIcon(new ImageIcon(pim));
			Carrfour.secondPage.add(personne);
			while (y < 580) {
				sleep(100);
				personne.setBounds(x, y++, w, h);
			}
			pimg = new ImageIcon(getClass().getResource(en[i]));
			pim = pimg.getImage();
			pim = pim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			personne.setIcon(new ImageIcon(pim));
			Carrfour.secondPage.add(personne);
			while (x < 370) {
				sleep(100);
				personne.setBounds(x++, y, w, h);
			}
			pimg = new ImageIcon(getClass().getResource(b[i]));
			pim = pimg.getImage();
			pim = pim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			personne.setIcon(new ImageIcon(pim));
			Carrfour.secondPage.add(personne);
			while (y > 520) {
				sleep(100);
				personne.setBounds(x, y--, w, h);
			}
			pimg = new ImageIcon(getClass().getResource(en[i]));
			pim = pimg.getImage();
			pim = pim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			personne.setIcon(new ImageIcon(pim));
			Carrfour.secondPage.add(personne);
			while (x < 590) {
				sleep(100);
				personne.setBounds(x++, y, w, h);
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
			break;

		}

	}
}
