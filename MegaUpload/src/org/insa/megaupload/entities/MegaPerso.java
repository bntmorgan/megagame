package org.insa.megaupload.entities;

import org.insa.megaupload.actions.OuvertureServeur;
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
	private Action hoveredAction;
	
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

	public int getNbServeursOuverts() {
		return nbServeursOuverts;
	}

	/**
	 * @return the hoveredAction
	 */
	public Action getHoveredAction() {
		return hoveredAction;
	}

	/**
	 * @param hoveredAction the hoveredAction to set
	 */
	public void setHoveredAction(Action hoveredAction) {
		this.hoveredAction = hoveredAction;
	}

	public void ouvrirServeur() {
		if (this.action == null) {
			if (ServeurRules.peutOuvrirServeur()) {
				System.out.println("ouverture serveur");
				// XXX: constante
				setAction(new OuvertureServeur(this, 10000));
			} else {
				// TODO: affichage : pas asez d'argent ! veuillez vendre plus de comptes premium !
			}
		}
	}
	
	public int getAvatarX() {
		// XXX: constante
		return 10;
	}
	
	public int getAvatarY() {
		// XXX: constantes
		return 10 + (getAvatarHeight() + 10) * num;
	}
	
	public int getAvatarWidth() {
		return imgBig.getWidth() * getAvatarHeight() / imgBig.getHeight();
	}
	
	public int getAvatarHeight() {
		// XXX: constante
		return 100;
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		
		int actionHeight = 50;
		int openWidth = openServerImg.getWidth()*actionHeight/openServerImg.getHeight();
		int moveWidth = moveImg.getWidth()*actionHeight/moveImg.getHeight();
		
		if (Context.getHoveredPerso() == this || this.hoveredAction != null) {
			imgBig.draw(getAvatarX(), getAvatarY(), getAvatarWidth(), getAvatarHeight());
			openServerImg.draw(getAvatarX() + getAvatarWidth(), getAvatarY(), openWidth, actionHeight);
			moveImg.draw(getAvatarX() + getAvatarWidth(), getAvatarY() + 50, moveWidth, actionHeight);
		} else {
			imgBig.draw(getAvatarX(), getAvatarY(), getAvatarWidth(), getAvatarHeight());
		}
	}

}