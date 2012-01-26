package org.insa.megaupload.actions;

import org.insa.megaupload.entities.Personnage;

public class FermetureServeurs extends Action {

	public FermetureServeurs(Personnage perso, int tempsTotal) {
		super(perso, tempsTotal);
	}

	@Override
	public void finished() {
		perso.getLieuActuel().delServeurs();
	}

}
