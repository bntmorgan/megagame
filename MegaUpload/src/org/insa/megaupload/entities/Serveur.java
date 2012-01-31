package org.insa.megaupload.entities;

import org.insa.megaupload.example.Context;
import org.insa.megaupload.rules.ServeurRules;

public class Serveur {

	public Serveur() {
	}
	
	//déclenché tous les X base de temps, augmente le compteur de thune avec les gains du serveur
	public void getGainArgent() {
		Context.incCptThunes(ServeurRules.getRegleGainBase());
	}

}
