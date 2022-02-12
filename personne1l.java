import java.awt.Image;

import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class personne1l extends Thread {
	private Semaphore waitBus1, v1;
	private String direction[] = { "busL", "Non" };
	private String myDirection;
	int i;
	private String en[] = { "/p droite/1.png", "/p droite/2.png", "/p droite/3.png", "/p droite/4.png", "/p droite/5.png" },
			b[] = { "/p bus/1.png", "/p bus/2.png", "/p bus/3.png", "/p bus/4.png", "/p bus/5.png" },
			f[] = { "/p haut/1.png", "/p haut/2.png", "/p haut/3.png", "/p haut/4.png", "/p haut/5.png" };

	public personne1l(Semaphore waitBus1, Semaphore v1) {
		this.waitBus1 = waitBus1;
		this.v1 = v1;
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
		int x = -30, y = 180, w = 30, h = 30;

		JLabel personne = new JLabel();
		personne.setBounds(x, y, w, h);
		ImageIcon pimg = new ImageIcon(getClass().getResource(en[i]));
		Image pim = pimg.getImage();
		pim = pim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
		personne.setIcon(new ImageIcon(pim));
		Carrfour.secondPage.add(personne);

		v1.acquire(); // pour l'entrée de gauche haut
		sleep(new Random().nextInt(30000));
		Carrfour.secondPage.add(personne);
		while (x < 100) {
			sleep(100);
			personne.setBounds(x++, y, w, h);
		}
		v1.release();
		while (x < 135) {
			sleep(100);
			personne.setBounds(x++, y, w, h);
		}
		switch (myDirection) {
		case "busL":
			ImageIcon pimg1 = new ImageIcon(getClass().getResource(f[i]));
			Image pim1 = pimg1.getImage();
			pim1 = pim1.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			personne.setIcon(new ImageIcon(pim1));
			Carrfour.secondPage.add(personne);

			while (y > 130) {
				sleep(100);
				personne.setBounds(x, y--, w, h);
			}

			ImageIcon pimg2 = new ImageIcon(getClass().getResource(en[i]));
			Image pim2 = pimg2.getImage();
			pim2 = pim2.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			personne.setIcon(new ImageIcon(pim2));
			Carrfour.secondPage.add(personne);

			while (x < 210) {
				sleep(100);
				personne.setBounds(x++, y, w, h);
			}
			waitBus1.acquire(); // pour attendre de bus
			ImageIcon pimg3 = new ImageIcon(getClass().getResource(b[i]));
			Image pim3 = pimg3.getImage();
			pim3 = pim3.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			personne.setIcon(new ImageIcon(pim3));
			Carrfour.secondPage.add(personne);

			while (y < 190) {
				sleep(15);
				personne.setBounds(x, y++, w, h);
			}
			personne.setBounds(-50, -50, w, h);
			break;
		case "Non":
			ImageIcon pimg4 = new ImageIcon(getClass().getResource(en[i]));
			Image pim4 = pimg4.getImage();
			pim4 = pim4.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			personne.setIcon(new ImageIcon(pim4));
			Carrfour.secondPage.add(personne);

			while (x < 135) {
				sleep(100);
				personne.setBounds(x++, y, w, h);
			}
			ImageIcon pimg5 = new ImageIcon(getClass().getResource(f[i]));
			Image pim5 = pimg5.getImage();
			pim5 = pim5.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			personne.setIcon(new ImageIcon(pim5));
			Carrfour.secondPage.add(personne);

			while (y > 105) {
				sleep(100);
				personne.setBounds(x, y--, w, h);
			}
			ImageIcon pimg6 = new ImageIcon(getClass().getResource(en[i]));
			Image pim6 = pimg6.getImage();
			pim6 = pim6.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			personne.setIcon(new ImageIcon(pim6));
			Carrfour.secondPage.add(personne);
			while (x < 390) {
				sleep(100);
				personne.setBounds(x++, y, w, h);
			}
			ImageIcon pimg7 = new ImageIcon(getClass().getResource(b[i]));
			Image pim7 = pimg7.getImage();
			pim7 = pim7.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			personne.setIcon(new ImageIcon(pim7));
			Carrfour.secondPage.add(personne);
			while (y < 170) {
				sleep(100);
				personne.setBounds(x, y++, w, h);
			}
			pimg5 = new ImageIcon(getClass().getResource(en[i]));
			pim5 = pimg5.getImage();
			pim5 = pim5.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			personne.setIcon(new ImageIcon(pim5));
			Carrfour.secondPage.add(personne);
			while (x < 600) {
				sleep(100);
				personne.setBounds(x++, y, w, h);
			}
			pimg5 = new ImageIcon(getClass().getResource(f[i]));
			pim5 = pimg5.getImage();
			pim5 = pim5.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			personne.setIcon(new ImageIcon(pim5));
			Carrfour.secondPage.add(personne);
			while (y > -50) {
				sleep(100);
				personne.setBounds(x, y--, w, h);
			}
			break;
		}

	}

}
