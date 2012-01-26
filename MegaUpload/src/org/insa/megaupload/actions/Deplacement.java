package org.insa.megaupload.actions;

import java.util.Stack;

import org.insa.megaupload.entities.Lieu;
import org.insa.megaupload.entities.MegaPerso;
import org.insa.megaupload.entities.Personnage;
import org.insa.megaupload.entities.Trajet;
import org.insa.megaupload.example.Context;
import org.insa.megaupload.rules.DeplacementRules;
import org.newdawn.slick.Graphics;

public class Deplacement extends Action {
	private Lieu source;
	private Lieu cible;
	private Stack<Trajet> trajets;
	private double avancementEtape;
	
	public Deplacement(Personnage perso, Lieu source, Lieu cible, Stack<Trajet> trajets) {
		super(perso, getTemps(trajets));
		this.source = source;
		this.cible = cible;
		this.trajets = trajets;
	}
	
	public static int getDistance(Stack<Trajet> trajets) {
		int dist = 0;
		for (Trajet t : trajets) {
			dist += t.getDistance();
		}
		return dist;
	}
	
	private static int getTemps(Stack<Trajet> trajets) {
		int temps = 0;
		for (Trajet t : trajets) {
			temps += t.getTemps();
		}
		return temps;
	}
	
	public int getDistance() {
		return getDistance(trajets);
	}
	
	public int getTemps() {
		return getTemps(trajets);
	}
	
	/**
	 * @return the avancement
	 */
	public double getAvancementEtape() {
		return avancementEtape;
	}
	
	/**
	 * @param avancement the avancementEtape to set
	 */
	public void setAvancementEtape(double avancement) {
		if (avancement >= 100) {
			this.etapeSuivante();
		} else {
			this.avancementEtape = avancement;
		}
	}
	
	public void etapeSuivante() {
		Lieu lieuAtteint = this.trajets.peek().getCible(this.perso.getLieuActuel());
		this.trajets.pop();
		this.avancementEtape = 0;
		if (this.trajets.isEmpty()) {
			perso.setLieuActuel(this.getCible());
			perso.setAction(null);
		} else {
			perso.setLieuActuel(lieuAtteint);
		}
	}
	
	public Trajet getEtape() {
		if (this.trajets.isEmpty()) {
			return null;
		} else {
			return this.trajets.peek();
		}
	}
	
	public void setEtapes(Stack<Trajet> etapes) {
		this.trajets = etapes;
	}
	
	/**
	 * @return the source
	 */
	public Lieu getSource() {
		return source;
	}

	/**
	 * @return the cible
	 */
	public Lieu getCible() {
		return cible;
	}	
	
	public void setCible(Lieu cible) {
		this.cible = cible;
	}

	@Override
	public void update(int delta) {
		if (perso instanceof MegaPerso && getAvancementEtape() == 0 ) {
			int distanceTotale = getEtape().getDistance();
			Context.decCptThunes((int) (distanceTotale*DeplacementRules.getCoutDeplacement()));
		}
		
		int tempsTotal = getEtape().getTemps();
		double tempsParcouru = (getAvancementEtape() * tempsTotal) / 100.;
		setAvancementEtape(100 * (tempsParcouru + perso.getVitesse())/tempsTotal); 
	}
	
	@Override
	public void render(Graphics g) {
		// Do nothing :)
	}
	
	@Override
	public void finished() {
		// Do nothing :)
	}
	
}
