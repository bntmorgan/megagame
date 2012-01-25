package org.insa.megaupload.entities;

import org.insa.megaupload.example.Context;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MegaPerso extends Personnage {
	private static int nbPersos = 0;
	
	private String nom;
	private int nbServeursOuverts;
	private int num;
	private Image img1;
	private Image img2;
	
	public MegaPerso(String nom, Lieu lieuInitial, Image imgBig, Image imgPawn) throws SlickException {
		super(lieuInitial, imgBig, imgPawn);

		this.nom = nom;
		this.nbServeursOuverts = 0;
		this.num = nbPersos;
		nbPersos++;
		
		this.img1 = new Image("resources/img/Backup-IBM-Server-icon-30px.png");
		this.img2 = new Image("resources/img/plane.png");
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
	
	public void draw() {
		super.draw();
		
		if (Context.getHoveredPerso() == this) {

			imgBig.draw(10, 100*(1 + num), imgBig.getWidth()*100/imgBig.getHeight(), 100);
			img1.draw(110, 100*(1 + num)-25, img1.getWidth()*100/img2.getHeight(), 50);
			img2.draw(110, 100*(1 + num)+25, img2.getWidth()*100/img2.getHeight(), 50);

		} else {
			imgBig.draw(10, 100*(1 + num), imgBig.getWidth()*100/imgBig.getHeight(), 100);
		}
	}

}