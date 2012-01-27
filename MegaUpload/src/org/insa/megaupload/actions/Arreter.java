/**
 * 
 */
package org.insa.megaupload.actions;

import org.insa.megaupload.entities.AgentFBI;
import org.insa.megaupload.entities.MegaPerso;
import org.insa.megaupload.entities.Personnage;
import org.insa.megaupload.example.Context;

/**
 * @author garfunk
 *
 */
public class Arreter extends Action {

	private MegaPerso megaPerso;
	
	public Arreter(Personnage perso, int tempsTotal, MegaPerso megaPerso) {
		super(perso, tempsTotal);
		this.megaPerso = megaPerso;
	}

	/* (non-Javadoc)
	 * @see org.insa.megaupload.actions.Action#finished()
	 */
	@Override
	public void finished() {
		System.out.println("Je suis arrivé !!");
		if( megaPerso.getDeplacement() == null && megaPerso.getLieuActuel().equals(perso.getLieuActuel())){
			System.out.println("J'arrête...");
			((AgentFBI) perso).arreter(megaPerso);
			perso.seDeplacer(Context.getCarte().getMapLieux().get(AgentFBI.SPAWN_LIEU));
		} else {
			System.out.println("J'arrête pas ?!");
		}
	}

}
