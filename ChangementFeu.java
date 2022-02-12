import java.awt.Image;

import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ChangementFeu extends Thread {
	private Semaphore sfeu1r, sfeu2r, sfeu1l, sfeu2l;
	private final int Duree_du_feu = 30000;

	private String V1LF[] = { "/V1LF/VV1L.png", "/V1LF/OV1L.png", "/V1LF/RV1L.png" },
			V1RF[] = { "/V1RF/VV1R.png", "/V1RF/OV1R.png", "/V1RF/RV1R.png" },
			V2LF[] = { "/V2LF/VV2L.png", "/V2LF/OV2L.png", "/V2LF/RV2L.png" },
			V2RF[] = { "/V2RF/VV2R.png", "/V2RF/OV2R.png", "/V2RF/RV2R.png" };

	JLabel feuV1_1 = new JLabel(), feuV1_2 = new JLabel(),
			feuV2_1 = new JLabel(), feuV2_2 = new JLabel();
	ImageIcon FV1Limg, FV1Rimg, FV2Limg, FV2Rimg;
	Image FV1Lim, FV1Rim, FV2Lim, FV2Rim;
	int w = 50, h = 20;

	public ChangementFeu(Semaphore sfeu1r, Semaphore sfeu2r, Semaphore sfeu1l, Semaphore sfeu2l) {
		this.sfeu1r = sfeu1r;
		this.sfeu2r = sfeu2r;
		this.sfeu1l = sfeu1l;
		this.sfeu2l = sfeu2l;

		FV1Limg = new ImageIcon(getClass().getResource(V1LF[0]));
		FV1Lim = FV1Limg.getImage();
		FV1Lim = FV1Lim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
		feuV1_1.setIcon(new ImageIcon(FV1Lim));
		Carrfour.secondPage.add(feuV1_1);

		FV1Rimg = new ImageIcon(getClass().getResource(V1RF[0]));
		FV1Rim = FV1Rimg.getImage();
		FV1Rim = FV1Rim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
		feuV1_2.setIcon(new ImageIcon(FV1Rim));
		Carrfour.secondPage.add(feuV1_2);

		FV2Limg = new ImageIcon(getClass().getResource(V2LF[2]));
		FV2Lim = FV2Limg.getImage();
		FV2Lim = FV2Lim.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
		feuV2_1.setIcon(new ImageIcon(FV2Lim));
		Carrfour.secondPage.add(feuV2_1);

		FV2Rimg = new ImageIcon(getClass().getResource(V2RF[2]));
		FV2Rim = FV2Rimg.getImage();
		FV2Rim = FV2Rim.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
		feuV2_2.setIcon(new ImageIcon(FV2Rim));
		Carrfour.secondPage.add(feuV2_2);

		feuV1_1.setBounds(603, 470, w, h);
		feuV1_2.setBounds(875, 217, w, h);
		feuV2_1.setBounds(630, 190, h, w);
		feuV2_2.setBounds(875, 470, h, w);
	}

	public void run() {
		try {
			changement();
		} catch (InterruptedException e) {
		}
	}

	private void changement() throws InterruptedException {
		int Feu = 1;
		while (true) {
			sleep(Duree_du_feu);
			if (Feu == 1) {
				sfeu1r.acquire(); // pour attender si il y a une véhicule circule dans le carrefour (à partir de voie 1 à droit)

				FV1Limg = new ImageIcon(getClass().getResource(V1LF[1]));
				FV1Lim = FV1Limg.getImage();
				FV1Lim = FV1Lim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
				feuV1_1.setIcon(new ImageIcon(FV1Lim));
				Carrfour.secondPage.add(feuV1_1);

				FV1Rimg = new ImageIcon(getClass().getResource(V1RF[1]));
				FV1Rim = FV1Rimg.getImage();
				FV1Rim = FV1Rim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
				feuV1_2.setIcon(new ImageIcon(FV1Rim));
				Carrfour.secondPage.add(feuV1_2);

				FV2Limg = new ImageIcon(getClass().getResource(V2LF[1]));
				FV2Lim = FV2Limg.getImage();
				FV2Lim = FV2Lim.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
				feuV2_1.setIcon(new ImageIcon(FV2Lim));
				Carrfour.secondPage.add(feuV2_1);

				FV2Rimg = new ImageIcon(getClass().getResource(V2RF[1]));
				FV2Rim = FV2Rimg.getImage();
				FV2Rim = FV2Rim.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
				feuV2_2.setIcon(new ImageIcon(FV2Rim));
				Carrfour.secondPage.add(feuV2_2);

				sfeu1l.acquire(); // pour attender si il y a une véhicule circule dans le carrefour (à partir de voie 1 à gauche)

				FV1Limg = new ImageIcon(getClass().getResource(V1LF[2]));
				FV1Lim = FV1Limg.getImage();
				FV1Lim = FV1Lim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
				feuV1_1.setIcon(new ImageIcon(FV1Lim));
				Carrfour.secondPage.add(feuV1_1);

				FV1Rimg = new ImageIcon(getClass().getResource(V1RF[2]));
				FV1Rim = FV1Rimg.getImage();
				FV1Rim = FV1Rim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
				feuV1_2.setIcon(new ImageIcon(FV1Rim));
				Carrfour.secondPage.add(feuV1_2);

				FV2Limg = new ImageIcon(getClass().getResource(V2LF[0]));
				FV2Lim = FV2Limg.getImage();
				FV2Lim = FV2Lim.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
				feuV2_1.setIcon(new ImageIcon(FV2Lim));
				Carrfour.secondPage.add(feuV2_1);

				FV2Rimg = new ImageIcon(getClass().getResource(V2RF[0]));
				FV2Rim = FV2Rimg.getImage();
				FV2Rim = FV2Rim.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
				feuV2_2.setIcon(new ImageIcon(FV2Rim));
				Carrfour.secondPage.add(feuV2_2);

				Feu = 2;
				// pour signaler les véhicules attende dans la voie 2 (haute et bas)
				sfeu2r.release();
				sfeu2l.release();
			} else {
				sfeu2r.acquire(); // pour attender si il y a une véhicule circule dans le carrefour (à partir de voie 2 bas)

				FV1Limg = new ImageIcon(getClass().getResource(V1LF[1]));
				FV1Lim = FV1Limg.getImage();
				FV1Lim = FV1Lim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
				feuV1_1.setIcon(new ImageIcon(FV1Lim));
				Carrfour.secondPage.add(feuV1_1);

				FV1Rimg = new ImageIcon(getClass().getResource(V1RF[1]));
				FV1Rim = FV1Rimg.getImage();
				FV1Rim = FV1Rim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
				feuV1_2.setIcon(new ImageIcon(FV1Rim));
				Carrfour.secondPage.add(feuV1_2);

				FV2Limg = new ImageIcon(getClass().getResource(V2LF[1]));
				FV2Lim = FV2Limg.getImage();
				FV2Lim = FV2Lim.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
				feuV2_1.setIcon(new ImageIcon(FV2Lim));
				Carrfour.secondPage.add(feuV2_1);

				FV2Rimg = new ImageIcon(getClass().getResource(V2RF[1]));
				FV2Rim = FV2Rimg.getImage();
				FV2Rim = FV2Rim.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
				feuV2_2.setIcon(new ImageIcon(FV2Rim));
				Carrfour.secondPage.add(feuV2_2);

				sfeu2l.acquire(); // pour attende si il y a une véhicule circule dans le carrefour (à partir de voie 2 haut)
			
				FV1Limg = new ImageIcon(getClass().getResource(V1LF[0]));
				FV1Lim = FV1Limg.getImage();
				FV1Lim = FV1Lim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
				feuV1_1.setIcon(new ImageIcon(FV1Lim));
				Carrfour.secondPage.add(feuV1_1);

				FV1Rimg = new ImageIcon(getClass().getResource(V1RF[0]));
				FV1Rim = FV1Rimg.getImage();
				FV1Rim = FV1Rim.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
				feuV1_2.setIcon(new ImageIcon(FV1Rim));
				Carrfour.secondPage.add(feuV1_2);

				FV2Limg = new ImageIcon(getClass().getResource(V2LF[2]));
				FV2Lim = FV2Limg.getImage();
				FV2Lim = FV2Lim.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
				feuV2_1.setIcon(new ImageIcon(FV2Lim));
				Carrfour.secondPage.add(feuV2_1);

				FV2Rimg = new ImageIcon(getClass().getResource(V2RF[2]));
				FV2Rim = FV2Rimg.getImage();
				FV2Rim = FV2Rim.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
				feuV2_2.setIcon(new ImageIcon(FV2Rim));
				Carrfour.secondPage.add(feuV2_2);

				Feu = 1;
				// pour signaler les véhicules attende dans la voie 1 ( à droite et à gauche)
				sfeu1r.release();
				sfeu1l.release();
			}
		}
	}

}
