package org.insa.megaupload.entities;

public class Trajet {

	private Lieu depart;
	private Lieu arrivee;
	
	/**
	 * @param depart
	 * @param arrivee
	 */
	public Trajet(Lieu depart, Lieu arrivee) {
		super();
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
	
	public int getDistance() {
		return (int) Math.sqrt((depart.getX() - arrivee.getX()) * (depart.getX() - arrivee.getX()) +
				(depart.getY() - arrivee.getY()) * (depart.getY() - arrivee.getY()));
	}
	
	public int getTemps() {
		return getDistance() * 10;
	}
	
	@Override
	public String toString() {
		return "Trajet : "+depart+" "+arrivee;
	}
}
