/**
 * 
 */
package org.insa.megaupload.example;

import org.insa.megaupload.entities.AgentFBI;
import org.insa.megaupload.entities.Carte;
import org.insa.megaupload.entities.Deplacement;
import org.insa.megaupload.entities.Lieu;
import org.insa.megaupload.entities.MegaPerso;
import org.insa.megaupload.entities.Personnage;
import org.insa.megaupload.entities.Trajet;
import org.insa.megaupload.entities.Serveur;
import org.insa.megaupload.rules.ServeurRules;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

/**
 * @author fougeane
 *
 */
public class Main extends BasicGame {
	private boolean mousePressed;
	private int mouseX;
	private int mouseY;
	
	private int cptSoft;
	
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
		MegaPerso kim = new MegaPerso("Kim DotCom", (Lieu) c.getLieux().toArray()[27], new Image("resources/img/kim.png"));
		Context.addPersonnage(kim);
		Context.addPersonnage(new MegaPerso("Finn Batato", (Lieu) c.getLieux().toArray()[3], new Image("resources/img/finn.png")));
		AgentFBI a = new AgentFBI((Lieu) c.getLieux().toArray()[2], new Image("resources/img/point-orange.png"));
		Context.addPersonnage(a);
		a.poursuivre(kim);
		Context.setSelectedPerso(kim);
		
		
		Music music = new Music("resources/sound/megasong.ogg");
		music.loop();
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
		//toutes les secondes
		if(++this.cptSoft%100 == 0){
			//récupérer les gains des serveurs
			Context.incCptThunes(ServeurRules.getRegleGainBase()*Context.getCptServeursOuverts());
		}
		
		//System.out.println("Update: " + delta);
		for (Personnage p : Context.getPersonnages()) {
			Deplacement d;
			
			if (p instanceof AgentFBI) {
				MegaPerso poursuivi = ((AgentFBI) p).getPoursuivi();
				if (poursuivi != null) {
					if (p.getLieuActuel() == poursuivi.getLieuActuel()) {
						((AgentFBI) p).arreter(poursuivi);
					} else if (p.getDeplacement() == null) {
						p.seDeplacer(poursuivi.getLieuActuel());
					} else if (p.getDeplacement().getCible() != poursuivi.getLieuActuel()) {
						p.seDeplacer(poursuivi.getLieuActuel());
					}
				}
			}
			
			d = p.getDeplacement();
			if (d != null) {
				Lieu cible = d.getEtape().getCible(p.getLieuActuel());
				/*System.out.println("Je vais de " + p.getLieuActuel().getNom() + " à " + cible.getNom());
				System.out.println(d.getAvancementEtape() + "%");
				System.out.println("->");*/
				int distanceTotale = d.getEtape().getDistance();
				double distanceParcourue = (double)(d.getAvancementEtape() * distanceTotale) / 100.;
				d.setAvancementEtape((int) (100.*(double)(distanceParcourue + 5)/(double)distanceTotale));
				//System.out.println(d.getAvancementEtape() + "%");
			}
		}
		

	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		Context.getCarte().draw(g);
		g.drawString("Welcome to MegaUpload!", 10, 50);
		g.drawString("Argent \n" + Integer.toString(Context.getCptThunes()), 1200, 50);
		g.drawString("Serveurs\n" + Integer.toString(Context.getCptServeursOuverts()), 1200, 100);
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
		
		for (Lieu l : Context.getCarte().getLieux()) {
			for (Trajet t : l.getTrajets()) {
				g.drawLine(t.getDepart().getX(), t.getDepart().getY(), t.getArrivee().getX(), t.getArrivee().getY());
			}
		}
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		Lieu hoveredLieu = null;
		MegaPerso hoveredPerso = null;
		
		//recherche d'un lieu correspondant à la position de la souris
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
			if (Context.getHoveredLieu() != null) {
				Context.setSelectedLieu(Context.getHoveredLieu());
			
			if(ServeurRules.peutOuvrirServeur()){
					Lieu selL = Context.getSelectedLieu();
					if (selL != null){
						selL.addServeur(new Serveur());					
					}
				}
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
