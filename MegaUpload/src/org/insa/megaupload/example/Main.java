package org.insa.megaupload.example;

import java.io.IOException;

import org.insa.megaupload.entities.AgentFBI;
import org.insa.megaupload.entities.Carte;
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
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;

/**
 * @author fougeane
 * 
 */
public class Main extends BasicGame {
	private boolean started;
	private Music music;
	private Image loadingImg;
	private int cptSoft;

	public Main() {
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
		loadingImg = new Image("resources/img/megaupload-logo.png");
		LoadingList.setDeferredLoading(true);
		
		MegaPerso.init();
		
		Carte c = new Carte();
		Context.setCarte(c);
		music = new Music("resources/sound/megasong.ogg");
		Lieu.setImages(new Image("resources/img/City-20px.png"), new Image("resources/img/City-30px.png"));

		MegaPerso kim = new MegaPerso("Kim DotCom", (Lieu) c.getLieux().toArray()[27], new Image("resources/img/avatar-yellow.png"), new Image("resources/img/yellow.png"));
		Context.addPersonnage(kim);
		
		MegaPerso finn = new MegaPerso("Finn Batato", (Lieu) c.getLieux().toArray()[3], new Image("resources/img/avatar-red.png"), new Image("resources/img/red.png"));
		Context.addPersonnage(finn);
		/*
		MegaPerso finn = new MegaPerso("Finn Batato", (Lieu) c.getLieux().toArray()[3], new Image("resources/img/redguy.png"), new Image("resources/img/red.png")));
		Context.addPersonnage(finn);
		
		MegaPerso finn = new MegaPerso("Finn Batato", (Lieu) c.getLieux().toArray()[3], new Image("resources/img/redguy.png"), new Image("resources/img/red.png")));
		Context.addPersonnage(finn);
		*/
		AgentFBI a1 = new AgentFBI((Lieu) c.getLieux().toArray()[2], new Image("resources/img/fbiguy.png"), new Image("resources/img/fbi.png"));
		Context.addPersonnage(a1);
		a1.poursuivre(kim);
		Context.setSelectedPerso(kim);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if (LoadingList.get().getRemainingResources() > 0) {
			DeferredResource nextResource = LoadingList.get().getNext();
			try {
				nextResource.load();
			} catch (IOException e) {
				throw new SlickException("Failed to load: " + nextResource.getDescription(), e);
			}
		} else {
			if (!started) {
				started = true;
				music.loop();
			}

			//toutes les secondes
			if(++this.cptSoft%100 == 0){
				//récupérer les gains des serveurs
				Context.incCptThunes(ServeurRules.getRegleGainBase()*Context.getCptServeursOuverts());
			}
			
			//System.out.println("Update: " + delta);
			for (Personnage p : Context.getPersonnages()) {
				p.update(delta);
			}
		}
	}
	public void render(GameContainer container, Graphics g)	throws SlickException {
		if (!started) {
			g.drawString("Loading...", 500, 100);
			loadingImg.draw((container.getWidth() - loadingImg.getWidth()) / 2,
					(container.getHeight() - loadingImg.getHeight()) / 2);
		} else {
			Context.getCarte().draw(g);
			g.drawString("Welcome to MegaUpload!", 10, 50);
			g.drawString("Argent \n" + Integer.toString(Context.getCptThunes()), 1200, 50);
			g.drawString("Serveurs\n" + Integer.toString(Context.getCptServeursOuverts()), 1200, 100);
			if (Context.getSelectedPerso() != null) {
				g.drawString("Selected perso: "	+ Context.getSelectedPerso().getNom(), 10, 600);
			}
			if (Context.getSelectedLieu() != null) {
				g.drawString("Selected lieu: " + Context.getSelectedLieu().getNom(), 10, 620);
			}
			g.setColor(Color.red);
			
			for (Personnage p : Context.getPersonnages()) {
				p.draw();
			}

			for (Lieu l : Context.getCarte().getLieux()) {
				for (Trajet t : l.getTrajets()) {
					g.drawLine(t.getDepart().getX(), t.getDepart().getY(), t.getArrivee().getX(), t.getArrivee().getY());
				}
			}
			

			
		}
	}
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		Action hoveredAction = null;
		Lieu hoveredLieu = null;
		MegaPerso hoveredPerso = null;

		// recherche d'un lieu correspondant à la position de la souris
		for (Lieu l : Context.getCarte().getLieux()) {
			if (newx >= l.getX() - l.getWidth() / 2
					&& newx <= l.getX() + l.getWidth() / 2
					&& newy >= l.getY() - l.getHeight() / 2
					&& newy <= l.getY() + l.getHeight() / 2) {
				hoveredLieu = l;
				break;
			}
		}

		//recherche d'un personnage correspondant à la position de la souris
		for (Personnage p : Context.getPersonnages()) {
			if (p instanceof MegaPerso) {
				int num = ((MegaPerso) p).getNum();
				if (newx <= 100 && newy >= (num + 1) * 100 && newy <= (num + 2) * 100) {
					hoveredPerso = ((MegaPerso) p);
					break;
				}
			}
		}
		
		//recherche de l'action correspondant à la position de la souris
		

		Context.setHoveredAction(hoveredAction);
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
			if (Context.getHoveredAction() != null) {
				Context.setSelectedAction(Context.getHoveredAction());
			}
			
			MegaPerso selectedPerso = Context.getSelectedPerso();
			Lieu selectedLieu = Context.getSelectedLieu();
			Action selectedAction = Context.getSelectedAction();
			if (selectedAction != Action.SE_DEPLACER && selectedPerso != null && selectedLieu != null) {
				selectedPerso.seDeplacer(selectedLieu);
				Context.setSelectedLieu(null);
			}
			else if (selectedAction != Action.OUVRIR_SERVEUR && selectedPerso != null && selectedLieu != null) {
				if(ServeurRules.peutOuvrirServeur()){
					Lieu selL = Context.getSelectedLieu();
					if (selL != null){
						selL.addServeur(new Serveur());					
					}
				}
				else {
					//affichage : pas asez d'argent ! veuillez vendre plus de comptes premium !
				}
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
			else {
				//affichage : pas asez d'argent ! veuillez vendre plus de comptes premium !
			}
		}
	}
}