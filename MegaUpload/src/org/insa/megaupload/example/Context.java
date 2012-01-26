package org.insa.megaupload.example;

import java.util.ArrayList;
import java.util.Collection;

import org.insa.megaupload.entities.*;

public class Context {
	private static Collection<Personnage> personnages = new ArrayList<Personnage>();
	private static Carte carte;
	private static Action hoveredAction;
	private static Action selectedAction;
	private static MegaPerso hoveredPerso;
	private static MegaPerso selectedPerso;
	private static Lieu hoveredLieu;
	private static Lieu selectedLieu;
	private static int cptThunes = 10000;
	private static int cptTelechargements;
	private static int cptServeursOuverts;
	
	/**
	 * Ajoute de l'argent au compteur de thune 
	 * @param cptThunes
	 */
	public static void incCptThunes(int cptThunes) {
		Context.cptThunes += cptThunes;
	}
	
	/**
	 * Ajoute de l'argent au compteur de thune
	 * @param cptThunes
	 */
	public static void incCptTelechargements(int cptThunes) {
		Context.cptTelechargements += cptTelechargements;
	}
	
	/**
	 * Retire de l'argent au compteur de thune 
	 * @param cptThunes
	 */
	public static void decCptThunes(int cptThunes){
		Context.cptThunes -= cptThunes;
		if(Context.cptThunes < 0){
			Context.cptThunes = 0 ;
		}
	}
	
	/**
	 * @return the cptThunes
	 */
	public static int getCptThunes() {
		return cptThunes;
	}
	
	/**
	 * @return the selectedAction
	 */
	public static Action getSelectedAction() {
		return selectedAction;
	}

	/**
	 * @param selectedAction the selectedAction to set
	 */
	public static void setSelectedAction(Action selectedAction) {
		Context.selectedAction = selectedAction;
	}

	/**
	 * @return the cptTelechargements
	 */
	public static int getCptTelechargements() {
		return cptTelechargements;
	}

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
	
	/**
	 * @return the hoveredAction
	 */
	public static Action getHoveredAction() {
		return hoveredAction;
	}

	/**
	 * @param hoveredAction the hoveredAction to set
	 */
	public static void setHoveredAction(Action hoveredAction) {
		Context.hoveredAction = hoveredAction;
	}

	/**
	 * @return the hoveredPerso
	 */
	public static MegaPerso getHoveredPerso() {
		return hoveredPerso;
	}

	/**
	 * @param hoveredPerso the hoveredPerso to set
	 */
	public static void setHoveredPerso(MegaPerso hoveredPerso) {
		Context.hoveredPerso = hoveredPerso;
	}

	/**
	 * @return the hoveredLieu
	 */
	public static Lieu getHoveredLieu() {
		return hoveredLieu;
	}

	/**
	 * @param hoveredLieu the hoveredLieu to set
	 */
	public static void setHoveredLieu(Lieu hoveredLieu) {
		Context.hoveredLieu = hoveredLieu;
	}
	
	public static void addPersonnage(Personnage p) {
		Context.personnages.add(p);
	}
	
	public static Collection<Personnage> getPersonnages() {
		return Context.personnages;
	}

	public static int getCptServeursOuverts() {
		return Context.cptServeursOuverts;
	}

	public static void incCptServeurs() {
		Context.cptServeursOuverts++;
		
	}

	public static void decCptServeurs(int nb) {
		Context.cptServeursOuverts -= nb;
	}
	
	public static void mouseMoved(int oldx, int oldy, int newx, int newy) {
		Lieu hoveredLieu = null;

		// Recherche d'un lieu correspondant à la position de la souris
		for (Lieu l : Context.getCarte().getLieux()) {
			if (newx >= l.getX() - l.getWidth() / 2
					&& newx <= l.getX() + l.getWidth() / 2
					&& newy >= l.getY() - l.getHeight() / 2
					&& newy <= l.getY() + l.getHeight() / 2) {
				hoveredLieu = l;
				break;
			}
		}
		
		Context.setHoveredLieu(hoveredLieu);
	}
	
	public static void mouseClicked(int button, int x, int y, int clickCount) {
		if (Context.getHoveredLieu() != null) {
			Context.setSelectedLieu(Context.getHoveredLieu());
		
			if (clickCount == 1) {
				MegaPerso selectedPerso = Context.getSelectedPerso();
				Lieu selectedLieu = Context.getSelectedLieu();
				Action selectedAction = Context.getSelectedAction();
				
				if (selectedPerso != null && selectedLieu != null) {
					selectedPerso.seDeplacer(selectedLieu);
				} else if (selectedAction == Action.SE_DEPLACER && selectedLieu != null) {
					selectedPerso.seDeplacer(selectedLieu);
					Context.setSelectedAction(null);
				}		
			} else {
				if (Context.getSelectedPerso() != null) {
					Context.getSelectedPerso().ouvrirServeur();
				}
			}
		}
	}
	
	
}
