import java.awt.Image;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Voie1R extends Thread {

	private Semaphore sfeu1, waitBusl;
	private ArrayList<ArrayList<Semaphore>> semaphores;
	private int i, coordonnees[][], imagein;
	private String type;
	private String direction[] = { "voie1L", "voie2L", "voie2R" };
	private String myDirection;
	private String V1rCar[] = { "/V1R/V1 R p.png", "/V1R/V1R R.png", "/V1R/V1R Taxi.png", "/V1R/V1R W.png", "/V1R/V1R Y.png" },
			V2lCar[] = { "/V2L/V2 L p.png", "/V2L/V2L R.png", "/V2L/V2L Taxi.png", "/V2L/V2L W.png", "/V2L/V2L Y.png" },
			V2rCar[] = { "/V2R/V2 R p.png", "/V2R/V2R R.png", "/V2R/V2R Taxi.png", "/V2R/V2R W.png", "/V2R/V2R Y.png" };
	private String V1rBus[] = { "/V1R/V1 R bus.png", "/V1R/V1 R rBus.png" };
	private String V2lBus[] = { "/V2L/V2 L Bus.png", "/V2L/V2 L rBus.png" };
	private String V2rBus[] = { "/V2R/V2 R Bus.png", "/V2R/V2 R rBus.png" };

	public Voie1R(Semaphore sfeu1, ArrayList<ArrayList<Semaphore>> semaphores, int[][] coordonnees, String type, Semaphore waitBusl) {
		this.sfeu1 = sfeu1;
		this.waitBusl = waitBusl;
		this.semaphores = semaphores;
		this.coordonnees = coordonnees;
		this.i = coordonnees[0].length - 1;
		this.type = type;
		this.myDirection = direction[new Random().nextInt(direction.length)];
		switch (this.type) {
		case "car":
			this.imagein = new Random().nextInt(V1rCar.length);
			break;
		case "bus":
			this.imagein = new Random().nextInt(V1rBus.length);
			break;
		}
	}

	public void run() {
		try {
			traversee1();
		} catch (InterruptedException e) {
		}
	}

	private void traversee1() throws InterruptedException {
		int x = coordonnees[0][i], y = 270, w = 65, h = 40;
		JLabel voiture = new JLabel();
		ImageIcon voitureimg;
		Image im;
		switch (type) {
		case "bus":
			w = 75;
			voitureimg = new ImageIcon(getClass().getResource(V1rBus[imagein]));
			im = voitureimg.getImage();
			im = im.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			voiture.setIcon(new ImageIcon(im));
			Carrfour.secondPage.add(voiture);
			break;
		case "car":
			voitureimg = new ImageIcon(getClass().getResource(V1rCar[imagein]));
			im = voitureimg.getImage();
			im = im.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			voiture.setIcon(new ImageIcon(im));
			Carrfour.secondPage.add(voiture);
			break;
		}
		sleep((new Random()).nextInt(60000));
		semaphores.get(2).get(i).acquire(); // pour tester si partie i de voie 1 de droit est libre
		while (coordonnees[0][i] != 570) {
			while (x > coordonnees[0][i]) {
				sleep(10);
				voiture.setBounds(x--, y, w, h);
			}
			if (x == 845)
				sfeu1.acquire(); // pour tester le si feu de voie 1 est vert
			else {
				semaphores.get(2).get(i - 1).acquire(); // pour tester si partie i-1 de voie 1 de droit est libre
				semaphores.get(2).get(i).release();
			}
			i--;
		}
		switch (myDirection) {
		case "voie1L":
			semaphores.get(5).get(2).acquire(); // pour assurer que il n'y a pas une véhicule circule dans le carrefour devant le voie 1
			i++;
			semaphores.get(2).get(i).release();
			i--;
			while (x >= coordonnees[0][i]) {
				sleep(10);
				voiture.setBounds(x--, y, w, h);
			}
			semaphores.get(5).get(2).release();
			sfeu1.release();
			i--;
			switch (type) {
			case "bus":
				while (coordonnees[0][i] != 210) {
					while (x > coordonnees[0][i]) {
						sleep(10);
						voiture.setBounds(x--, y, w, h);
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
					voiture.setBounds(x--, y--, w, h);
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
				semaphores.get(2).get(i).acquire();
				for (int j = 0; j < 65; j++) {
					sleep(10);
					voiture.setBounds(x--, y++, w, h);
				}
				semaphores.get(2).get(i + 1).release();
				semaphores.get(2).get(i).release();
				i--;
				semaphores.get(4).get(1).release();
				while (i != -1) {
					while (x > coordonnees[0][i]) {
						sleep(10);
						voiture.setBounds(x--, y, w, h);
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
						voiture.setBounds(x--, y, w, h);
					}
					if ((i - 1) != -1)
						semaphores.get(2).get(i - 1).acquire(); // pour tester si partie i-1 de voie 1 de droit est libre
					semaphores.get(2).get(i).release();
					i--;
				}
				break;
			}
			break;
		case "voie2L":
			semaphores.get(5).get(2).acquire(); // pour assurer que il n'y a pas une véhicule circule dans le carrefour devant le voie 1
			i++;
			semaphores.get(2).get(i).release();
			while (x >= 800) {
				sleep(10);
				voiture.setBounds(x--, y, w, h);
			}
			while (x > 785) {
				sleep(10);
				voiture.setBounds(x--, y--, w, h);
			}
			i = 3;
			switch (type) {
			case "bus":
				voitureimg = new ImageIcon(getClass().getResource(V2rBus[imagein]));
				im = voitureimg.getImage();
				im = im.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
				voiture.setIcon(new ImageIcon(im));
				Carrfour.secondPage.add(voiture);
				break;
			case "car":
				voitureimg = new ImageIcon(getClass().getResource(V2rCar[imagein]));
				im = voitureimg.getImage();
				im = im.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
				voiture.setIcon(new ImageIcon(im));
				Carrfour.secondPage.add(voiture);
				break;
			}
			semaphores.get(5).get(2).release();
			sfeu1.release();
			while (i != -1) {
				while (y > coordonnees[1][i]) {
					sleep(10);
					voiture.setBounds(x, y--, h, w);
				}
				if ((i - 1) != -1)
					semaphores.get(3).get(i - 1).acquire(); // pour tester si partie i-1 de voie 2 de bas est libre
				semaphores.get(3).get(i).release();
				i--;
			}
			break;
		case "voie2R":
			semaphores.get(5).get(0).acquire();// pour assurer que il n'y a pas une véhicule circule dans le carrefour devant le voie 2
			semaphores.get(5).get(2).acquire(); // pour assurer que il n'y a pas une véhicule circule dans le carrefour devant le voie 1
			i++;
			semaphores.get(2).get(i).release();
			while (x >= 685) {
				sleep(10);
				voiture.setBounds(x--, y, w, h);
			}
			while (x > 700) {
				sleep(10);
				voiture.setBounds(x--, y++, w, h);
			}
			i = 4;
			switch (type) {
			case "bus":
				voitureimg = new ImageIcon(getClass().getResource(V2lBus[imagein]));
				im = voitureimg.getImage();
				im = im.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
				voiture.setIcon(new ImageIcon(im));
				Carrfour.secondPage.add(voiture);
				break;
			case "car":
				voitureimg = new ImageIcon(getClass().getResource(V2lCar[imagein]));
				im = voitureimg.getImage();
				im = im.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
				voiture.setIcon(new ImageIcon(im));
				Carrfour.secondPage.add(voiture);
				break;
			}
			while (y < coordonnees[1][i]) {
				sleep(10);
				voiture.setBounds(x, y++, h, w);
			}
			i++;
			semaphores.get(5).get(0).release();
			semaphores.get(5).get(2).release();
			sfeu1.release();
			while (i != coordonnees[1].length) {
				while (y < coordonnees[1][i]) {
					sleep(10);
					voiture.setBounds(x, y++, h, w);
				}
				if ((i + 1) != coordonnees[1].length)
					semaphores.get(1).get(i + 1).acquire(); // pour tester si partie i+1 de voie 2 de haut est libre
				semaphores.get(1).get(i).release();
				i++;
			}
			break;
		}
	}
}
