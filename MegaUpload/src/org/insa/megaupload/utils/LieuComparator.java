package org.insa.megaupload.utils;

import java.util.Comparator;
import java.util.Map;

import org.insa.megaupload.entities.Lieu;

public class LieuComparator implements Comparator<Lieu> {

		private Map<Lieu, Integer> couts;

		/**
		 * @param couts
		 * @param marque
		 */
		public LieuComparator(Map<Lieu, Integer> couts) {
			super();
			this.couts = couts;
		}

		@Override
		public int compare(Lieu lieu1, Lieu lieu2) {
			//si on est marqué on va a la fin
			if (couts.get(lieu1) < couts.get(lieu2))
				return -1;
			else if(couts.get(lieu1) > couts.get(lieu2))
				return 1;
			return 0;
		}
	}