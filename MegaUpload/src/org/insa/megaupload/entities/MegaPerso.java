package org.insa.megaupload.entities;

import org.insa.megaupload.example.Action;
import org.insa.megaupload.example.Context;
import org.insa.megaupload.rules.ServeurRules;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MegaPerso extends Personnage {
	private static int nbPersos = 0;
	private static Image moveImg;
	private static Image openServerImg;
	
	private String nom;
	private int nbServeursOuverts;
	private int num;
	private boolean ouvreUnServeur;
	private int tempsRestant;
	private int tempsTotal;
	private Action action;
	
	public MegaPerso(String nom, Lieu lieuInitial, Image imgBig, Image imgPawn) throws SlickException {
		super(lieuInitial, imgBig, imgPawn);

		this.nom = nom;
		this.nbServeursOuverts = 0;
		this.num = nbPersos;
		nbPersos++;
	}
	
	public static void init() throws SlickException {
		openServerImg = new Image("resources/img/Backup-IBM-Server-icon.png");
		moveImg = new Image("resources/img/plane.png");
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public int getNum() {
		return num;
	}

	public int getNbServeursOuverts() {
		return nbServeursOuverts;
	}
	
	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	public void ouvrirServeur() {
		if (!ouvreUnServeur) {
			if (ServeurRules.peutOuvrirServeur()) {
				System.out.println("ouverture serveur");
				ouvreUnServeur = true;
				tempsTotal = 10000;
				tempsRestant = tempsTotal;
			} else {
				// TODO: affichage : pas asez d'argent ! veuillez vendre plus de comptes premium !
			}
		}
	}
	
	@Override
	public void update(int delta) {
		super.update(delta);
		
		if (ouvreUnServeur) {
			if (tempsRestant <= 0) {
				getLieuActuel().addServeur(new Serveur());
				ouvreUnServeur = false;
			} else {
				tempsRestant -= delta;
			}
		}
	}
	
	@Override
	public void seDeplacer(Lieu l) {
		if (!ouvreUnServeur) {
			super.seDeplacer(l);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		
		int persoHeight = 100;
		int persoWidth = imgBig.getWidth()*persoHeight/imgBig.getHeight();
		int actionHeight = 50;
		int openWidth = openServerImg.getWidth()*actionHeight/openServerImg.getHeight();
		int moveWidth = moveImg.getWidth()*actionHeight/moveImg.getHeight();
		int leftmargin = 10;
		
		if (Context.getHoveredPerso() == this || this.action != null) {
			imgBig.draw(leftmargin, leftmargin + (persoHeight + leftmargin)*num, persoWidth, persoHeight);
			openServerImg.draw(leftmargin + persoHeight, leftmargin + (leftmargin + persoHeight)*num, openWidth, actionHeight);
			moveImg.draw(leftmargin + persoHeight, leftmargin + (leftmargin + persoHeight)*num+50, moveWidth, actionHeight);
		} else {
			imgBig.draw(10, 10 + (persoHeight + 10)*num, persoWidth, persoHeight);
		}
		
		if (ouvreUnServeur) {
			g.drawRect(10, (persoHeight + 10)*(num + 1) - 10, persoWidth, 10);
			int filledWidth = (int) (100. * ((double)(tempsTotal - tempsRestant)/(double)tempsTotal));
			g.fillRect(10, (persoHeight + 10)*(num + 1) - 10, filledWidth, 10);
		}
	}

}