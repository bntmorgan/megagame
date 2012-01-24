package org.insa.megaupload.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import org.insa.megaupload.entities.Graphe;
import org.insa.megaupload.entities.Lieu;
import org.insa.megaupload.entities.Trajet;

public class Algo {

	public static Stack<Trajet> PCC(Graphe g, Lieu depart, Lieu arrivee) {

		Map<Lieu, Integer> couts = new HashMap<Lieu, Integer>(g.getLieux().size());
		Map<Lieu, Boolean> marque = new HashMap<Lieu, Boolean>(g.getLieux().size());
		Map<Lieu, Trajet> predecesseurs = new HashMap<Lieu, Trajet>(g.getLieux().size());

		// initialisation de l'algorithme
		for (Lieu l : g.getLieux()){
			couts.put(l, Integer.MAX_VALUE);
			marque.put(l, new Boolean(false));
		}

		// cout a zero pour le depart
		couts.put(depart, 0);

		Queue<Lieu> pq = new PriorityQueue<Lieu>(g.getLieux().size(), new LieuComparator(couts));
		pq.add(depart);

		// boolean a false par defaut en java et null pour les prédécesseurs
		while (!pq.isEmpty()) {

			// On cherche le premier non marqué de cout non infini
			Lieu courant = pq.poll();

			if (marque.get(courant).booleanValue())
				continue;

			// si on est arrivé
			if (courant == arrivee)
				break;
			
			// mise a jour des voisins
			for (Trajet t : courant.getTrajets()) {
				Lieu voisin = t.getCible(courant);

				if(! marque.get(voisin) ){
					if (couts.get(voisin) > couts.get(courant) + t.getDistance()) {
						predecesseurs.put(voisin, t);
						couts.put(voisin, couts.get(courant) + t.getDistance());
						//on ajoute un voisin seulement s'il est pas marqué
						pq.add(voisin);
					}
				}
			}

			// on se marque
			marque.put(courant, true);
		}

		// on constitue le trajet a partir de la lise de prédecesseurs en
		// partant du point d'arrivée
		Lieu courant = arrivee;

		Stack<Trajet> trajet = new Stack<Trajet>();
		while (courant != depart) {
			trajet.push(predecesseurs.get(courant));
			courant = predecesseurs.get(courant).getCible(courant);
		}

		return trajet;
	}
}
