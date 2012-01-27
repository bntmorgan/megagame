/**
 * 
 */
package org.insa.megaupload.actions;

import org.insa.megaupload.entities.Personnage;

/**
 * @author garfunk
 *
 */
public class Poursuivre extends Action {

	public Poursuivre(Personnage perso, int tempsTotal) {
		super(perso, tempsTotal);
	}

	/* (non-Javadoc)
	 * @see org.insa.megaupload.actions.Action#finished()
	 */
	@Override
	public void finished() {
		perso.kill();
	}

}
