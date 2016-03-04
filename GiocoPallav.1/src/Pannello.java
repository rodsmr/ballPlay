import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.Random;

class Pannello extends JPanel {

	protected Random generatore; /** Generatore random */
	protected int x; /** Posizione x iniziale del cerchio */
	protected int y; /** Posizione y iniziale del cerchio */
	protected int fineX; /** Mi indica quando il cerchio raggiunge la x massima */
	protected int fineY; /** Mi indica quando il cerchio raggiunge la y massima */
	protected int ritardo; /** Ritardo del timer */
	protected Timer tempo; /** Timer */
	protected Rectangle barra; /** Barra su cui rimbalzerà la pallina */
	protected Rectangle contorno; /** Rettangolo che delimita il campo di gioco */
	protected int xBarra; /** Posizione x iniziale della barra */
	protected int yBarra; /** Posizione y iniziale della barra */
	protected int diametro; /** Diametro del cerchio */
	protected JLabel scrittaPunteggio; /** Scritta per il punteggio */
	protected int punteggio; /** Contatore per il punteggio */
	protected JLabel scrittaLivello; /** Scritta per il livello */
	protected int livello; /** Contatore per il livelli */
	protected JLabel scrittaNome; /** Scritta per il nome */
	protected String nomeUtente; /** Nome dell'utente */
	protected int lunghezzaBarra; /** Variabile per la lunghezza della barra */
	protected int colpito; /** Variabile per contare quante volte colpisco il mattone */
	protected int xPillola; /** Posizione iniziale della x della pillola */
	protected int yPillola; /** Posizione iniziale della y della pillola */
	protected boolean disegna; /** Serve a fare disegnre o meno la pillola */
	protected int barraBeccataPillola; /** Serve a far cambiare il colore alla barra */
	protected Color colore; /** Variabile che contiene i colori utilizzati */
	protected Graphics2D pennello2D; /** Pennello di tipo 2D */

	/** Costruttore */
	public Pannello(String nomeUtente) {

		generatore = new Random();

		x = generatore.nextInt(500)+100;
		y = generatore.nextInt(100)+100;

		fineX = 0;
		fineY = 0;

		xPillola = 380;
		yPillola = 150;

		ritardo = 14;

		tempo = new Timer(ritardo, new AscoltatoreTimer());

		xBarra = 280;
		yBarra = 443;
		lunghezzaBarra = 200;

		diametro = 50;

		punteggio = 0;

		disegna = false;

		barraBeccataPillola = 0;

		colore = Color.blue;

		scrittaPunteggio = new JLabel(punteggio + " punti");
		scrittaPunteggio.setBounds(150, 450, 60, 60);
		this.add(scrittaPunteggio);

		livello = 0;

		scrittaLivello = new JLabel("Livello: " + livello);
		scrittaLivello.setBounds(300, 450, 60, 60);
		this.add(scrittaLivello);

		this.nomeUtente = nomeUtente;

		scrittaNome = new JLabel("Nome: " + nomeUtente);
		scrittaNome.setBounds(400, 450, 100, 60);
		this.add(scrittaNome);

		colpito = 0;

		this.setLayout(null);
	}

	public void paint(Graphics pennello) {

		super.paint(pennello);

		/** Creazione oggetto di tipo Graphics2D */
		pennello2D = (Graphics2D)pennello;

		/** Creazione del campo esterno */
		contorno = new Rectangle(100, 100, 587, 363);
		pennello2D.setColor(Color.black);
		pennello2D.draw(contorno);

		/** Creazione della palla */
		Ellipse2D.Double palla = new Ellipse2D.Double(x, y, diametro, diametro);
		pennello2D.setColor(Color.green);
		pennello2D.fill(palla);
		pennello2D.setColor(Color.black);
		pennello2D.draw(palla);

		/** Creazione della barra */
		barra = new Rectangle(xBarra, yBarra, lunghezzaBarra, 20);
		pennello2D.setColor(colore);
		pennello2D.fill(barra);

		if (punteggio >= 1300) {

			if (colpito < 4) {
				Color oro = new Color(255, 215, 0);
				Rectangle aiuto = new Rectangle(350, 110, 70, 30);
				pennello2D.setColor(oro);
				pennello2D.fill(aiuto);
			}

			 if (colpito == 4 && disegna == true) {

			 	pennello2D.setColor(colore);

				if (barraBeccataPillola != 0) {

					lunghezzaBarra = 150;

					barra = new Rectangle(xBarra, yBarra, lunghezzaBarra, 20);
					pennello2D.fill(barra);
				}

				else {

					lunghezzaBarra = 50;

					barra = new Rectangle(xBarra, yBarra, lunghezzaBarra, 20);
					pennello2D.fill(barra);
				}

				Color argento = new Color(192, 192, 192);
				Ellipse2D.Double pillola = new Ellipse2D.Double(xPillola, yPillola, 30, 30);
				pennello2D.setColor(argento);
				pennello2D.fill(pillola);
			}
		}
	}

	public class AscoltatoreTimer implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			int raggio; /** Raggio del cerchio */
			raggio = diametro / 2;

			if (y > 410) {
				JOptionPane.showMessageDialog(null, "GAME OVER!");
				tempo.stop();
			}
			else {
				if (x > 630) {
					fineX = 1;
				}
				if (x < 100) {
					fineX = 0;
					x += 2;
					y += 3;
				}
				if (y < 100) {
					fineY = 0;
					y += 3;
					x -= 2;
				}
				/** Rimbalzo sulla barra */
				if ((y >= 390) && (y < 410) && ((x+raggio) >= xBarra) && ((x+raggio) <= (xBarra + lunghezzaBarra)) && (fineX == 1)) {
					fineX = 1;
					fineY = 1;
					punteggio+=50;
					scrittaPunteggio.setText(punteggio + " punti");
				}
				if ((y >= 390) && (y < 410) && ((x+raggio) >= xBarra) && ((x+raggio) <= (xBarra + lunghezzaBarra)) && (fineX == 0)) {
					fineX = 0;
					fineY = 1;
					punteggio+=50;
					scrittaPunteggio.setText(punteggio + " punti");
				}
				/** Colpimento mattone */
				if (punteggio >= 1300 && colpito < 4) {
					if ((y >= 110) && (y <= 140) && (x+raggio >= 350) && (x+raggio <= 350+70) && (fineX == 1)){
						colpito++;
						fineX = 1;
						fineY = 0;
					}
					if ((y >= 110) && (y <= 140) && (x+raggio >= 350) && (x+raggio <= 350+70) && (fineX == 0)){
						colpito++;
						fineX = 0;
						fineY = 0;
					}
					if ((x == 350) && (y+raggio >= 150) && (y+raggio <= 150+30) && (fineY == 1)) {
						colpito++;
						fineX = 0;
						fineY = 1;
					}
					if ((x == 350) && (y+raggio >= 150) && (y+raggio <= 150+30) && (fineY == 0)) {
						colpito++;
						fineX = 0;
						fineY = 0;
					}
					if ((x == 350+70) && (y+raggio >= 150) && (y+raggio <= 150+30) && (fineY == 1)) {
						colpito++;
						fineX = 0;
						fineY = 1;
					}
					if ((x == 350+70) && (y+raggio >= 150) && (y+raggio <= 150+30) && (fineY == 0)) {
						colpito++;
						fineX = 0;
						fineY = 0;
					}
				}
				if (punteggio >= 1300 && colpito >= 4) {
					if (yPillola <= 430) {
						yPillola += 5;
						xPillola += 0;
						disegna = true;
					}
					else {
						yPillola += 0;
						xPillola += 0;
						disegna = false;
					}

					if ((yPillola >= 390) && (yPillola < 410) && (xPillola+raggio >= xBarra) && (xPillola+raggio <= xBarra+lunghezzaBarra)) {
						barraBeccataPillola++;
					}
				}
				if (fineX == 0) {
					x += 2;
				}
				else {
					x -= 2;
				}
				if (fineY == 0) {
					y += 3;
				}
				else {
					y -= 3;
				}

				if (punteggio >= 100) {
					ritardo = 10;
					tempo.setDelay(ritardo);
					lunghezzaBarra = 150;
					livello = 1;
					scrittaLivello.setText("Livello: " + livello);
				}

				if (punteggio >= 300) {
					lunghezzaBarra = 100;
					livello = 2;
					scrittaLivello.setText("Livello: " + livello);
				}

				if (punteggio >= 500) {
					ritardo = 8;
					tempo.setDelay(ritardo);
					livello = 3;
					scrittaLivello.setText("Livello: " + livello);
				}

				if (punteggio >= 800) {
					lunghezzaBarra = 75;
					livello = 4;
					scrittaLivello.setText("Livello: " + livello);
				}

				if (punteggio >= 1000) {
					ritardo = 7;
					tempo.setDelay(ritardo);
					lunghezzaBarra = 50;
					livello = 5;
					scrittaLivello.setText("Livello: " + livello);
				}

				if (punteggio >= 1300) {
					ritardo = 5;
					tempo.setDelay(ritardo);
					if (barraBeccataPillola == 0) {
						lunghezzaBarra = 50;
						colore = Color.red;
					}
					else {
						lunghezzaBarra = 150;
						colore = Color.yellow;
					}
					livello = 6;
					scrittaLivello.setText("Livello: " + livello);

				}

				repaint();
			}
		}
	}
}
