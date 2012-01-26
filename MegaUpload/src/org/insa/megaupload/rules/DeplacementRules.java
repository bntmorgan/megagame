package org.insa.megaupload.rules;

import org.insa.megaupload.entities.AgentFBI;
import org.insa.megaupload.entities.Lieu;
import org.insa.megaupload.entities.MegaPerso;
import org.insa.megaupload.entities.Personnage;
import org.insa.megaupload.example.Context;

public class DeplacementRules {
	//cout nominal pour un déplacement de 1 pixel
	private static float coutDeplacement = 1f;

	public static float getCoutDeplacement() {
		return coutDeplacement;
	}
	
	public static boolean peutSeDeplacer(int distance){
		Personnage p = Context.getSelectedPerso();
		if (p != null) {
			if (p instanceof AgentFBI) {
				return true;
			}
			if (p instanceof MegaPerso) {
				if (Context.getCptThunes()>distance*coutDeplacement) {
					return true;
				}
			}
		}
		return false;
	}

}
