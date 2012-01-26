package org.insa.megaupload.actions;

import org.insa.megaupload.entities.Personnage;
import org.insa.megaupload.entities.Serveur;

public class OuvertureServeur extends Action {

	public OuvertureServeur(Personnage perso, int tempsTotal) {
		super(perso, tempsTotal);
	}

	@Override
	public void finished() {
		perso.getLieuActuel().addServeur(new Serveur());
	}

}
