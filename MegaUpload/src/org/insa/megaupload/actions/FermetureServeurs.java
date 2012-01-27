package org.insa.megaupload.actions;

import org.insa.megaupload.entities.AgentFBI;
import org.insa.megaupload.entities.Personnage;
import org.insa.megaupload.example.Context;

public class FermetureServeurs extends Action {

	public FermetureServeurs(Personnage perso, int tempsTotal) {
		super(perso, tempsTotal);
	}

	@Override
	public void finished() {
		perso.getLieuActuel().delServeurs();
		perso.seDeplacer(Context.getCarte().getMapLieux().get(AgentFBI.SPAWN_LIEU));
	}

}
