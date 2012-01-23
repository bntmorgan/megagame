package org.insa.megaupload.entities;

public class Trajet {

	private Lieu lieuDepart;
	private Lieu lieuArrivee;
	
	public Trajet(Lieu lieuDepart, Lieu lieuArrivee) {
		this.lieuDepart = lieuDepart;
		this.lieuArrivee = lieuArrivee;
	}

	public Lieu getLieuDepart() {
		return lieuDepart;
	}
	
	public Lieu getLieuArrivee() {
		return lieuArrivee;
	}
	
	//attribut calculé
	public int getDistance(){
		return 0;
	}
	//attribut calculé
	public int getTemps(){
		return 0;
	}
	
	
}
