import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Carrfour implements ActionListener {

	static JFrame frmCarrfour;
	static SecondPage secondPage;
	static FirstPage firstPage;
	JButton btnStart;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Carrfour();
			}
		});
	}

	public Carrfour() {
		initialize();
	}

	JLayeredPane layeredPane;
	CardLayout card;

	private void initialize() {
		frmCarrfour = new JFrame();
		frmCarrfour.setResizable(false);
		frmCarrfour.setSize(new Dimension(1360, 730));
		frmCarrfour.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 1360, 730);
		frmCarrfour.getContentPane().add(layeredPane);

		card = new CardLayout();
		layeredPane.setLayout(card);

		firstPage = new FirstPage();
		firstPage.setLayout(null);

		btnStart = new JButton("Start");
		btnStart.setFont(new Font("Inj Free", Font.ITALIC | Font.BOLD, 22));
		btnStart.setBackground(Color.black);
		btnStart.setForeground(new Color(220, 220, 220));
		btnStart.setBorder(new LineBorder(Color.black, 2, true));
		btnStart.setFocusPainted(false);
		firstPage.add(btnStart);
		btnStart.addActionListener(this);
		btnStart.setBounds(595, 500, 150, 40);

		secondPage = new SecondPage();
		secondPage.setLayout(null);

		layeredPane.add(firstPage, "FirstPage");
		layeredPane.add(secondPage, "Carrefour");

		JLabel p1 = new JLabel();
		ImageIcon p1img = new ImageIcon(getClass().getResource("/p/2.png"));
		Image p1im = p1img.getImage();
		p1im = p1im.getScaledInstance(60, 40, java.awt.Image.SCALE_SMOOTH);
		p1.setIcon(new ImageIcon(p1im));
		secondPage.add(p1);
		p1.setBounds(1045, 45, 60, 40);

		JLabel p4 = new JLabel();
		ImageIcon p4img = new ImageIcon(getClass().getResource("/p/6.png"));
		Image p4im = p4img.getImage();
		p4im = p4im.getScaledInstance(40, 60, java.awt.Image.SCALE_SMOOTH);
		p4.setIcon(new ImageIcon(p4im));
		Carrfour.secondPage.add(p4);
		p4.setBounds(1120, 540, 40, 60);

		JLabel p2 = new JLabel();
		ImageIcon p2img = new ImageIcon(getClass().getResource("/p/4.png"));
		Image p2im = p2img.getImage();
		p2im = p2im.getScaledInstance(40, 25, java.awt.Image.SCALE_SMOOTH);
		p2.setIcon(new ImageIcon(p2im));
		Carrfour.secondPage.add(p2);
		p2.setBounds(220, 640, 40, 25);

		JLabel p3 = new JLabel();
		ImageIcon p3img = new ImageIcon(getClass().getResource("/p/7.png"));
		Image p3im = p3img.getImage();
		p3im = p3im.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		p3.setIcon(new ImageIcon(p3im));
		Carrfour.secondPage.add(p3);
		p3.setBounds(520, 100, 30, 30);

		frmCarrfour.pack();
		frmCarrfour.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStart) {
			card.show(layeredPane, "Carrefour");
			startCir();
		}
	}

	public static void startCir() {
		try {
			Semaphore sfeu1r = new Semaphore(1, true), sfeu2r = new Semaphore(0, true),
					sfeu1l = new Semaphore(1, true), sfeu2l = new Semaphore(0, true), 
					busStationL = new Semaphore(1, true), busStationR = new Semaphore(1, true);

			int coordonneesVoie1[] = { -150, -60, 30, 120, 210, 300, 390, 480, 570, 845, 935, 1025, 1115, 1205, 1295, 1385 },
				coordonneesVoie2[] = { -110, -20, 70, 160, 440, 530, 620, 710 };

			int[][] coordonnees = { coordonneesVoie1, coordonneesVoie2 };

			String type[] = { "car", "car", "car", "bus", "bus" };

			ArrayList<Semaphore> svoie1L = new ArrayList<Semaphore>();
			ArrayList<Semaphore> svoie2L = new ArrayList<Semaphore>();
			ArrayList<Semaphore> svoie1R = new ArrayList<Semaphore>();
			ArrayList<Semaphore> svoie2R = new ArrayList<Semaphore>();
			ArrayList<Semaphore> crossroads = new ArrayList<Semaphore>();
			ArrayList<Semaphore> busStation = new ArrayList<Semaphore>();

			Semaphore v1 = new Semaphore(1, true);
			Semaphore v2 = new Semaphore(1, true);
			Semaphore v3 = new Semaphore(1, true);
			Semaphore v4 = new Semaphore(1, true);

			Semaphore waitBusl = new Semaphore(0, true), waitBusr = new Semaphore(0, true);

			busStation.add(busStationL);
			busStation.add(busStationR);

			ArrayList<ArrayList<Semaphore>> semaphores = new ArrayList<ArrayList<Semaphore>>();
			semaphores.add(svoie1L);
			semaphores.add(svoie2L);
			semaphores.add(svoie1R);
			semaphores.add(svoie2R);
			semaphores.add(busStation);
			semaphores.add(crossroads);

			for (int i = 0; i < 4; i++) {
				crossroads.add(new Semaphore(1, true));
			}
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
			System.out.println(nb_v1r);
			for (int i = 0; i < nb_v1r; i++) {
				Voie1R v1r = new Voie1R(sfeu1r, semaphores, coordonnees, type[(new Random()).nextInt(5)], waitBusl);
				v1r.start();
			}

			int nb_v1l = (new Random()).nextInt(20);
			System.out.println(nb_v1l);
			for (int i = 0; i < nb_v1l; i++) {
				Voie1L v1l = new Voie1L(sfeu1l, semaphores, coordonnees, type[(new Random()).nextInt(5)], waitBusr);
				v1l.start();
			}

			int nb_v2l = (new Random()).nextInt(20);
			System.out.println(nb_v2l);
			for (int i = 0; i < nb_v2l; i++) {
				Voie2L v2l = new Voie2L(sfeu2l, semaphores, coordonnees, type[(new Random()).nextInt(5)], waitBusl);
				v2l.start();
			}

			int nb_v2r = (new Random()).nextInt(20);
			System.out.println(nb_v2r);
			for (int i = 0; i < nb_v2r; i++) {
				Voie2R v2r = new Voie2R(sfeu2r, semaphores, coordonnees, type[(new Random()).nextInt(5)], waitBusl);
				v2r.start();
			}

			int nb_p1r = (new Random()).nextInt(10);
			System.out.println(nb_p1r);
			for (int i = 0; i < nb_p1r; i++) {
				personne1r p1r = new personne1r(waitBusr, v3);
				p1r.start();
			}

			int nb_p1l = (new Random()).nextInt(10);
			System.out.println(nb_p1l);
			for (int i = 0; i < nb_p1l; i++) {
				personne1l p1l = new personne1l(waitBusl, v1);
				p1l.start();
			}

			int nb_p1l2r = (new Random()).nextInt(10);
			System.out.println(nb_p1l2r);
			for (int i = 0; i < nb_p1l2r; i++) {
				personne1l2r p1l2r = new personne1l2r(v2);
				p1l2r.start();
			}
			int nb_p1l2l = (new Random()).nextInt(10);
			System.out.println(nb_p1l2l);
			for (int i = 0; i < nb_p1l2l; i++) {
				personne1l2l p1l2l = new personne1l2l(v4);
				p1l2l.start();
			}
		} catch (Exception e) {
		}
	}
}
