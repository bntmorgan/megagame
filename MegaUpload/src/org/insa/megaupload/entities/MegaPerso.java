package org.insa.megaupload.entities;

import org.insa.megaupload.example.Context;
import org.newdawn.slick.Image;

public class MegaPerso extends Personnage {
	private static int nbPersos = 0;
	
	private String nom;
	private int nbServeursOuverts;
	private int num;
	
	public MegaPerso(String nom, Lieu lieuInitial, Image img) {
		super(lieuInitial, img);
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
			img.drawFlash(10, 100*(1 + num), img.getWidth()*100/img.getHeight(), 100);
		} else {
			img.draw(10, 100*(1 + num), img.getWidth()*100/img.getHeight(), 100);
		}
	}

}