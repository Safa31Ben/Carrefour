import java.awt.Color;
import java.util.concurrent.Semaphore;

public class ChangementFeu extends Thread {
	private Semaphore sfeu1r , sfeu2r, sfeu1l , sfeu2l;
	private final int Duree_du_feu = 4500;
	public ChangementFeu(Semaphore sfeu1r, Semaphore sfeu2r, Semaphore sfeu1l, Semaphore sfeu2l ) {
		this.sfeu1r = sfeu1r;
		this.sfeu2r = sfeu2r;
		this.sfeu1l = sfeu1l;
		this.sfeu2l = sfeu2l;
	}
	public void run() {
		try {
			changement();
		} catch (InterruptedException e) {}
	}
	
	private void changement() throws InterruptedException {
		int Feu = 1;
		while(true) {
			sleep(Duree_du_feu);
			if(Feu == 1) {
				sfeu1r.acquire();
				Carrfour.feuV1_1.setBackground(new Color(255,215,0));
				Carrfour.feuV1_2.setBackground(new Color(255,215,0));
				Carrfour.feuV2_1.setBackground(new Color(255,215,0));
				Carrfour.feuV2_2.setBackground(new Color(255,215,0));
				sfeu1l.acquire();
				Carrfour.feuV1_1.setBackground(new Color(255,0,0));
				Carrfour.feuV1_2.setBackground(new Color(255,0,0));
				Carrfour.feuV2_1.setBackground(new Color(0,100,0));
				Carrfour.feuV2_2.setBackground(new Color(0,100,0));
				Feu = 2;
				sfeu2r.release();
				sfeu2l.release();
			}else {
				sfeu2r.acquire();
				Carrfour.feuV1_1.setBackground(new Color(255,215,0));
				Carrfour.feuV1_2.setBackground(new Color(255,215,0));
				Carrfour.feuV2_1.setBackground(new Color(255,215,0));
				Carrfour.feuV2_2.setBackground(new Color(255,215,0));
				sfeu2l.acquire();
				Carrfour.feuV1_1.setBackground(new Color(0,100,0));
				Carrfour.feuV1_2.setBackground(new Color(0,100,0));
				Carrfour.feuV2_1.setBackground(new Color(255,0,0));
				Carrfour.feuV2_2.setBackground(new Color(255,0,0));
				Feu = 1;
				sfeu1r.release();
				sfeu1l.release();
			}
		}
	}

}
