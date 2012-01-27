package org.insa.megaupload.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Carte extends Image implements Graphe{
	private Map<String, Lieu> mapLieux; 
	private List<Lieu> lieux;
	
	public Carte() throws SlickException {
		super("resources/img/map.png");
		
		try {
			mapLieux = new HashMap<String, Lieu>();
			BufferedReader br;
			String filename;
			
			//parse les caracteristiques des lieux depuis le fichier de lieux
			filename = "/resources/lieux.data";
			br = new BufferedReader(new InputStreamReader(Carte.class.getResourceAsStream(filename)));
			while (br.ready()) {
				String line = br.readLine();
				String data[] = line.split("\t");
				
				if (data.length == 6) {
					Lieu l = new Lieu(data[0], Integer.valueOf(data[1]), Integer.valueOf(data[2]),
							Integer.valueOf(data[3]), Integer.valueOf(data[4]), Integer.valueOf(data[5]));
					mapLieux.put(l.getNom(), l);
				}
			}
			br.close();
			
			//parse les arcs depuis le fichier
			filename = "/resources/trajets.data";
			br = new BufferedReader(new InputStreamReader(Carte.class.getResourceAsStream(filename)));
			while (br.ready()) {
				String line = br.readLine();
				String data[] = line.split("\t");
				
				if (data.length == 2) {
					Lieu depart = mapLieux.get(data[0]);
					Lieu arrivee = mapLieux.get(data[1]);
					
					if (depart != null && arrivee != null) {
						Trajet t = new Trajet(depart, arrivee);
						depart.addTrajet(t);
						arrivee.addTrajet(t);
					}
				}
			}
			br.close();
			
			this.lieux = new ArrayList<Lieu>(mapLieux.values());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Lieu> getLieux() {
		return lieux;
	}
	
	public Map<String, Lieu> getMapLieux() {
		return mapLieux;
	}
	
	public void draw(Graphics g) {
		super.draw();
		
		g.setAntiAlias(true);
		g.setLineWidth(2);
		g.setColor(new Color(44, 86, 106));
		
		// XXX: debug: affichage des arêtes
		for (Lieu l : lieux) {
			for (Trajet t : l.getTrajets()) {
				g.drawLine(t.getDepart().getX(), t.getDepart().getY(), t.getArrivee().getX(), t.getArrivee().getY());
			}
		}
		
		g.setLineWidth(1);
		
		for (Lieu l : lieux) {
			// Afficage des lieux
			l.draw(g);
		}
	}
}
