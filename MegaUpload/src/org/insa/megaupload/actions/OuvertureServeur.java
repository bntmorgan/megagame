package org.insa.megaupload.actions;

import org.insa.megaupload.entities.MegaPerso;
import org.insa.megaupload.entities.Personnage;
import org.insa.megaupload.entities.Serveur;
import org.insa.megaupload.example.Context;
import org.insa.megaupload.rules.ServeurRules;

public class OuvertureServeur extends Action {

	public OuvertureServeur(Personnage perso, int tempsTotal) {
		super(perso, tempsTotal);
		Context.getMainScreenController().addInfoText(((MegaPerso)perso).getNom() + " set up a new server $" + ServeurRules.getRegleCoutOuverture()*perso.getLieuActuel().getCoutHeberg());
		
		//décompte le cout d'ouverture du serveur du compteur de thune
		Context.decCptThunes(ServeurRules.getRegleCoutOuverture());
	}

	@Override
	public void finished() {
		perso.getLieuActuel().addServeur(new Serveur());
		Context.getMainScreenController().addInfoText(((MegaPerso)perso).getNom() + " succeeds to create the server");
	}

}
