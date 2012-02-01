package org.insa.megaupload.entities;

public class PareFeu {
	
	private static final long DUREE = 2000;
	
	private long tempsRestant;
	private boolean decrement;
	
	public PareFeu(){
		this.tempsRestant = DUREE;
		decrement = true;
	}
	
	public PareFeu(boolean decrement){
		this.tempsRestant = DUREE;
		this.decrement = decrement;
	}
	
	/**
	 * @return the tempsRestant
	 */
	public long getTempsRestant() {
		return tempsRestant;
	}

	public void decrementTime(int delta){
		if(decrement)
			tempsRestant -= delta;
	}
	
}
