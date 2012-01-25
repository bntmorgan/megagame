package org.insa.megaupload.entities;

import org.insa.megaupload.example.Context;
import org.newdawn.slick.Image;

public class MegaPerso extends Personnage {
	private static int nbPersos = 0;
	
	private String nom;
	private int nbServeursOuverts;
	private int num;
	
	public MegaPerso(String nom, Lieu lieuInitial, Image imgBig, Image imgPawn) {
		super(lieuInitial, imgBig, imgPawn);
		this.nom = nom;
		this.nbServeursOuverts = 0;
		this.num = nbPersos;
		nbPersos++;
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
			imgBig.drawFlash(10, 100*(1 + num), imgBig.getWidth()*100/imgBig.getHeight(), 100);
		} else {
			imgBig.draw(10, 100*(1 + num), imgBig.getWidth()*100/imgBig.getHeight(), 100);
		}
	}

}