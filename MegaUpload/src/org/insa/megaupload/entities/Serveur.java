package org.insa.megaupload.entities;

import org.insa.megaupload.example.Context;
import org.insa.megaupload.rules.ServeurRules;

public class Serveur {
	private int gainCulturel;
	private Lieu lieuHebergement;
	
	
	public Serveur(int gainArgent, int gainCulturel, Lieu lieuHebergement) {
		this.gainCulturel = gainCulturel;
		this.lieuHebergement = lieuHebergement;
		
		//décompte le cout d'ouverture du serveur du compteur de thune
		Context.decCptThunes(ServeurRules.getRegleCoutOuverture());
	}
	
	//déclenché tous les X base de temps, augmente le compteur de thune avec les gains du serveur
	public void getGainArgent() {
		Context.incCptThunes(ServeurRules.getRegleGainBase());
	}
	
	public int getGainCulturel() {
		return gainCulturel;
	}
	
	public Lieu getLieuHebergement() {
		return lieuHebergement;
	}
	
	
	
	
}
