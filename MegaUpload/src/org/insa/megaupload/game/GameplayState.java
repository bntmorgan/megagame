/**
 * 
 */
package org.insa.megaupload.game;

import org.insa.megaupload.actions.OuvertureServeur;
import org.insa.megaupload.control.MainScreenController;
import org.insa.megaupload.entities.AgentFBI;
import org.insa.megaupload.entities.Carte;
import org.insa.megaupload.entities.FBI;
import org.insa.megaupload.entities.Lieu;
import org.insa.megaupload.entities.MegaPerso;
import org.insa.megaupload.entities.Personnage;
import org.insa.megaupload.example.Context;
import org.insa.megaupload.rules.ServeurRules;
import org.insa.megaupload.utils.FontUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
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
	
	private enum MegaUploadGameState {
		LOADING, RUNNING, WON, LOST;
	}
	
	private Music music;
	private Image loadingImg;
	private Image loseImg;
	private Image winImg;
	private int cptSoft;
	private Nifty nifty;
	private boolean returnToMenu;
	private MegaUploadGameState state;
	private boolean popupOn;
	private boolean imageDrawn;

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
		loseImg = new Image("resources/img/fbi-closed.jpg");
		winImg = new Image("resources/img/megaupload-premiumacces.jpg");
		
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
		new MegaPerso("Kim DotCom", "resources/img/avatar-yellow.png", "resources/img/yellow.png");
		new MegaPerso("Mathias Ortmann", "resources/img/avatar-green.png", "resources/img/green.png");
		new MegaPerso("Bram van der Kolk", "resources/img/avatar-purple.png", "resources/img/purple.png");
		new MegaPerso("Finn Batato", "resources/img/avatar-red.png", "resources/img/red.png");

		// Initialisation FBI
		FBI.getInstance();
		
		// Paramètres d'affichage
		container.setShowFPS(false);

		// On initialise le cul
		initNifty(container, game);
		
		this.state = MegaUploadGameState.LOADING;
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
		Context.setMainScreen(nifty.getCurrentScreen());
		Context.setMainScreenController((MainScreenController) Context.getMainScreen().getScreenController());
		Context.getMainScreenController().setGameplayState(this);
	}

	/* (non-Javadoc)
	 * @see de.lessvoid.nifty.slick2d.NiftyOverlayBasicGameState#renderGame(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	protected void renderGame(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		switch (state) {
			case LOADING:
				// XXX: laid (taille, alignement, couleur...)
				FontUtils.drawCenter(g.getFont(), "Loading...", 0, 150, 1280);
				loadingImg.draw((container.getWidth() - loadingImg.getWidth()) / 2,
						(container.getHeight() - loadingImg.getHeight()) / 2);
				break;
			case RUNNING:
				g.setBackground(new Color(64, 122, 156));
				Context.getCarte().draw(g);
				
				g.setColor(Color.red);
				g.setAntiAlias(true);
				
				// XXX: alignement
				FontUtils.drawRight(g.getFont(), "Cash: " + Integer.toString(Context.getCptThunes()) + "$", 0, 10, 1280 - 15);
				FontUtils.drawRight(g.getFont(), Integer.toString(Context.getCptServeursOuverts()) + " servers up", 0, 30, 1280 - 15);
				
				// Dessin des personnages à leur nouvelle position
				MegaPerso.getParticleSystem().render();
				AgentFBI.getParticleSystem().render();
				for (Personnage p : Context.getPersonnages()) {
					p.draw(g, nifty);
				}
				
				// XXX: debug
				if (Context.getSelectedPerso() != null) {
					Context.getSelectedPerso().activateParticleSystem();
					g.drawString("Selected perso: "	+ Context.getSelectedPerso().getNom(), 10, 600);
				}

				for (MegaPerso mp : Context.getNonSelectedPerso()) {
					if (mp.getAction() instanceof OuvertureServeur) {
						mp.activateParticleSystem();
					} else {
						mp.desactivateParticleSystem();
					}
				}

				if (Context.getSelectedLieu() != null) {
					g.drawString("Selected lieu: " + Context.getSelectedLieu().getNom(), 10, 620);
				}
	

				break;
			case WON:
				
				g.drawImage( winImg, (container.getWidth() - winImg.getWidth() ) /2,
						(container.getHeight()-winImg.getHeight())/2 );

				if(!popupOn && imageDrawn){
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					popupOn = true;
					nifty.createPopupWithId("popupWin", "popupWin");
					nifty.showPopup(nifty.getCurrentScreen(), "popupWin", null);
				}
				
				imageDrawn = true;
				
				break;
				
			case LOST:
				
				g.drawImage( loseImg, (container.getWidth() - loseImg.getWidth() ) /2,
								(container.getHeight()-loseImg.getHeight())/2 );

				if(!popupOn && imageDrawn){
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					popupOn = true;
					nifty.createPopupWithId("popupWin", "popupWin");
					nifty.showPopup(nifty.getCurrentScreen(), "popupWin", null);
				}
				
				imageDrawn = true;
				
				break;
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
			switch (state) {
				case LOADING:
					// Chargement différé terminé, démarrage du jeu
					LoadingList.setDeferredLoading(false);
					music.loop();
					state = MegaUploadGameState.RUNNING;
					break;
				case RUNNING:
					// Update du contexte
					Context.update(delta);
					
					if (Context.getMegaPersos().size() == 0) {
						this.state = MegaUploadGameState.LOST;
					}
					
					if (Context.getCptThunes() == 0 && Context.getCptServeursOuverts() == 0) {
						this.state = MegaUploadGameState.LOST;
					}
					
					if (Context.getCptServeursOuverts() >= 50) {
						this.state = MegaUploadGameState.WON;
					}
					
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
					break;
				case WON:
					break;
				case LOST:
					break;
			}
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

	@Override
	public void keyPressed(int key, char c) {
			switch (key) {
				case Input.KEY_1:
					if (Context.getMegaPersos().size() >= 1)
						Context.setSelectedPerso(Context.getMegaPersos().get(0));
					break;
				case Input.KEY_2:
					if (Context.getMegaPersos().size() >= 2)
						Context.setSelectedPerso(Context.getMegaPersos().get(1));
					break;
				case Input.KEY_3:
					if (Context.getMegaPersos().size() >= 3)
						Context.setSelectedPerso(Context.getMegaPersos().get(2));
					break;
				case Input.KEY_4:
					if (Context.getMegaPersos().size() >= 4)
						Context.setSelectedPerso(Context.getMegaPersos().get(3));
					break;
				case Input.KEY_A:
					if (Context.getSelectedPerso() != null)
						Context.getSelectedPerso().ouvrirServeur();
					break;
				case Input.KEY_R:
					if (Context.getSelectedPerso() != null)
						Context.getSelectedPerso().seDeplacer(Context.getCarte().getLieux().get(Context.rand(Context.getCarte().getLieux().size() - 1)));
					break;
				// Impossible
				default:
					break;
			}

	}
	
	/**
	 * @param returnToMenu the returnToMenu to set
	 */
	public void returnToMenu() {
		this.music.stop();
		this.returnToMenu = true;
	}

	/**
	 * @return the popupOn
	 */
	public boolean isPopupOn() {
		return popupOn;
	}

	/**
	 * @param popupOn the popupOn to set
	 */
	public void setPopupOn(boolean popupOn) {
		this.popupOn = popupOn;
	}
	
	public void closePopup(){
		nifty.closePopup("popupWin");
	}
	
	
	
}
