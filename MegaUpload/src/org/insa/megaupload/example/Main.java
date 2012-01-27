package org.insa.megaupload.example;

import org.insa.megaupload.control.MainScreenController;
import org.insa.megaupload.entities.*;
import org.insa.megaupload.rules.ServeurRules;
import org.insa.megaupload.utils.FontUtils;
import org.newdawn.slick.*;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;

import de.lessvoid.nifty.Nifty;

/**
 * @author fougeanoob
 * 
 */
public class Main extends CoolNiftyOverlayBasicGame {
	private boolean started;
	private Music music;
	private Image loadingImg;
	private int cptSoft;
	private Nifty nifty;

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
	protected void initGameAndGUI(GameContainer container) throws SlickException {		
		// Initialisation ressources
		// Chargée directement pour pouvoir être affichée pendant le chargement des autres
		loadingImg = new Image("resources/img/megaupload-logo.png");
		
		// Chargement différé pour afficher l'écran de chargement
		LoadingList.setDeferredLoading(true);
		
		// Initialisation ressources
		music = new Music("resources/sound/megasong.ogg");
		MegaPerso.init();
		AgentFBI.init();
		Lieu.init();
		
		// Initialisation carte
		Carte c = new Carte();
		Context.setCarte(c);

		// Initialisation personnages
		MegaPerso kim = new MegaPerso("Kim DotCom", (Lieu) c.getLieux().toArray()[27], "resources/img/avatar-yellow.png", "resources/img/yellow.png");
		MegaPerso mathias = new MegaPerso("Mathias Ortmann", (Lieu) c.getLieux().toArray()[10], "resources/img/avatar-green.png", "resources/img/green.png");
		MegaPerso bram = new MegaPerso("Bram van der Kolk", (Lieu) c.getLieux().toArray()[42], "resources/img/avatar-purple.png", "resources/img/purple.png");
		MegaPerso finn = new MegaPerso("Finn Batato", (Lieu) c.getLieux().toArray()[3], "resources/img/avatar-red.png", "resources/img/red.png");
		
		Context.addPersonnage(kim);
		Context.addPersonnage(mathias);
		Context.addPersonnage(bram);
		Context.addPersonnage(finn);

		// Initialisation agents FBI
		AgentFBI a1 = new AgentFBI((Lieu) c.getLieux().toArray()[1]);
		AgentFBI a2 = new AgentFBI((Lieu) c.getLieux().toArray()[2]);
		AgentFBI a3 = new AgentFBI((Lieu) c.getLieux().toArray()[3]);
		AgentFBI a4 = new AgentFBI((Lieu) c.getLieux().toArray()[4]);
		
		Context.addPersonnage(a1);
		Context.addPersonnage(a2);
		Context.addPersonnage(a3);
		Context.addPersonnage(a4);
		
		a1.poursuivre(kim);
		
		Context.setSelectedPerso(kim);
		
		// Paramètres d'affichage
		container.setShowFPS(false);
	}

	@Override
	protected void prepareNifty(Nifty nifty) {
		this.nifty = nifty;
		nifty.fromXml("src/resources/layout/console.xml", "mainScreen");
		Context.setMainScreenController((MainScreenController) nifty.getCurrentScreen().getScreenController());
		nifty.setDebugOptionPanelColors(true);
	}
	
	@Override
	protected void updateGame(GameContainer container, int delta)
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
				initNifty(container);
				music.loop();
				started = true;
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
		}
	}

	@Override
	protected void renderGame(GameContainer container, Graphics g)
			throws SlickException {
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
			Personnage.getParticleSystem().render();
			for (Personnage p : Context.getPersonnages()) {
				p.draw(g, nifty);
			}
			
			// XXX: debug
			if (Context.getSelectedPerso() != null) {
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
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		Context.mouseMoved(oldx, oldy, newx, newy);
	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		Context.mouseClicked(button, x, y, clickCount);
	}
}