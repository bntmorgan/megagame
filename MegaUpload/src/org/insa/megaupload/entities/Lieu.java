package org.insa.megaupload.entities;

import org.insa.megaupload.example.Context;
import org.newdawn.slick.Image;

public class Lieu {

	private String nom;
	private int coutHeberg;
	private int risque;
	private int tempsMiseEnPlace;
	private int x;
	private int y;
	private Image img;
	private Image imgH;

	public Lieu(String nom, int coutHeberg, int risque, int tempsMiseEnPlace) {
		super();
		this.nom = nom;
		this.coutHeberg = coutHeberg;
		this.risque = risque;
		this.tempsMiseEnPlace = tempsMiseEnPlace;
	}

	public String getNom() {
		return nom;
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

	public void draw() {
		if (Context.getSelectedLieu() == this) {
			imgH.draw(x, y);
		} else {
			img.draw(x, y);
		}
	}

}
