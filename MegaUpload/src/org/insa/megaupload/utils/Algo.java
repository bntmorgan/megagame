package org.insa.megaupload.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.insa.megaupload.entities.Lieu;
import org.insa.megaupload.entities.Trajet;;


public class Algo {
	
	private class Carte{
		public ArrayList<Lieu> getLieux(){return new ArrayList<Lieu>();}
	}
	
	public static List<Trajet> PCC(Carte c, Lieu depart, Lieu arrivee){

		Map<Lieu,Integer> couts = new HashMap<Lieu,Integer>(c.getLieux().size());
		Map<Lieu,Boolean> marque = new HashMap<Lieu,Boolean>(c.getLieux().size());
		Map<Lieu,Trajet> predecesseurs = new HashMap<Lieu,Trajet>(c.getLieux().size());
		
		//initialisation de l'algorithme
		for (Lieu l : c.getLieux())
			couts.put(l, -1);
		
		//cout a zero pour le depart
		couts.put(depart, 0);
		
		//boolean a false par defaut en java et null pour les prédécesseurs
		
		while(true){
			
			//On cherche le premier non marqué de cout non infini 
			Lieu courant = null;
			for (Lieu l : c.getLieux()){
				if(!couts.get(l).equals(-1) && marque.get(l) != true){
					courant = l;
					break;
				}
			}
			
			//condition de terminaison
			if(courant == null)
				break;
			
			//mise a jour des voisins
			for(Trajet t : courant.getTrajets()){
				Lieu voisin = null;
				if(t.getLieuDepart().equals(courant))
					voisin = t.getLieuArrivee();
				else
					voisin = t.getLieuDepart();
					
				//TODO ajouter pour cout infini  = -1
				if(couts.get(voisin) > couts.get(courant) + t.getDistance()){
					predecesseurs.put(voisin, t);
					couts.put(voisin, couts.get(courant) + t.getDistance());
				}				
			}
			
			//on se marque
			marque.put(courant, true);
			
			//si on est arrivé
			if(courant == arrivee)
				break;
		}
		
		//on constitue le trajet a partir de la lise de prédecesseurs en partant du point d'arrivée
		Lieu courant = arrivee;
		
		Stack<Trajet> trajet = new Stack<Trajet>();
		while(courant != depart){
			trajet.push(predecesseurs.get(courant));
		}
		
		return trajet;
	}
}
