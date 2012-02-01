package org.insa.megaupload.entities;

public class PareFeu {
	
	private static final long DUREE = 2000;
	
	private long tempsRestant;
	
	public PareFeu(){
		this.tempsRestant = DUREE;
	}

	/**
	 * @return the tempsRestant
	 */
	public long getTempsRestant() {
		return tempsRestant;
	}

	/**
	 * @param tempsRestant the tempsRestant to set
	 */
	public void setTempsRestant(long tempsRestant) {
		this.tempsRestant = tempsRestant;
	}
	
}
