package org.insa.megaupload.entities;

import java.util.ArrayList;

import org.insa.megaupload.example.Context;
import org.newdawn.slick.Image;

public class Lieu {
	private String nom;
	private int coutHeberg;
	private int risque;
	private int tempsMiseEnPlace;
	private int x;
	private int y;
	private ArrayList<Trajet> trajets;

	private static int width;
	private static int height;
	private static Image img, imgH;

	public Lieu(String nom, int x, int y, int coutHeberg, int risque, int tempsMiseEnPlace) {
		this.nom = nom;
		this.x = x;
		this.y = y;
		this.coutHeberg = coutHeberg;
		this.risque = risque;
		this.tempsMiseEnPlace = tempsMiseEnPlace;
		this.trajets = new ArrayList<Trajet>();
	}
	
	public static void setImages(Image img, Image imgH) {
		Lieu.img = img;
		Lieu.imgH = imgH;
		width = img.getWidth();
		height = img.getHeight();
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

	public void draw() {
		if (Context.getSelectedLieu() == this) {
			imgH.draw(x - width / 2, y - height / 2);
		} else {
			img.draw(x - width / 2, y - height / 2);
		}
	}
}
