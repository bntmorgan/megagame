package org.insa.megaupload.entities;

public class MegaPerso extends Personnage{
	private String nom;
	private int nbServeursOuverts;
	
	
	public MegaPerso(String nom, Lieu lieuInitial) {
		super(lieuInitial);
		this.nom = nom;
		this.nbServeursOuverts=0;
	}

	public int getNbServeursOuverts() {
		return nbServeursOuverts;
	}
	 
	public void ouvrirServeur(){
		
	}
	
	

}
