package org.insa.megaupload.entities;

public class Trajet {

	private Lieu depart;
	private Lieu arrivee;
	
	public Trajet(Lieu depart, Lieu arrivee) {
		this.depart = depart;
		this.arrivee = arrivee;
	}

	public Lieu getDepart() {
		return depart;
	}
	
	public Lieu getArrivee() {
		return arrivee;
	}
	
	public Lieu getCible(Lieu source) {
		if (source == depart) {
			return arrivee;
		} else if (source == arrivee) {
			return depart;
		} else {
			return null;
		}
	}
	
	// Attribut calculé
	public int getDistance() {
		return 0;
	}
	
	// Attribut calculé
	public int getTemps() {
		return 0;
	}
	
}
