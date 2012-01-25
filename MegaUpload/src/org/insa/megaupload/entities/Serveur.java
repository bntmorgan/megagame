package org.insa.megaupload.entities;

import org.insa.megaupload.example.Context;
import org.insa.megaupload.rules.ServeurRules;

public class Serveur {
	private int gainArgent;
	private int gainCulturel;
	private Lieu lieuHebergement;
	
	
	public Serveur(int gainArgent, int gainCulturel, Lieu lieuHebergement) {
		this.gainArgent = 0;
		this.gainCulturel = gainCulturel;
		this.lieuHebergement = lieuHebergement;
		
		//décompte le cout d'ouverture du serveur du compteur de thune
		Context.decCptThunes(ServeurRules.getRegleCoutOuverture());
	}
	
	//déclenché tous les X base de temps, augmente le compteur de thune avec les gains du serveur
	public void gainArgent() {
		Context.incCptThunes(ServeurRules.getRegleGainBase());
		this.gainArgent += ServeurRules.getRegleGainBase();
	}
	
	public int getGainArgent() {
		return gainArgent;
	}

	public int getGainCulturel() {
		return gainCulturel;
	}
	
	public Lieu getLieuHebergement() {
		return lieuHebergement;
	}
	
	
	
	
}
