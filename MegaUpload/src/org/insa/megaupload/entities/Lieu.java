package org.insa.megaupload.entities;

import java.util.ArrayList;

import org.insa.megaupload.example.Context;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Lieu {
	private String nom;
	private int coutHeberg;
	private int risque;
	private int tempsMiseEnPlace;
	private int x;
	private int y;
	private ArrayList<Trajet> trajets;
	
	private Image img, imgH;

	public Lieu(String nom, int x, int y, int coutHeberg, int risque, int tempsMiseEnPlace) {
		super();
		this.nom = nom;
		this.x = x;
		this.y = y;
		this.coutHeberg = coutHeberg;
		this.risque = risque;
		this.tempsMiseEnPlace = tempsMiseEnPlace;
		this.trajets = new ArrayList<Trajet>();
		try {
			this.img = new Image("resources/img/point-bleu.png");
			this.imgH = new Image("resources/img/point-orange.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
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

	/**
	 * @return the trajets
	 */
	public ArrayList<Trajet> getTrajets() {
		return trajets;
	}

	/**
	 * @param trajet the trajet to add
	 */
	public void addTrajet(Trajet trajet) {
		this.trajets.add(trajet);
	}

}
