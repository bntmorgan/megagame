package org.insa.megaupload.entities;

import org.newdawn.slick.Image;

public class AgentFBI extends Personnage {
	private MegaPerso poursuivi;
	
	public AgentFBI(Lieu lieuInitial, Image img) {
		super(lieuInitial, img);
	}
	
	public void fermerServeur() {
		this.getLieuActuel().delServeurs();
	}

}
