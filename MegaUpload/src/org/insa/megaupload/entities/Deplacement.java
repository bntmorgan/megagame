package org.insa.megaupload.entities;

import java.util.Queue;

public class Deplacement {
	private int avancementEtape;
	private Lieu source;
	private Lieu cible;
	private Queue<Trajet> trajets;
	
	public Deplacement(Lieu source, Lieu cible, Queue<Trajet> trajets) {
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
		this.trajets.poll();
		this.avancementEtape = 0;
	}
	
	public Trajet getEtape() {
		return this.trajets.peek();
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
	
}
