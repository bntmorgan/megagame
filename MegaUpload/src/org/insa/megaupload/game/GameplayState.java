/**
 * 
 */
package org.insa.megaupload.game;

import java.util.ArrayList;

import org.insa.megaupload.control.MainScreenController;
import org.insa.megaupload.entities.AgentFBI;
import org.insa.megaupload.entities.Carte;
import org.insa.megaupload.entities.FBI;
import org.insa.megaupload.entities.Lieu;
import org.insa.megaupload.entities.MegaPerso;
import org.insa.megaupload.entities.Personnage;
import org.insa.megaupload.entities.Trajet;
import org.insa.megaupload.example.Context;
import org.insa.megaupload.rules.ServeurRules;
import org.insa.megaupload.utils.FontUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.slick2d.NiftyOverlayBasicGameState;

/**
 * @author garfunk
 *
 */
public class GameplayState extends NiftyOverlayBasicGameState {
	
	private boolean started;
	private Music music;
	private Image loadingImg;
	private int cptSoft;
	private Nifty nifty;
	private boolean returnToMenu;

	public GameplayState() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see de.lessvoid.nifty.slick2d.NiftyOverlayBasicGameState#enterState(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	protected void enterState(GameContainer arg0, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.lessvoid.nifty.slick2d.NiftyOverlayBasicGameState#initGameAndGUI(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	protected void initGameAndGUI(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		// Initialisation ressources
		// Chargée directement pour pouvoir être affichée pendant le chargement des autres
		loadingImg = new Image("resources/img/megaupload-logo.png");
		
		// Chargement différé pour afficher l'écran de chargement
		//LoadingList.setDeferredLoading(true);
		
		// Initialisation ressources
		music = new Music("resources/sound/megasong.ogg");
		MegaPerso.init();
		AgentFBI.init();
		Lieu.init();
		
		// Initialisation carte
		Carte c = new Carte();
		Context.setCarte(c);

		// Initialisation personnages
		MegaPerso kim = new MegaPerso("Kim DotCom", "resources/img/avatar-yellow.png", "resources/img/yellow.png");
		MegaPerso mathias = new MegaPerso("Mathias Ortmann", "resources/img/avatar-green.png", "resources/img/green.png");
		MegaPerso bram = new MegaPerso("Bram van der Kolk", "resources/img/avatar-purple.png", "resources/img/purple.png");
		MegaPerso finn = new MegaPerso("Finn Batato", "resources/img/avatar-red.png", "resources/img/red.png");
		
		Context.addPersonnage(kim);
		Context.addPersonnage(mathias);
		Context.addPersonnage(bram);
		Context.addPersonnage(finn);

		// Initialisation FBI
		FBI.getInstance();
		
		// Paramètres d'affichage
		container.setShowFPS(false);

		// On initialise le cul
		initNifty(container, game);
	}

	/* (non-Javadoc)
	 * @see de.lessvoid.nifty.slick2d.NiftyOverlayBasicGameState#leaveState(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	protected void leaveState(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	/* (non-Javadoc)
	 * @see de.lessvoid.nifty.slick2d.NiftyOverlayBasicGameState#prepareNifty(de.lessvoid.nifty.Nifty, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	protected void prepareNifty(Nifty nifty, StateBasedGame game) {
		this.nifty = nifty;
		nifty.fromXml("src/resources/layout/console.xml", "mainScreen");
		Context.setMainScreenController((MainScreenController) nifty.getCurrentScreen().getScreenController());
		Context.getMainScreenController().setGameplayState(this);
	}

	/* (non-Javadoc)
	 * @see de.lessvoid.nifty.slick2d.NiftyOverlayBasicGameState#renderGame(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	protected void renderGame(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		if (!started) {
			// XXX: laid (taille, alignement, couleur...)
			FontUtils.drawCenter(g.getFont(), "Loading...", 0, 150, 1280);
			loadingImg.draw((container.getWidth() - loadingImg.getWidth()) / 2,
					(container.getHeight() - loadingImg.getHeight()) / 2);
		} else {
			Context.getCarte().draw(g);
			
			g.setColor(Color.red);
			
			// XXX: alignement
			FontUtils.drawRight(g.getFont(), "Cash: " + Integer.toString(Context.getCptThunes()) + "$", 0, 10, 1280 - 15);
			FontUtils.drawRight(g.getFont(), Integer.toString(Context.getCptServeursOuverts()) + " servers up", 0, 30, 1280 - 15);

			// Dessin des personnages à leur nouvelle position
			MegaPerso.getParticleSystem().render();
			for (Personnage p : Context.getPersonnages()) {
				p.draw(g, nifty);
			}
			
			// XXX: debug
			if (Context.getSelectedPerso() != null) {
				Context.getSelectedPerso().activateParticleSystem();
				for(MegaPerso mp : Context.getNonSelectedPerso()){
					mp.desactivateParticleSystem();
				}
				g.drawString("Selected perso: "	+ Context.getSelectedPerso().getNom(), 10, 600);
			}
			if (Context.getSelectedLieu() != null) {
				g.drawString("Selected lieu: " + Context.getSelectedLieu().getNom(), 10, 620);
			}

			// XXX: debug: affichage des arêtes
			for (Lieu l : Context.getCarte().getLieux()) {
				for (Trajet t : l.getTrajets()) {
					g.drawLine(t.getDepart().getX(), t.getDepart().getY(), t.getArrivee().getX(), t.getArrivee().getY());
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see de.lessvoid.nifty.slick2d.NiftyOverlayBasicGameState#updateGame(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	protected void updateGame(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if (LoadingList.get().getRemainingResources() > 0) {
			// Chargement de la prochaine ressource différée
			DeferredResource nextResource = LoadingList.get().getNext();
			try {
				nextResource.load();
			} catch (Exception e) {
				throw new SlickException("Failed to load: " + nextResource.getDescription(), e);
			}
		} else {
			if (!started) {
				// Chargement différé terminé, démarrage du jeu
				LoadingList.setDeferredLoading(false);
				music.loop();
				started = true;
			}

			// Update du contexte
			Context.update(delta);
			
			// Toutes les secondes
			if (++this.cptSoft % 100 == 0) {
				// Récupérer les gains des serveurs
				Context.incCptThunes(ServeurRules.getRegleGainBase() * Context.getCptServeursOuverts());
			}
			
			// Update des actions et déplacements des personnages
			for (Personnage p : Context.getPersonnages()) {
				p.update(delta);
			}
			
			// Update du FBI
			FBI.getInstance().update(delta);
		}
		
		if (returnToMenu) {
			game.enterState(0);
			returnToMenu = false;
		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		Context.mouseMoved(oldx, oldy, newx, newy);
	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		Context.mouseClicked(button, x, y, clickCount);
	}

	/**
	 * @param returnToMenu the returnToMenu to set
	 */
	public void returnToMenu() {
		this.music.stop();
		this.returnToMenu = true;
	}
	
}
