package org.insa.megaupload.entities;

public class Serveur {
	private int gainArgent;
	private int gainCulturel;
	private Lieu lieuHebergement;
	
	
	public Serveur(int gainArgent, int gainCulturel, Lieu lieuHebergement) {
		super();
		this.gainArgent = gainArgent;
		this.gainCulturel = gainCulturel;
		this.lieuHebergement = lieuHebergement;
	}
	
	public int getGainArgent() {
		return gainArgent;
	}
	
	public int getGainCulturel() {
		return gainCulturel;
	}
	
	public Lieu getLieuHebergement() {
		return lieuHebergement;
	}
	
	
	
	
}
