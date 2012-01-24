package org.insa.megaupload.example;

import org.insa.megaupload.entities.Carte;
import org.insa.megaupload.entities.Lieu;
import org.insa.megaupload.entities.MegaPerso;

public class Context {
	private static Carte carte;
	private static MegaPerso selectedPerso;
	private static Lieu selectedLieu;
	
	/**
	 * @return the selectedPerso
	 */
	public static MegaPerso getSelectedPerso() {
		return selectedPerso;
	}
	
	/**
	 * @param selectedPerso the selectedPerso to set
	 */
	public static void setSelectedPerso(MegaPerso selectedPerso) {
		Context.selectedPerso = selectedPerso;
	}
	
	/**
	 * @return the selectedLieu
	 */
	public static Lieu getSelectedLieu() {
		return selectedLieu;
	}
	
	/**
	 * @param selectedLieu the selectedLieu to set
	 */
	public static void setSelectedLieu(Lieu selectedLieu) {
		Context.selectedLieu = selectedLieu;
	}

	/**
	 * @return the carte
	 */
	public static Carte getCarte() {
		return carte;
	}

	/**
	 * @param carte the carte to set
	 */
	public static void setCarte(Carte carte) {
		Context.carte = carte;
	}
	
}
