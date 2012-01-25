package org.insa.megaupload.entities;

import java.util.Stack;

public class Deplacement {
	private Personnage perso;
	private Lieu source;
	private Lieu cible;
	private Stack<Trajet> trajets;
	private int avancementEtape;
	
	public Deplacement(Personnage perso, Lieu source, Lieu cible, Stack<Trajet> trajets) {
		this.perso = perso;
		this.source = source;
		this.cible = cible;
		this.trajets = trajets;
	}
	
	/**
	 * @return the avancement
	 */
	public int getAvancementEtape() {
		return avancementEtape;
	}
	
	/**
	 * @param avancement the avancementEtape to set
	 */
	public void setAvancementEtape(int avancement) {
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
			perso.setDeplacement(null);
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
	
}
