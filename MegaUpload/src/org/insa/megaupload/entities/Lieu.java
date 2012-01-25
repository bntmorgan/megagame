package org.insa.megaupload.entities;

import java.util.ArrayList;

import org.insa.megaupload.example.Context;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Lieu {
	private String nom;
	private int coutHeberg;
	private int risque;
	private int tempsMiseEnPlace;
	private int x;
	private int y;
	private ArrayList<Trajet> trajets;

	private ArrayList<Serveur> serveurs;


	private static int width, height;
	private static int widthH, heightH;
	private static Image img, imgH;

	public Lieu(String nom, int x, int y, int coutHeberg, int risque, int tempsMiseEnPlace) {
		this.nom = nom;
		this.x = x;
		this.y = y;
		this.coutHeberg = coutHeberg;
		this.risque = risque;
		this.tempsMiseEnPlace = tempsMiseEnPlace;
		this.trajets = new ArrayList<Trajet>();
		this.serveurs = new ArrayList<Serveur>();
	}
	


	public static void setImages(Image img, Image imgH) {
		Lieu.img = img;
		Lieu.imgH = imgH;
		width = img.getWidth();
		height = img.getHeight();
		widthH = imgH.getWidth();
		heightH = imgH.getHeight();
	}

	public String getNom() {
		return nom;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getCoutHeberg() {
		return coutHeberg;
	}

	public int getRisque() {
		return risque;
	}

	public int getTempsMiseEnPlace() {
		return tempsMiseEnPlace;
	}
	
	public ArrayList<Trajet> getTrajets() {
		return trajets;
	}

	public void addTrajet(Trajet trajet) {
		this.trajets.add(trajet);
	}
	
	public ArrayList<Serveur> getServeurs() {
		return serveurs;
	}

	public void delServeurs() {
		//décrémente le compteur global de serveurs
		Context.decCptServeurs(this.serveurs.size());
		this.serveurs.removeAll(this.serveurs);
	}

	public void addServeur(Serveur serveur) {
		this.serveurs.add(serveur);
		Context.incCptServeurs();
	}

	public void draw(Graphics g) {
		if (Context.getHoveredLieu() == this) {
			imgH.draw(x - widthH / 2, y - heightH / 2);
			g.drawString(this.nom, x, y + heightH / 2);
			g.drawString(Integer.toString(this.serveurs.size()) + " serveurs" , x, y + heightH);
		} else {
			img.draw(x - width / 2, y - height / 2);
		}
	}
	
	@Override
	public String toString() {
		return this.nom;
	}
}
