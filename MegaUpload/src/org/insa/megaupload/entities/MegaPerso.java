package org.insa.megaupload.entities;

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
	
	public MegaPerso(String nom, Lieu lieuInitial, Image imgBig, Image imgPawn) throws SlickException {
		super(lieuInitial, imgBig, imgPawn);

		this.nom = nom;
		this.nbServeursOuverts = 0;
		this.num = nbPersos;
		nbPersos++;
	}
	
	public static void init() throws SlickException {
		openServerImg = new Image("resources/img/Backup-IBM-Server-icon-30px.png");
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
	
	public void ouvrirServeur() {
		if (!ouvreUnServeur) {
			if (ServeurRules.peutOuvrirServeur()) {
				System.out.println("ouverture serveur");
				ouvreUnServeur = true;
				tempsTotal = 10000;
				tempsRestant = tempsTotal;
			} else {
				System.out.println("fuck !!");
				//affichage : pas asez d'argent ! veuillez vendre plus de comptes premium !
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
		
		int height = 100;
		int width = imgBig.getWidth()*100/imgBig.getHeight(); 
		
		if (Context.getHoveredPerso() == this) {
			imgBig.draw(10, 10 + (height + 10)*num, width, height);
			openServerImg.draw(110, 10 + 110*num, openServerImg.getWidth()*100/openServerImg.getHeight(), 50);
			moveImg.draw(110, 10 + 110*num+50, moveImg.getWidth()*100/moveImg.getHeight(), 50);
		} else {
			imgBig.draw(10, 10 + (height + 10)*num, width, height);
		}
		
		if (ouvreUnServeur) {
			g.drawRect(10, (height + 10)*(num + 1) - 10, width, 10);
			int filledWidth = (int) (100. * ((double)(tempsTotal - tempsRestant)/(double)tempsTotal));
			g.fillRect(10, (height + 10)*(num + 1) - 10, filledWidth, 10);
		}
	}

}