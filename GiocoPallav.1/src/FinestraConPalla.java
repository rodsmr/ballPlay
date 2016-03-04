/**
 * @(#)FinestraConPalla.java
 *
 * FinestraConPalla application
 *
 * @author
 * @version 1.00 2011/1/19
 */

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class FinestraConPalla {

	protected static Pannello panel;
	protected static JLabel inizio;
	protected static String nome;

    public static void main(String[] args) {

    	/** Creazione e settaggio della finestra */
    	JFrame finestra = new JFrame("La palla");
    	finestra.setSize(800, 600);
    	finestra.setLocation(100, 100);
    	finestra.setResizable(false);
    	finestra.setDefaultCloseOperation(finestra.EXIT_ON_CLOSE);

		/** Indicazioni per l'utente */
		inizio = new JLabel("Premi INVIO per iniziare a giocare!");
		inizio.setBounds(200, 300, 420, 200);
		inizio.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		finestra.add(inizio);

		nome = JOptionPane.showInputDialog("Nome Utente: ", "Inserisci il tuo nickname");

    	/** Creazione ed inserimento dell'oggetto pannello nella finestra */
		panel = new Pannello(nome);
		finestra.add(panel);

		finestra.addKeyListener(new AscoltatoreTastiera());

		finestra.setVisible(true);
    }

	public static class AscoltatoreTastiera implements KeyListener {

		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode() == 37) {
				panel.xBarra -= 20;
			}

			if (e.getKeyCode() == 39) {
				panel.xBarra += 20;
			}

			if (e.getKeyCode() == 10) {
				inizio.setText("");
				panel.tempo.start();
			}

			if (panel.xBarra < 101) {
				panel.xBarra = 101;
			}

			if (panel.xBarra+panel.lunghezzaBarra > 687) {
				panel.xBarra = 687 - panel.lunghezzaBarra;
			}
		}

    	public void keyReleased(KeyEvent e) {

    	}

    	public void keyTyped(KeyEvent e) {

    	}
	}
}
