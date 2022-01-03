
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;

public class Voie2R extends Thread {
	private Semaphore sfeu2;
	private ArrayList<Semaphore> svoie2;
	int i, coordonneesVoie2[];

	public Voie2R(Semaphore sfeu2, ArrayList<Semaphore> svoie2, int coordonneesVoie2[]) {
		this.sfeu2 = sfeu2;
		this.svoie2 = svoie2;
		this.coordonneesVoie2 = coordonneesVoie2;
		this.i = coordonneesVoie2.length - 1;
	}

	public void run() {
		try {
			traversee2();
		} catch (InterruptedException e) {
		}
	}

	private void traversee2() throws InterruptedException {
		int x = 785, y = coordonneesVoie2[i], w = 40, h = 50;
		JButton voiture = new JButton();
		voiture.setBounds(x, y, w, h);
		Carrfour.frmCarrfour.getContentPane().add(voiture);
		svoie2.get(i).acquire();
		while (coordonneesVoie2[i] != 160) {
			while (y > coordonneesVoie2[i]) {
				sleep(10);
				voiture.setBounds(x, y--, w, h);
			}
			if (y == 440)
				sfeu2.acquire();
			else
				svoie2.get(i - 1).acquire();
			svoie2.get(i).release();
			i--;
		}
		while (y >= coordonneesVoie2[i]) {
			sleep(10);
			voiture.setBounds(x, y--, w, h);
		}
		i--;
		sfeu2.release();
		while (i != -1) {
			while (y > coordonneesVoie2[i]) {
				sleep(10);
				voiture.setBounds(x, y--, w, h);
			}
			if ((i - 1) != -1)
				svoie2.get(i - 1).acquire();
			svoie2.get(i).release();
			i--;
		}
	}
}