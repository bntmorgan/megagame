package org.insa.megaupload.entities;

public class Personnage {
	private Lieu lieuActuel;
	
	public Personnage(Lieu lieuInitial) {
		this.lieuActuel = lieuInitial;
	}

	public Lieu getLieuActuel() {
		return lieuActuel;
	}
	
	public void seDeplacer(Trajet t){
		
	}
	
	
}
