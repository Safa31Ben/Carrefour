import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;

public class Voie1L extends Thread {
	private Semaphore sfeu1, arret;
	private ArrayList<Semaphore> svoie1;
	private int i, coordonneesVoie1[];
	private String type;

	public Voie1L(Semaphore sfeu1, ArrayList<Semaphore> svoie1, int coordonneesVoie1[], String type, Semaphore arret) {
		this.sfeu1 = sfeu1;
		this.svoie1 = svoie1;
		this.coordonneesVoie1 = coordonneesVoie1;
		this.i = 0;
		this.type = type;
		this.arret = arret;
	}

	public void run() {
		try {
			traversee1();
		} catch (InterruptedException e) {
		}
	}

	private void traversee1() throws InterruptedException {
		int x = coordonneesVoie1[i], y = 380, w = 50, h = 40;
		JButton voiture = new JButton();
		Carrfour.frmCarrfour.getContentPane().add(voiture);
		(svoie1.get(i)).acquire();
		switch (type) {
		case "bus":
			w = 65;
			voiture.setBounds(x, y, w, h);
			while (coordonneesVoie1[i] != 210) {
				while (x < coordonneesVoie1[i]) {
					sleep(10);
					voiture.setBounds(x++, y, w, h);
				}
				if (x == 120)
					arret.acquire();
				svoie1.get(i + 1).acquire();
				svoie1.get(i).release();
				i++;
			}
			for (int j = 0; j < 35; j++) {
				sleep(10);
				voiture.setBounds(x++, y, w, h);
			}
			svoie1.get(i).release();
			for (int j = 0; j < 65; j++) {
				sleep(10);
				voiture.setBounds(x++, y++, w, h);
			}
			for (int j = 0; j < 10; j++) {
				sleep(10);
				voiture.setBounds(x++, y, w, h);
			}
			sleep(2500);
			i++;
			svoie1.get(i).acquire();
			for (int j = 0; j < 65; j++) {
				sleep(10);
				voiture.setBounds(x++, y--, w, h);
			}
			arret.release();
			while (coordonneesVoie1[i] != 845) {
				while (x < coordonneesVoie1[i]) {
					sleep(10);
					voiture.setBounds(x++, y, w, h);
				}
				if (x == 570)
					sfeu1.acquire();
				else
					svoie1.get(i + 1).acquire();
				svoie1.get(i).release();
				i++;
			}
			break;
		case "voiture":
			voiture.setBounds(x, y, w, h);
			while (coordonneesVoie1[i] != 845) {
				while (x < coordonneesVoie1[i]) {
					sleep(10);
					voiture.setBounds(x++, y, w, h);
				}
				if (x == 570)
					sfeu1.acquire();
				else
					svoie1.get(i + 1).acquire();
				svoie1.get(i).release();
				i++;
			}
			break;
		}

		while (x <= coordonneesVoie1[i]) {
			sleep(10);
			voiture.setBounds(x++, y, w, h);
		}
		i++;
		sfeu1.release();
		while (i != coordonneesVoie1.length) {
			while (x < coordonneesVoie1[i]) {
				sleep(10);
				voiture.setBounds(x++, y, w, h);
			}
			if ((i + 1) != coordonneesVoie1.length)
				svoie1.get(i + 1).acquire();
			svoie1.get(i).release();
			i++;
		}
	}
}