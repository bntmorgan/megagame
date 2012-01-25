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
	
	@Override
	public void update(int delta) {

		MegaPerso poursuivi = this.getPoursuivi();
		if (poursuivi != null) {
			if (this.getLieuActuel() == poursuivi.getLieuActuel()) {
				 this.arreter(poursuivi);
			} else if (this.getDeplacement() == null) {
				this.seDeplacer(poursuivi.getLieuActuel());
			} else if (this.getDeplacement().getCible() != poursuivi.getLieuActuel()) {
				this.seDeplacer(poursuivi.getLieuActuel());
			}
		}
		
		//s'il y a des serveurs sur la position actuelle, ils peuvent etre fermés
		if(!this.getLieuActuel().getServeurs().isEmpty()){
			double rand = Math.random();
			Lieu l = this.getLieuActuel();
			double risque = rand * l.getRisque();
			if( risque > 1){
				l.delServeurs();
			}
		}
		
		super.update(delta);
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
