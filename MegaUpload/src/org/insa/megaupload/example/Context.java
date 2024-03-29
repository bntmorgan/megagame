package org.insa.megaupload.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.insa.megaupload.control.MainScreenController;
import org.insa.megaupload.entities.*;

import de.lessvoid.nifty.screen.Screen;

public class Context {
	private static Carte carte;
	private static int cptServeursOuverts;
	private static int cptTelechargements;
	private static int cptThunes = 10000;
	private static Action hoveredAction;
	private static Lieu hoveredLieu;
	private static MegaPerso hoveredPerso;
	private static MainScreenController mainScreenController;
	private static Screen mainScreen;
	private static int niveau = 1;
	private static List<Personnage> personnages = new ArrayList<Personnage>();
	private static Action selectedAction;
	private static Lieu selectedLieu;
	private static MegaPerso selectedPerso;
	private static int[] seuilsNiveaux = { 10, 25, 40 };
	private static List<Lieu> lieuxAvecServeurs = new ArrayList<Lieu>();

	public static void addPersonnage(Personnage p) {
		Context.personnages.add(p);
	}

	public static void decCptServeurs(int nb) {
		Context.cptServeursOuverts -= nb;
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
	 * @return the carte
	 */
	public static Carte getCarte() {
		return carte;
	}

	public static int getCptServeursOuverts() {
		return Context.cptServeursOuverts;
	}

	/**
	 * @return the cptTelechargements
	 */
	public static int getCptTelechargements() {
		return cptTelechargements;
	}

	/**
	 * @return the cptThunes
	 */
	public static int getCptThunes() {
		return cptThunes;
	}

	/**
	 * @return the hoveredAction
	 */
	public static Action getHoveredAction() {
		return hoveredAction;
	}

	/**
	 * @return the hoveredLieu
	 */
	public static Lieu getHoveredLieu() {
		return hoveredLieu;
	}

	/**
	 * @return the hoveredPerso
	 */
	public static MegaPerso getHoveredPerso() {
		return hoveredPerso;
	}

	/**
	 * @return the mainScreenController
	 */
	public static MainScreenController getMainScreenController() {
		return mainScreenController;
	}

	public static int getNiveau() {
		return niveau;
	}

	public static Collection<Personnage> getPersonnages() {
		return Context.personnages;
	}

	/**
	 * @return the selectedAction
	 */
	public static Action getSelectedAction() {
		return selectedAction;
	}

	/**
	 * @return the non selectedPerso
	 */
	public static ArrayList<MegaPerso> getNonSelectedPerso() {
		ArrayList<MegaPerso> ret = new ArrayList<MegaPerso>();
		for(Personnage p : personnages){
			if(p instanceof MegaPerso && p != Context.getSelectedPerso() && !p.isDead()){
				ret.add((MegaPerso)p);
			}
		}
		return ret;
	}
	
	
	/**
	 * @return the selectedLieu
	 */
	public static Lieu getSelectedLieu() {
		return selectedLieu;
	}

	/**
	 * @return the selectedPerso
	 */
	public static MegaPerso getSelectedPerso() {
		return (selectedPerso != null && selectedPerso.isDead()) ? null : selectedPerso;
	}

	public static void incCptServeurs() {
		Context.cptServeursOuverts++;

	}

	/**
	 * Ajoute de l'argent au compteur de thune
	 * @param cptThunes
	 */
	public static void incCptTelechargements(int cptThunes) {
		Context.cptTelechargements += cptTelechargements;
	}

	/**
	 * Ajoute de l'argent au compteur de thune 
	 * @param cptThunes
	 */
	public static void incCptThunes(int cptThunes) {
		Context.cptThunes += cptThunes;
	}

	public static void mouseClicked(int button, int x, int y, int clickCount) {
		if (Context.getHoveredLieu() != null) {
			Context.setSelectedLieu(Context.getHoveredLieu());

			if (clickCount == 1) {
				MegaPerso selectedPerso = Context.getSelectedPerso();
				Lieu selectedLieu = Context.getSelectedLieu();

				if (selectedPerso != null && selectedLieu != null) {
					selectedPerso.seDeplacer(selectedLieu);
				}		
			} else {
				if (Context.getSelectedPerso() != null) {
					Context.getSelectedPerso().ouvrirServeur();
				}
			}
		}
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

	/**
	 * @param carte the carte to set
	 */
	public static void setCarte(Carte carte) {
		Context.carte = carte;
	}

	/**
	 * @param hoveredAction the hoveredAction to set
	 */
	public static void setHoveredAction(Action hoveredAction) {
		Context.hoveredAction = hoveredAction;
	}

	/**
	 * @param hoveredLieu the hoveredLieu to set
	 */
	public static void setHoveredLieu(Lieu hoveredLieu) {
		Context.hoveredLieu = hoveredLieu;
	}

	/**
	 * @param hoveredPerso the hoveredPerso to set
	 */
	public static void setHoveredPerso(MegaPerso hoveredPerso) {
		Context.hoveredPerso = hoveredPerso;
	}

	/**
	 * @param mainScreenController the mainScreenController to set
	 */
	public static void setMainScreenController(
			MainScreenController mainScreenController) {
		Context.mainScreenController = mainScreenController;
	}

	/**
	 * @param selectedAction the selectedAction to set
	 */
	public static void setSelectedAction(Action selectedAction) {
		Context.selectedAction = selectedAction;
	}

	/**
	 * @param selectedLieu the selectedLieu to set
	 */
	public static void setSelectedLieu(Lieu selectedLieu) {
		Context.selectedLieu = selectedLieu;
	}

	/**
	 * @param selectedPerso the selectedPerso to set
	 */
	public static void setSelectedPerso(MegaPerso selectedPerso) {
		Context.selectedPerso = selectedPerso;
	}

	public static void update(int delta) {
		if (niveau <= seuilsNiveaux.length && cptServeursOuverts >= seuilsNiveaux[niveau - 1]) {
			niveau++;
			if (AgentFBI.getFBICoefRand() > 0) {
				AgentFBI.setFBICoefRand(AgentFBI.getFBICoefRand() - 0.25);
			}
		}
	}
	
	public static List<Lieu> getLieuxAvecServeurs() {
		return lieuxAvecServeurs;
	}
	
	public static void addLieuAvecServeur(Lieu l) {
		lieuxAvecServeurs.add(l);
	}
	
	public static void removeLieuAvecServeur(Lieu l) {
		lieuxAvecServeurs.remove(l);
	}

	public static List<MegaPerso> getMegaPersos() {
		List<MegaPerso> megaPersos = new ArrayList<MegaPerso>();
		for (Personnage p : getPersonnages()) {
			if (p instanceof MegaPerso && !p.isDead()) {
				megaPersos.add((MegaPerso) p);
			}
		}
		return megaPersos;
	}
	
	public static List<AgentFBI> getAgentsFBI() {
		List<AgentFBI> agentsFBI = new ArrayList<AgentFBI>();
		for (Personnage p : getPersonnages()) {
			if (p instanceof AgentFBI) {
				agentsFBI.add((AgentFBI) p);
			}
		}
		return agentsFBI;
	}
	
	public static int rand(int min, int max) {
		return new Random().nextInt(max + 1 - min) + min;
	}
	
	public static int rand(int max) {
		return rand(0, max);
	}
	
	public static Lieu getMegaPersoSpawnLieu() {
		Lieu randomLieu;
		do {
			randomLieu = getCarte().getLieux().get(rand(getCarte().getLieux().size() -1));
		} while (randomLieu.getNom() == AgentFBI.SPAWN_LIEU);
		
		return randomLieu;
	}

	public static void removePersonnage(Personnage personnage) {
		personnages.remove(personnage);
	}

	/**
	 * @return the mainScreen
	 */
	public static Screen getMainScreen() {
		return mainScreen;
	}

	/**
	 * @param mainScreen the mainScreen to set
	 */
	public static void setMainScreen(Screen mainScreen) {
		Context.mainScreen = mainScreen;
	}

}
