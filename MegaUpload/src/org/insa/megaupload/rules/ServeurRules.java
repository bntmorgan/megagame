package org.insa.megaupload.rules;

public class ServeurRules {
	
	//TODO valeurs à priori, on affinera
	private static int coutOuverture = 1000;
	private static int gainBase = 100;

	public static int getRegleCoutOuverture() {
		return coutOuverture;
	}
	
	public static int getRegleGainBase(){
		return gainBase;
	}

}
