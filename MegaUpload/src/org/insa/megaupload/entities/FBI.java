package org.insa.megaupload.entities;

import java.util.List;

import org.insa.megaupload.example.Context;

public class FBI {
	private static FBI instance;
	private int nbAgents;
	
	private FBI() {
		nbAgents = 0;
		nouvelAgent();
	}
	
	private void nouvelAgent() {
		new AgentFBI();
		nbAgents++;
	}
	
	public void update(int delta) {
		if (nbAgents < Context.getNiveau()) {
			nouvelAgent();
		}
		
		for (AgentFBI a : Context.getAgentsFBI()) {
			if (a.getAction() == null) {
				int pieceStrategie = Context.rand(1);
				if (pieceStrategie == 0) {
					// Pile : on ferme les serveurs d'un lieu
					List<Lieu> lieuxAvecServeurs = Context.getLieuxAvecServeurs();
					if (lieuxAvecServeurs.size() > 0) {
						int deLieu = Context.rand(lieuxAvecServeurs.size() - 1);
						a.seDeplacer(lieuxAvecServeurs.get(deLieu));
					}
				} else {
					// Face : on poursuit un personnage
					List<MegaPerso> megaPersos = Context.getMegaPersos();
					if (megaPersos.size() > 0) {
						int dePerso = Context.rand(megaPersos.size() - 1);
						a.poursuivre(megaPersos.get(dePerso));
					}
				}
			}
		}
	}
	
	public static FBI getInstance() {
		if (instance == null) {
			instance = new FBI();
		}
		return instance;
	}
}
