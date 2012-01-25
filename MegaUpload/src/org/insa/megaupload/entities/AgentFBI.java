package org.insa.megaupload.entities;

import org.newdawn.slick.Image;

public class AgentFBI extends Personnage {
	private MegaPerso poursuivi;
	protected final static double coefRand = 0.9;
	
	public double getCoefRand() {
		return coefRand;
	}
	
	public AgentFBI(Lieu lieuInitial, Image img) {
		super(lieuInitial, img);
	}
	
	public void poursuivre(MegaPerso perso) {
		this.poursuivi = perso;
	}
	
	public MegaPerso getPoursuivi() {
		return this.poursuivi;
	}
	
	public void arreter(MegaPerso perso) {
		if (this.getLieuActuel() == perso.getLieuActuel()) {
			//System.out.println(perso.getNom() + " arrêté!");
			//this.poursuivi = null;
		}
	}
	
	public void fermerServeur() {
		this.getLieuActuel().delServeurs();
	}

}
