/**
 * 
 */
package org.insa.megaupload.example;

import org.insa.megaupload.entities.Carte;
import org.insa.megaupload.entities.Deplacement;
import org.insa.megaupload.entities.Lieu;
import org.insa.megaupload.entities.MegaPerso;
import org.insa.megaupload.entities.Personnage;
import org.insa.megaupload.entities.Serveur;
import org.insa.megaupload.rules.ServeurRules;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author fougeane
 *
 */
public class Main extends BasicGame {
	private boolean mousePressed;
	private int mouseX;
	private int mouseY;
	
	public Main () {
		super("MegaUpload");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Main());
			app.setDisplayMode(1280, 667, false);
			app.setMaximumLogicUpdateInterval(10);
			app.setMinimumLogicUpdateInterval(10);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		Carte c = new Carte();
		Context.setCarte(c);
		Lieu.setImages(new Image("resources/img/City-20px.png"), new Image("resources/img/City-30px.png"));
		Context.addPersonnage(new MegaPerso("Kim DotCom", (Lieu) c.getLieux().toArray()[27], new Image("resources/img/kim.png")));
		Context.addPersonnage(new MegaPerso("Finn Batato", (Lieu) c.getLieux().toArray()[3], new Image("resources/img/finn.png")));
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		//System.out.println("Update: " + delta);
		for (Personnage p : Context.getPersonnages()) {
			Deplacement d = p.getDeplacement();
			if (d != null) {
				Lieu cible = d.getEtape().getCible(p.getLieuActuel());
				System.out.println("Je vais de " + p.getLieuActuel().getNom() + " à " + cible.getNom());
				System.out.println(d.getAvancementEtape() + "%");
				System.out.println("->");
				int distanceTotale = d.getEtape().getDistance();
				int distanceParcourue = (d.getAvancementEtape() * distanceTotale) / 100;
				d.setAvancementEtape(100*(distanceParcourue + delta)/distanceTotale);
				System.out.println(d.getAvancementEtape() + "%");
			}
		}

	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		Context.getCarte().draw(g);
		g.drawString("Welcome to MegaUpload!", 10, 50);
		if (Context.getSelectedPerso() != null) {
			g.drawString("Selected perso: " + Context.getSelectedPerso().getNom(), 10, 600);
		}
		if (Context.getSelectedLieu() != null) {
			g.drawString("Selected lieu: " + Context.getSelectedLieu().getNom(), 10, 620);
		}
		g.setColor(Color.red);
		//if (this.mousePressed) {
			int r = 6;
			g.fillOval(this.mouseX - r/2, this.mouseY - r/2, r, r);
			g.drawString("Mouse pressed: " + this.mouseX + ", " + this.mouseY, 10, 75);
		//}
		for (Personnage p : Context.getPersonnages()) {
			p.draw();
		}
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		Lieu hoveredLieu = null;
		MegaPerso hoveredPerso = null;
		
		for (Lieu l : Context.getCarte().getLieux()) {
			if (newx >= l.getX() - l.getWidth()/2 && newx <= l.getX() + l.getWidth()/2 &&
					newy >= l.getY() - l.getHeight()/2 && newy <= l.getY() + l.getHeight()/2) {
				hoveredLieu = l;
				break;
			}
		}
		
		for (Personnage p : Context.getPersonnages()) {
			if (p instanceof MegaPerso) {
				int num = ((MegaPerso) p).getNum();
				if (newx <= 100 && newy >= (num + 1)*100 && newy <= (num + 2)*100) {
					hoveredPerso = ((MegaPerso) p);
					break;
				}
			}
		}
		
		Context.setHoveredLieu(hoveredLieu);
		Context.setHoveredPerso(hoveredPerso);
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		if(clickCount == 1){
			if (Context.getHoveredLieu() != null) {
				Context.setSelectedLieu(Context.getHoveredLieu());
			}
			if (Context.getHoveredPerso() != null) {
				Context.setSelectedPerso(Context.getHoveredPerso());
			}
			
			MegaPerso selectedPerso = Context.getSelectedPerso();
			Lieu selectedLieu = Context.getSelectedLieu();
			if (selectedPerso != null && selectedLieu != null) {
				selectedPerso.seDeplacer(selectedLieu);
				Context.setSelectedLieu(null);
			}
		}
		//double click ou plus
		else{
			if(ServeurRules.peutOuvrirServeur()){
				Lieu l = Context.getSelectedLieu();
				l.addServeur(new Serveur());	
			}
			else{
				//affichage 
			}
		}
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		this.mousePressed = true;
		this.mouseX = x;
		this.mouseY = y;
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		this.mousePressed = false;
	}

}
