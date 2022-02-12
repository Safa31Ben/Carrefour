import java.awt.Image;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Voie2R extends Thread {
	private Semaphore sfeu2, waitBusl;
	private ArrayList<ArrayList<Semaphore>> semaphores;
	private int i, coordonnees[][], imagein;
	private String type;
	private String direction[] = { "voie2L", "voie1L", "voie1R" };
	private String myDirection;
	private String V1lCar[] = { "/V1L/V1 L p.png", "/V1L/V1L R.png", "/V1L/V1L Taxi.png", "/V1L/V1L W.png", "/V1L/V1L Y.png" },
			V1rCar[] = { "/V1R/V1 R p.png", "/V1R/V1R R.png", "/V1R/V1R Taxi.png", "/V1R/V1R W.png", "/V1R/V1R Y.png" },
			V2rCar[] = { "/V2R/V2 R p.png", "/V2R/V2R R.png", "/V2R/V2R Taxi.png", "/V2R/V2R W.png", "/V2R/V2R Y.png" };
	private String V1lBus[] = { "/V1L/V1 L bus .png", "/V1L/V1 L rBus.png" },
			V1rBus[] = { "/V1R/V1 R bus.png", "/V1R/V1 R rBus.png" },
			V2rBus[] = { "/V2R/V2 R Bus.png", "/V2R/V2 R rBus.png" };

	public Voie2R(Semaphore sfeu2, ArrayList<ArrayList<Semaphore>> semaphores, int[][] coordonnees, String type, Semaphore waitBusl) {
		this.sfeu2 = sfeu2;
		this.waitBusl = waitBusl;
		this.semaphores = semaphores;
		this.coordonnees = coordonnees;
		this.i = coordonnees[1].length - 1;
		this.type = type;
		this.myDirection = direction[new Random().nextInt(direction.length)];
		switch (this.type) {
		case "car":
			this.imagein = new Random().nextInt(V2rCar.length);
			break;
		case "bus":
			this.imagein = new Random().nextInt(V2rBus.length);
			break;
		}
	}

	public void run() {
		try {
			traversee2();
		} catch (InterruptedException e) {
		}
	}

	private void traversee2() throws InterruptedException {
		int x = 785, y = coordonnees[1][i], w = 40, h = 65;
		JLabel voiture = new JLabel();
		ImageIcon voitureimg;
		Image im;
		switch (type) {
		case "car":
			h = 65;
			voitureimg = new ImageIcon(getClass().getResource(V2rCar[imagein]));
			im = voitureimg.getImage();
			im = im.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			voiture.setIcon(new ImageIcon(im));
			Carrfour.secondPage.add(voiture);
			break;
		case "bus":
			h = 75;
			voitureimg = new ImageIcon(getClass().getResource(V2rBus[imagein]));
			im = voitureimg.getImage();
			im = im.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			voiture.setIcon(new ImageIcon(im));
			Carrfour.secondPage.add(voiture);
			break;
		}
		sleep((new Random()).nextInt(60000));
		semaphores.get(3).get(i).acquire(); // pour tester si partie i de voie 2 de bas est libre
		while (coordonnees[1][i] != 160) {
			while (y > coordonnees[1][i]) {
				sleep(10);
				voiture.setBounds(x, y--, w, h);
			}
			if (y == 440)
				sfeu2.acquire(); // pour tester le si feu de voie 2 est vert
			else {
				semaphores.get(3).get(i - 1).acquire(); // pour tester si partie i-1 de voie 2 de bas est libre
				semaphores.get(3).get(i).release();
			}
			i--;
		}
		switch (myDirection) {
		case "voie2L":
			semaphores.get(5).get(3).acquire(); // pour assurer que il n'y a pas une véhicule circule dans le carrefour devant le voie 2
			i++;
			semaphores.get(3).get(i).release();
			i--;
			while (y >= coordonnees[1][i]) {
				sleep(10);
				voiture.setBounds(x, y--, w, h);
			}
			i--;
			semaphores.get(5).get(3).release();
			sfeu2.release();
			while (i != -1) {
				while (y > coordonnees[1][i]) {
					sleep(10);
					voiture.setBounds(x, y--, w, h);
				}
				if ((i - 1) != -1)
					semaphores.get(3).get(i - 1).acquire(); // pour tester si partie i-1 de voie 2 de bas est libre
				semaphores.get(3).get(i).release();
				i--;
			}
			break;
		case "voie1L":
			semaphores.get(5).get(1).acquire(); // pour assurer que il n'y a pas une véhicule circule dans le carrefour devant le voie 2
			semaphores.get(5).get(3).acquire(); // pour assurer que il n'y a pas une véhicule circule dans le carrefour devant le voie 1
			i++;
			semaphores.get(3).get(i).release();
			while (y >= 290) {
				sleep(10);
				voiture.setBounds(x, y--, w, h);
			}
			while (y > 270) {
				sleep(10);
				voiture.setBounds(x--, y--, w, h);
			}
			i = 8;
			switch (type) {
			case "bus":
				voitureimg = new ImageIcon(getClass().getResource(V1rBus[imagein]));
				im = voitureimg.getImage();
				im = im.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
				voiture.setIcon(new ImageIcon(im));
				Carrfour.secondPage.add(voiture);
				break;
			case "car":
				voitureimg = new ImageIcon(getClass().getResource(V1rCar[imagein]));
				im = voitureimg.getImage();
				im = im.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
				voiture.setIcon(new ImageIcon(im));
				Carrfour.secondPage.add(voiture);
				break;
			}
			while (x > coordonnees[0][i]) {
				sleep(10);
				voiture.setBounds(x--, y, h, w);
			}
			i--;
			semaphores.get(5).get(1).release();
			semaphores.get(5).get(3).release();
			sfeu2.release();
			switch (type) {
			case "bus":
				while (coordonnees[0][i] != 210) {
					while (x > coordonnees[0][i]) {
						sleep(10);
						voiture.setBounds(x--, y, h, w);
					}
					if (x == 300) {
						semaphores.get(4).get(1).acquire(); // pour tester si l'arrêt de bus est libre
						break;
					}
					semaphores.get(2).get(i - 1).acquire(); // pour tester si partie i-1 de voie 1 de droit est libre
					semaphores.get(2).get(i).release();
					i--;
				}
				for (int j = 0; j < 65; j++) {
					sleep(10);
					voiture.setBounds(x--, y--, h, w);
				}
				semaphores.get(2).get(i).release();
				if (waitBusl.getQueueLength() < 2) {
					waitBusl.release(waitBusl.getQueueLength());
				} else if (waitBusl.getQueueLength() == 0) {
				} else {
					waitBusl.release(2);
				}
				i--;
				sleep(3000);
				i--;
				semaphores.get(2).get(i + 1).acquire(); // pour tester si partie i+1 de voie 1 de droit est libre
				semaphores.get(2).get(i).acquire(); // pour tester si partie i de voie 1 de haut droit libre
				for (int j = 0; j < 65; j++) {
					sleep(10);
					voiture.setBounds(x--, y++, h, w);
				}
				semaphores.get(2).get(i + 1).release();
				semaphores.get(2).get(i).release();
				i--;
				semaphores.get(4).get(1).release();
				while (i != -1) {
					while (x > coordonnees[0][i]) {
						sleep(10);
						voiture.setBounds(x--, y, h, w);
					}
					if ((i - 1) != -1)
						semaphores.get(2).get(i - 1).acquire(); // pour tester si partie i-1 de voie 1 de droit est libre
					semaphores.get(2).get(i).release();
					i--;
				}
				break;
			case "car":
				while (i != -1) {
					while (x > coordonnees[0][i]) {
						sleep(10);
						voiture.setBounds(x--, y, h, w);
					}
					if ((i - 1) != -1)
						semaphores.get(2).get(i - 1).acquire(); // pour tester si partie i-1 de voie 1 de droit est libre
					semaphores.get(2).get(i).release();
					i--;
				}
				break;
			}
			break;
		case "voie1R":
			semaphores.get(5).get(3).acquire(); // pour assurer que il n'y a pas une véhicule circule dans le carrefour devant le voie 2
			i++;
			semaphores.get(3).get(i).release();
			while (y >= 380) {
				sleep(10);
				voiture.setBounds(x, y--, w, h);
			}
			while (y > 400) {
				sleep(10);
				voiture.setBounds(x++, y--, w, h);
			}
			i = 9;
			switch (type) {
			case "bus":
				voitureimg = new ImageIcon(getClass().getResource(V1lBus[imagein]));
				im = voitureimg.getImage();
				im = im.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
				voiture.setIcon(new ImageIcon(im));
				Carrfour.secondPage.add(voiture);
				break;
			case "car":
				voitureimg = new ImageIcon(getClass().getResource(V1lCar[imagein]));
				im = voitureimg.getImage();
				im = im.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
				voiture.setIcon(new ImageIcon(im));
				Carrfour.secondPage.add(voiture);
				break;
			}
			semaphores.get(5).get(3).release();
			sfeu2.release();
			while (i != coordonnees[0].length) {
				while (x < coordonnees[0][i]) {
					sleep(10);
					voiture.setBounds(x++, y, h, w);
				}
				if ((i + 1) != coordonnees[0].length)
					semaphores.get(0).get(i + 1).acquire(); // pour tester si partie i+1 de voie 1 de gauche est libre
				semaphores.get(0).get(i).release();
				i++;
			}
			break;
		}
	}
}
