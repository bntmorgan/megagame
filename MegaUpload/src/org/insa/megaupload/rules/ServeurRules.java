package org.insa.megaupload.rules;

import org.insa.megaupload.entities.Lieu;
import org.insa.megaupload.entities.Personnage;
import org.insa.megaupload.example.Context;;

public class ServeurRules {
	
	//TODO valeurs à priori, on affinera
	private static int coutOuverture = 1000;
	private static int gainBase = 50;
	private static int telechargementsBase = 1000;

	public static int getRegleCoutOuverture() {
		return coutOuverture;
	}
	
	public static int getRegleGainBase(){
		return gainBase;
	}
	
	public static boolean peutOuvrirServeur(){
		//capacité financiere
		if (Context.getCptThunes()>coutOuverture) {
			Personnage p = Context.getSelectedPerso();
			Lieu l = Context.getSelectedLieu();
			if (p != null && p.getLieuActuel() == l) {
				return true;				
			}
		}
		return false;
	}
	
	public static int getRegleTelechargementsBase(){
		return telechargementsBase;
	}

}
