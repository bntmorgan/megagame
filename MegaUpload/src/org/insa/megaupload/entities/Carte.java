package org.insa.megaupload.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Carte extends Image implements Graphe{
	private Collection<Lieu> lieux;
	
	public Carte() throws SlickException {
		super("resources/img/map.png");
		
		try {
			Map<String, Lieu> lieux = new HashMap<String, Lieu>();
			BufferedReader br;
			String filename;
			
			filename = "/resources/lieux.data";
			br = new BufferedReader(new InputStreamReader(Carte.class.getResourceAsStream(filename)));
			while (br.ready()) {
				String line = br.readLine();
				String data[] = line.split("\t");
				
				if (data.length == 6) {
					Lieu l = new Lieu(data[0], Integer.valueOf(data[1]), Integer.valueOf(data[2]),
							Integer.valueOf(data[3]), Integer.valueOf(data[4]), Integer.valueOf(data[5]));
					lieux.put(l.getNom(), l);
				}
			}
			br.close();
			
			filename = "/resources/trajets.data";
			br = new BufferedReader(new InputStreamReader(Carte.class.getResourceAsStream(filename)));
			while (br.ready()) {
				String line = br.readLine();
				String data[] = line.split("\t");
				
				if (data.length == 2) {
					Lieu depart = lieux.get(data[0]);
					Lieu arrivee = lieux.get(data[1]);
					
					if (depart != null && arrivee != null) {
						Trajet t = new Trajet(depart, arrivee);
						depart.addTrajet(t);
						arrivee.addTrajet(t);
					}
				}
			}
			br.close();
			
			this.lieux = lieux.values();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Collection<Lieu> getLieux() {
		return lieux;
	}
	
	public void draw(Graphics g) {
		super.draw();
		
		for (Lieu l : lieux) {
			l.draw(g);
		}
	}
}
