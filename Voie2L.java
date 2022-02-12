import java.awt.Image;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Voie2L extends Thread {
	private Semaphore sfeu2, waitBusl;
	private ArrayList<ArrayList<Semaphore>> semaphores;
	private String type;
	private int i, coordonnees[][], imagein;
	private String direction[] = { "voie2R", "voie1L", "voie1R" };
	private String myDirection;
	private String V1lCar[] = { "/V1L/V1 L p.png", "/V1L/V1L R.png", "/V1L/V1L Taxi.png", "/V1L/V1L W.png", "/V1L/V1L Y.png" },
			V1rCar[] = { "/V1R/V1 R p.png", "/V1R/V1R R.png", "/V1R/V1R Taxi.png", "/V1R/V1R W.png", "/V1R/V1R Y.png" },
			V2lCar[] = { "/V2L/V2 L p.png", "/V2L/V2L R.png", "/V2L/V2L Taxi.png", "/V2L/V2L W.png", "/V2L/V2L Y.png" };
	private String V1lBus[] = { "/V1L/V1 L bus .png", "/V1L/V1 L rBus.png" };
	private String V1rBus[] = { "/V1R/V1 R bus.png", "/V1R/V1 R rBus.png" };
	private String V2lBus[] = { "/V2L/V2 L Bus.png", "/V2L/V2 L rBus.png" };

	public Voie2L(Semaphore sfeu2, ArrayList<ArrayList<Semaphore>> semaphores, int[][] coordonnees, String type, Semaphore waitBusl) {
		this.sfeu2 = sfeu2;
		this.waitBusl = waitBusl;
		this.semaphores = semaphores;
		this.coordonnees = coordonnees;
		this.i = 0;
		this.type = type;
		this.myDirection = direction[new Random().nextInt(direction.length)];
		this.myDirection = direction[new Random().nextInt(direction.length)];
		switch (this.type) {
		case "car":
			this.imagein = new Random().nextInt(V2lCar.length);
			break;
		case "bus":
			this.imagein = new Random().nextInt(V2lBus.length);
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
		int x = 680, y = coordonnees[1][i], w = 40, h = 65;
		JLabel voiture = new JLabel();
		ImageIcon voitureimg;
		Image im;
		switch (type) {
		case "car":
			h = 65;
			voitureimg = new ImageIcon(getClass().getResource(V2lCar[imagein]));
			im = voitureimg.getImage();
			im = im.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			voiture.setIcon(new ImageIcon(im));
			Carrfour.secondPage.add(voiture);
			break;
		case "bus":
			h = 75;
			voitureimg = new ImageIcon(getClass().getResource(V2lBus[imagein]));
			im = voitureimg.getImage();
			im = im.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			voiture.setIcon(new ImageIcon(im));
			Carrfour.secondPage.add(voiture);
			break;
		}
		sleep((new Random()).nextInt(60000));
		semaphores.get(1).get(i).acquire(); // pour tester si partie i de voie 2 de haut est libre
		while (coordonnees[1][i] != 440) {
			while (y < coordonnees[1][i]) {
				sleep(10);
				voiture.setBounds(x, y++, w, h);
			}
			if (y == 160)
				sfeu2.acquire(); // pour tester le si feu de voie 2 est vert
			else {
				semaphores.get(1).get(i + 1).acquire(); // pour tester si partie i+1 de voie 2 de haut est libre
				semaphores.get(1).get(i).release();
			}
			i++;
		}
		switch (myDirection) {
		case "voie2R":
			semaphores.get(5).get(1).acquire(); // pour assurer que il n'y a pas une véhicule circule dans le carrefour devant le voie 2
			i--;
			semaphores.get(1).get(i).release();
			i++;
			while (y <= coordonnees[1][i]) {
				sleep(10);
				voiture.setBounds(x, y++, w, h);
			}
			i++;
			semaphores.get(5).get(1).release();
			sfeu2.release();
			while (i != coordonnees[1].length) {
				while (y < coordonnees[1][i]) {
					sleep(10);
					voiture.setBounds(x, y++, w, h);
				}
				if ((i + 1) != coordonnees[1].length)
					semaphores.get(1).get(i + 1).acquire(); // pour tester si partie i+1 de voie 2 de haut est libre
				semaphores.get(1).get(i).release();
				i++;
			}
			break;
		case "voie1L":
			semaphores.get(5).get(1).acquire(); // pour assurer que il n'y a pas une véhicule circule dans le carrefour devant le voie 2
			i--;
			semaphores.get(1).get(i).release();
			while (y <= 250) {
				sleep(10);
				voiture.setBounds(x, y++, w, h);
			}
			while (y <= 270) {
				sleep(10);
				voiture.setBounds(x--, y++, w, h);
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
			sfeu2.release();
			switch (type) {
			case "bus":
				while (coordonnees[0][i] != 210) {
					while (x > coordonnees[0][i]) {
						sleep(10);
						voiture.setBounds(x--, y, h, w);
					}
					if (x == 300) {
						semaphores.get(4).get(1).acquire(); // pour tester si l'arret de bus est libre
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
				semaphores.get(2).get(i).acquire(); // pour tester si partie i de voie 1 de droit est libre
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
			semaphores.get(5).get(1).acquire(); // pour assurer que il n'y a pas une véhicule circule dans le carrefour devant le voie 1
			semaphores.get(5).get(3).acquire(); // pour assurer que il n'y a pas une véhicule circule dans le carrefour devant le voie 2
			i--;
			semaphores.get(1).get(i).release();
			while (y <= 365) {
				sleep(10);
				voiture.setBounds(x, y++, w, h);
			}
			while (y < 380) {
				sleep(10);
				voiture.setBounds(x++, y++, w, h);
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
			while (x < coordonnees[0][i]) {
				sleep(10);
				voiture.setBounds(x++, y, h, w);
			}
			i++;
			semaphores.get(5).get(1).release();
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
