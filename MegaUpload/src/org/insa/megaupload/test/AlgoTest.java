/**
 * 
 */
package org.insa.megaupload.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.insa.megaupload.entities.Graphe;
import org.insa.megaupload.entities.Lieu;
import org.insa.megaupload.entities.Trajet;
import org.insa.megaupload.utils.Algo;
import org.junit.Before;
import org.junit.Test;

/**
 * @author garfunk
 * 
 */
public class AlgoTest {

	private class Carte implements Graphe{

		private Collection<Lieu> lieux;

		private Lieu a;
		private Lieu b;
		private Lieu c;
		private Lieu d;
		private Lieu e;

		/**
		 * 
		 */
		public Carte() {

			super();
			lieux = new ArrayList<Lieu>(5);

			// sommets

			a = new Lieu("A", 0, 0, 0, 0, 0);
			b = new Lieu("B", 0, 0, 0, 0, 0);
			c = new Lieu("C", 0, 0, 0, 0, 0);
			d = new Lieu("D", 0, 0, 0, 0, 0);
			e = new Lieu("E", 0, 0, 0, 0, 0);

			// ajout des sommets

			lieux.add(a);
			lieux.add(b);
			lieux.add(c);
			lieux.add(d);
			lieux.add(e);

			// Arêtes

			Trajet t = new Trajet(a, c);
			a.addTrajet(t);
			c.addTrajet(t);

			t = new Trajet(c, b);
			b.addTrajet(t);
			c.addTrajet(t);

			t = new Trajet(b, e);
			b.addTrajet(t);
			e.addTrajet(t);

			t = new Trajet(b, d);
			b.addTrajet(t);
			d.addTrajet(t);

			t = new Trajet(a, e);
			a.addTrajet(t);
			e.addTrajet(t);

			t = new Trajet(a, d);
			a.addTrajet(t);
			d.addTrajet(t);
		}

		/**
		 * @return the lieux
		 */
		public Collection<Lieu> getLieux() {
			return lieux;
		}

		/**
		 * @return the a
		 */
		public Lieu getA() {
			return a;
		}

		/**
		 * @return the e
		 */
		public Lieu getE() {
			return e;
		}

	}

	private Carte carte;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		carte = new Carte();
	}

	/**
	 * Test method for
	 * {@link org.insa.megaupload.utils.Algo#PCC(org.insa.megaupload.entities.Carte, org.insa.megaupload.entities.Lieu, org.insa.megaupload.entities.Lieu)}
	 * .
	 */
	@Test
	public void testPCC() {
		Stack<Trajet> t = Algo.PCC(this.carte, carte.getA(), carte.getE());
		assertTrue("", t.peek().getDepart().getNom().equals("A") || t.peek().getArrivee().getNom().equals("A") );
		assertTrue("", t.peek().getDepart().getNom().equals("C") || t.peek().getArrivee().getNom().equals("C") );
		t.pop();
		assertTrue("", t.peek().getDepart().getNom().equals("C") || t.peek().getArrivee().getNom().equals("C") );
		assertTrue("", t.peek().getDepart().getNom().equals("B") || t.peek().getArrivee().getNom().equals("B") );
		t.pop();
		assertTrue("", t.peek().getDepart().getNom().equals("B") || t.peek().getArrivee().getNom().equals("B") );
		assertTrue("", t.peek().getDepart().getNom().equals("E") || t.peek().getArrivee().getNom().equals("E") );
		t.pop();
	}
}
