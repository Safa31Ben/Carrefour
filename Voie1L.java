import java.awt.Image;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Voie1L extends Thread {
	
	private Semaphore sfeu1, waitBusr;
	private ArrayList<ArrayList<Semaphore>> semaphores;
	private int i, coordonnees[][], imagein;
	private String type;
	private String direction[] = { "voie1R", "voie2L", "voie2R" };
	private String myDirection;
	private String V1lCar[] = { "/V1L/V1 L p.png", "/V1L/V1L R.png", "/V1L/V1L Taxi.png", "/V1L/V1L W.png", "/V1L/V1L Y.png" },
			V2lCar[] = { "/V2L/V2 L p.png", "/V2L/V2L R.png", "/V2L/V2L Taxi.png", "/V2L/V2L W.png", "/V2L/V2L Y.png" },
			V2rCar[] = { "/V2R/V2 R p.png", "/V2R/V2R R.png", "/V2R/V2R Taxi.png", "/V2R/V2R W.png", "/V2R/V2R Y.png" };
	private String V1lBus[] = { "/V1L/V1 L bus .png", "/V1L/V1 L rBus.png" },
			V2lBus[] = { "/V2L/V2 L Bus.png", "/V2L/V2 L rBus.png" },
			V2rBus[] = { "/V2R/V2 R Bus.png", "/V2R/V2 R rBus.png" };

	public Voie1L(Semaphore sfeu1, ArrayList<ArrayList<Semaphore>> semaphores, int[][] coordonnees, String type, Semaphore waitBusr) {
		this.sfeu1 = sfeu1;
		this.waitBusr = waitBusr;
		this.semaphores = semaphores;
		this.coordonnees = coordonnees;
		this.type = type;
		this.i = 0;
		this.myDirection = direction[new Random().nextInt(direction.length)];
		switch (this.type) {
		case "car":
			this.imagein = new Random().nextInt(V1lCar.length);
			break;
		case "bus":
			this.imagein = new Random().nextInt(V1lBus.length);
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
		int x = coordonnees[0][i], y = 380, w = 65, h = 40;
		JLabel voiture = new JLabel();
		ImageIcon voitureimg;
		Image im;
		sleep((new Random()).nextInt(60000));
		semaphores.get(0).get(i).acquire(); // pour tester si partie i de voie 1 de gauche est libre
		switch (type) {
		case "bus":
			w = 75;
			voitureimg = new ImageIcon(getClass().getResource(V1lBus[imagein]));
			im = voitureimg.getImage();
			im = im.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			voiture.setIcon(new ImageIcon(im));
			Carrfour.secondPage.add(voiture);
			while (coordonnees[0][i] != 210) {
				while (x < coordonnees[0][i]) {
					sleep(10);
					voiture.setBounds(x++, y, w, h);
				}
				if (x == 120)
					semaphores.get(4).get(0).acquire(); // pour tester si l'arret est libre
				semaphores.get(0).get(i + 1).acquire(); // pour tester si partie i+1 de voie 1 de gauche s'il est libre
				semaphores.get(0).get(i).release();
				i++;
			}
			for (int j = 0; j < 35; j++) {
				sleep(10);
				voiture.setBounds(x++, y, w, h);
			}
			semaphores.get(0).get(i).release();
			for (int j = 0; j < 60; j++) {
				sleep(10);
				voiture.setBounds(x++, y++, w, h);
			}
			for (int j = 0; j < 10; j++) {
				sleep(10);
				voiture.setBounds(x++, y, w, h);
			}
			if (waitBusr.getQueueLength() < 2) {
				waitBusr.release(waitBusr.getQueueLength());
			} else if (waitBusr.getQueueLength() == 0) {
			} else {
				waitBusr.release(2);
			}
			sleep(3000);
			i++;
			semaphores.get(0).get(i).acquire(); // pour tester si partie i de voie 1 de gauche est libre
			for (int j = 0; j < 65; j++) {
				sleep(10);
				voiture.setBounds(x++, y--, w, h);
			}
			semaphores.get(4).get(0).release();
			while (coordonnees[0][i] != 845) {
				while (x < coordonnees[0][i]) {
					sleep(10);
					voiture.setBounds(x++, y, w, h);
				}
				if (x == 570)
					sfeu1.acquire(); // pour tester le si feu de voie 1 est vert
				else {
					semaphores.get(0).get(i + 1).acquire(); // pour tester si partie i+1 de voie 1 de gauche est libre
					semaphores.get(0).get(i).release();
				}
				i++;
			}
			break;
		case "car":
			voitureimg = new ImageIcon(getClass().getResource(V1lCar[imagein]));
			im = voitureimg.getImage();
			im = im.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			voiture.setIcon(new ImageIcon(im));
			Carrfour.secondPage.add(voiture);
			while (coordonnees[0][i] != 845) {
				while (x < coordonnees[0][i]) {
					sleep(10);
					voiture.setBounds(x++, y, w, h);
				}
				if (x == 570)
					sfeu1.acquire(); // pour tester le si feu de voie 1 est vert
				else {
					semaphores.get(0).get(i + 1).acquire(); // pour tester si partie i+1 de voie 1 de gauche est libre
					semaphores.get(0).get(i).release();
				}
				i++;
			}
			break;
		}
		switch (myDirection) {
		case "voie1R":
			semaphores.get(5).get(0).acquire(); // pour assurer que il n'y a pas une véhicule circule dans le carrefour devant le voie 1
			i--;
			semaphores.get(0).get(i).release();
			i++;
			while (x <= coordonnees[0][i]) {
				sleep(10);
				voiture.setBounds(x++, y, w, h);
			}
			i++;
			semaphores.get(5).get(0).release();
			sfeu1.release();
			while (i != coordonnees[0].length) {
				while (x < coordonnees[0][i]) {
					sleep(10);
					voiture.setBounds(x++, y, w, h);
				}
				if ((i + 1) != coordonnees[0].length)
					semaphores.get(0).get(i + 1).acquire(); // pour tester si partie i+1 de voie 1 de gauche est libre
				semaphores.get(0).get(i).release();
				i++;
			}
			break;
		case "voie2L":
			semaphores.get(5).get(0).acquire(); // pour assurer que il n'y a pas une véhicule circule dans le carrefour devant le voie 1
			semaphores.get(5).get(2).acquire(); // pour assurer que il n'y a pas une véhicule circule dans le carrefour devant le voie 2
			i--;
			semaphores.get(0).get(i).release();
			while (x <= 770) {
				sleep(10);
				voiture.setBounds(x++, y, w, h);
			}
			while (x <= 785) {
				sleep(10);
				voiture.setBounds(x++, y--, w, h);
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
			while (y > coordonnees[1][i]) {
				sleep(10);
				voiture.setBounds(x, y--, h, w);
			}
			i--;
			semaphores.get(5).get(0).release();
			semaphores.get(5).get(2).release();
			sfeu1.release();
			while (i != -1) {
				while (y > coordonnees[1][i]) {
					sleep(10);
					voiture.setBounds(x, y--, h, w);
				}
				if ((i - 1) != -1)
					semaphores.get(3).get(i - 1).acquire(); // pour tester si partie i-1 de voie 2 de haut est libre
				semaphores.get(3).get(i).release();
				i--;
			}
			break;
		case "voie2R":
			semaphores.get(5).get(0).acquire(); // pour assurer que il n'y a pas une véhiculé circule dans le carrefour devant le voie 1
			i--;
			semaphores.get(0).get(i).release();
			while (x <= 665) {
				sleep(10);
				voiture.setBounds(x++, y, w, h);
			}
			while (x <= 680) {
				sleep(10);
				voiture.setBounds(x++, y++, w, h);
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
			semaphores.get(5).get(0).release();
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
