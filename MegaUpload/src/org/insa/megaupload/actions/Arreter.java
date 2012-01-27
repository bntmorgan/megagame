/**
 * 
 */
package org.insa.megaupload.actions;

import org.insa.megaupload.entities.MegaPerso;
import org.insa.megaupload.entities.Personnage;

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
		if( megaPerso.getDeplacement() == null && megaPerso.getLieuActuel().equals(perso.getLieuActuel())){
			megaPerso.kill();
		}
	}

}
