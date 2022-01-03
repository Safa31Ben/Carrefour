import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;
//import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import javax.swing.JButton;

public class Carrfour {

	static JFrame frmCarrfour;
	static JPanel feuV1_1;
	static JPanel feuV1_2;
	static JPanel feuV2_1;
	static JPanel feuV2_2;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Carrfour();
					Carrfour.frmCarrfour.setVisible(true);

					Semaphore sfeu1r = new Semaphore(1, true), sfeu2r = new Semaphore(0, true),
							sfeu1l = new Semaphore(1, true), sfeu2l = new Semaphore(0, true),
							arrL = new Semaphore(1, true), arrR = new Semaphore(1, true);
					int coordonneesVoie1[] = { -150, -60, 30, 120, 210, 300, 390, 480, 570, 845, 935, 1025, 1115, 1205,
							1295, 1385 }, coordonneesVoie2[] = { -110, -20, 70, 160, 440, 530, 620, 710 };

					String type[] = { "voiture", "bus" };

					ArrayList<Semaphore> svoie1L = new ArrayList<Semaphore>();
					ArrayList<Semaphore> svoie2L = new ArrayList<Semaphore>();
					ArrayList<Semaphore> svoie1R = new ArrayList<Semaphore>();
					ArrayList<Semaphore> svoie2R = new ArrayList<Semaphore>();

					for (int i = 0; i < coordonneesVoie1.length; i++) {
						svoie1L.add(new Semaphore(1, true));
						svoie1R.add(new Semaphore(1, true));
					}
					for (int i = 0; i < coordonneesVoie2.length; i++) {
						svoie2L.add(new Semaphore(1, true));
						svoie2R.add(new Semaphore(1, true));
					}

					ChangementFeu ch = new ChangementFeu(sfeu1r, sfeu2r, sfeu1l, sfeu2l);
					ch.start();

					int nb_v1r = (new Random()).nextInt(20);
					for (int i = 0; i < nb_v1r; i++) {
						Voie1R v1r = new Voie1R(sfeu1r, svoie1R, coordonneesVoie1, type[(new Random()).nextInt(2)],
								arrR);
						v1r.start();
					}
					int nb_v1l = (new Random()).nextInt(20);
					for (int i = 0; i < nb_v1l; i++) {
						Voie1L v1l = new Voie1L(sfeu1l, svoie1L, coordonneesVoie1, type[(new Random()).nextInt(2)],
								arrL);
						v1l.start();
					}
					int nb_v2l = (new Random()).nextInt(20);
					for (int i = 0; i < nb_v2l; i++) {
						Voie2L v2l = new Voie2L(sfeu2l, svoie2L, coordonneesVoie2);
						v2l.start();
					}

					int nb_v2r = (new Random()).nextInt(20);
					for (int i = 0; i < nb_v2r; i++) {
						Voie2R v2r = new Voie2R(sfeu2r, svoie2R, coordonneesVoie2);
						v2r.start();
					}

				} catch (Exception e) {
				}
			}
		});
	}

	public Carrfour() {
		initialize();
	}

	private void initialize() {
		frmCarrfour = new JFrame();
		frmCarrfour.getContentPane().setBackground(Color.GRAY);
		frmCarrfour.setTitle("Carrfour");
		frmCarrfour.setBounds(0, 0, 1360, 730);
		frmCarrfour.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCarrfour.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.GREEN);
		panel.setBounds(340, 0, 320, 250);
		frmCarrfour.getContentPane().add(panel);
		panel.setLayout(null);

		feuV2_1 = new JPanel();
		feuV2_1.setBounds(295, 224, 15, 15);
		panel.add(feuV2_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GREEN);
		panel_1.setBounds(340, 440, 320, 250);
		frmCarrfour.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		feuV1_1 = new JPanel();
		feuV1_1.setBounds(295, 11, 15, 15);
		panel_1.add(feuV1_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.GREEN);
		panel_2.setBounds(845, 0, 500, 250);
		frmCarrfour.getContentPane().add(panel_2);
		panel_2.setLayout(null);

		feuV1_2 = new JPanel();
		feuV1_2.setBounds(10, 225, 15, 15);
		panel_2.add(feuV1_2);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.GREEN);
		panel_3.setBounds(845, 440, 500, 250);
		frmCarrfour.getContentPane().add(panel_3);
		panel_3.setLayout(null);

		feuV2_2 = new JPanel();
		feuV2_2.setBounds(10, 11, 15, 15);
		panel_3.add(feuV2_2);

		feuV1_1.setBackground(new Color(0, 100, 0));
		feuV1_2.setBackground(new Color(0, 100, 0));
		feuV2_1.setBackground(new Color(255, 0, 0));
		feuV2_2.setBackground(new Color(255, 0, 0));

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(0, 0, 175, 250);
		frmCarrfour.getContentPane().add(panel_4);
		panel_4.setBackground(Color.GREEN);

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(175, 0, 165, 190);
		frmCarrfour.getContentPane().add(panel_5);
		panel_5.setBackground(Color.GREEN);

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBackground(Color.GREEN);
		panel_1_1.setBounds(0, 440, 175, 250);
		frmCarrfour.getContentPane().add(panel_1_1);

		JPanel panel_1_2 = new JPanel();
		panel_1_2.setLayout(null);
		panel_1_2.setBackground(Color.GREEN);
		panel_1_2.setBounds(175, 500, 165, 190);
		frmCarrfour.getContentPane().add(panel_1_2);
	}
}
