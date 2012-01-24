package org.insa.megaupload.entities;

public class Trajet {

	private Lieu depart;
	private Lieu arrivee;
	private int distance;
	
	/**
	 * @param depart
	 * @param arrivee
	 * @param distance
	 */
	public Trajet(Lieu depart, Lieu arrivee, int distance) {
		super();
		this.depart = depart;
		this.arrivee = arrivee;
		this.distance = distance;
	}



	public Lieu getDepart() {
		return depart;
	}
	
	public Lieu getArrivee() {
		return arrivee;
	}
	
	public int getDistance() {
		return distance;
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
	public int getTemps() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "Trajet : "+depart+" "+arrivee;
	}
}
