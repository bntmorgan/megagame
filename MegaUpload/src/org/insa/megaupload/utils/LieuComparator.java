package org.insa.megaupload.utils;

import java.util.Comparator;
import java.util.Map;

import org.insa.megaupload.entities.Lieu;

public class LieuComparator implements Comparator<Lieu> {

		private Map<Lieu, Integer> couts;
		private double seuilRand;

		/**
		 * @param couts
		 * @param marque
		 */
		public LieuComparator(Map<Lieu, Integer> couts) {
			super();
			this.couts = couts;
			this.seuilRand = 1;
		}
		
		public LieuComparator(Map<Lieu, Integer> couts, double seuilRand) {
			super();
			this.couts = couts;
			this.seuilRand = seuilRand;
		}

		public int compare(Lieu lieu1, Lieu lieu2) {
			double rand = Math.random();
			if (couts.get(lieu1) < couts.get(lieu2)) {
				//return -1;
				return (rand > seuilRand) ? 1 : -1;
			} else if(couts.get(lieu1) > couts.get(lieu2)) {
				//return 1;
				return (rand > seuilRand) ? -1 : 1;
			}
			return 0;
		}
	}