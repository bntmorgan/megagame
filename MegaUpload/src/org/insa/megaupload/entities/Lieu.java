package org.insa.megaupload.entities;

public class Lieu {
	
	private String nom;
	private int coutHeberg;
	private int risque;
	private int tempsMiseEnPlace;
	
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

	
	

}
