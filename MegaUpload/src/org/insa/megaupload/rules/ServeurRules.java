package org.insa.megaupload.rules;

import org.insa.megaupload.entities.MegaPerso;
import org.insa.megaupload.example.Context;

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
	
	public static boolean peutOuvrirServeur(MegaPerso p) {
		// Capacité financiere
		return (Context.getCptThunes() > coutOuverture);
	}
	
	public static int getRegleTelechargementsBase(){
		return telechargementsBase;
	}

}
