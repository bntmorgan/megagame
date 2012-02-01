package org.insa.megaupload.entities;

import java.awt.Color;

import org.insa.megaupload.actions.OuvertureServeur;
import org.insa.megaupload.example.Action;
import org.insa.megaupload.example.Context;
import org.insa.megaupload.example.CoolLinearParticleEmitter;
import org.insa.megaupload.rules.ServeurRules;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.effects.Falloff.HoverFalloffConstraint;
import de.lessvoid.nifty.effects.Falloff.HoverFalloffType;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;

public class MegaPerso extends Personnage {
	private static int nbPersos = 0;
	private static Image runawayImg;
	private static Image openServerImg;
	private static Image viewfinder;
	
	private String nom;
	private int nbServeursOuverts;
	private Action hoveredAction;
	private String avatarFilename;

	private int frais;
	private long timestampDenierAchat;
	
	private CoolLinearParticleEmitter particleEmitter;
    private static ParticleSystem particleSystem = null; 
	
	public MegaPerso(String nom, Lieu lieuInitial, String imgBig, String imgPawn) throws SlickException {
		super(lieuInitial, new Image(imgBig), new Image(imgPawn));

		this.avatarFilename = imgBig;
		this.nom = nom;
		this.nbServeursOuverts = 0;
		this.num = nbPersos;
		nbPersos++;
		
		this.frais = 0;
		this.timestampDenierAchat = 0;
		
		particleEmitter = new CoolLinearParticleEmitter(this.getX(), this.getY(), 6f, Color.GREEN );
		getParticleSystem().addEmitter(particleEmitter);
		particleEmitter.setEnabled(false);
	}
	
	public MegaPerso(String nom, String imgBig, String imgPawn) throws SlickException {
		this(nom, Context.getMegaPersoSpawnLieu(), imgBig, imgPawn);
	}
	
	public static void init() throws SlickException {
		openServerImg = new Image("resources/img/add-server.png");
		runawayImg = new Image("resources/img/run_away.png");
		viewfinder = new Image("resources/img/viewfinder.png");
	}
	
	public String getNom() {
		return this.nom;
	}

	public int getNbServeursOuverts() {
		return nbServeursOuverts;
	}

	/**
	 * @return the hoveredAction
	 */
	public Action getHoveredAction() {
		return hoveredAction;
	}

	/**
	 * @param hoveredAction the hoveredAction to set
	 */
	public void setHoveredAction(Action hoveredAction) {
		this.hoveredAction = hoveredAction;
	}

	public void ouvrirServeur() {
		if (this.action == null) {
			if (ServeurRules.peutOuvrirServeur(this)) {
				// XXX: constante
				setAction(new OuvertureServeur(this, 10000));
			} else {
				Context.getMainScreenController().addInfoText("Pas assez d'argent pour ouvrir un serveur ! Veuillez vendre plus de comptes premium !");
			}
		}
	}
	
	@Override
	public void draw(Graphics g) {
		if(!isDead()){
			super.draw(g);

			if (timestampDenierAchat > (System.currentTimeMillis() - 1*1000)) {
				String str = String.valueOf(-this.frais) + "$";
				int x = this.getX() - ((imgPawn.getWidth()/2 + g.getFont().getWidth(str))/2);
				int y = this.getY() + imgPawn.getHeight()/2;
				g.drawString(str, x, y);
			}
			
			//ajout de la cible si on est poursuivit
			boolean isFollowed = false;			
			for(AgentFBI agent : Context.getAgentsFBI()){
				if(agent.getPoursuivi() != null && agent.getPoursuivi().equals(this)){
					isFollowed = true;
					break;
				}
			}			
			if(isFollowed){
				int x = this.getX() - viewfinder.getWidth()/2;
				int y = this.getY() - viewfinder.getHeight()/2;
				g.drawImage(viewfinder, x, y);
			}
			
		}
	}
	
	public int getAvatarX() {
		// XXX: constante
		return 10;
	}
	
	public int getAvatarY() {
		// XXX: constantes
		return 10 + (getAvatarHeight() + 10) * num;
	}
	
	public int getAvatarWidth() {
		return imgBig.getWidth() * getAvatarHeight() / imgBig.getHeight();
	}
	
	public int getAvatarHeight() {
		// XXX: constante
		return 100;
	}
	
	public void startNifty(Nifty nifty) {
		ImageBuilder i = new ImageBuilder() {{
			  id(getNom());
	    	  x(Integer.toString(getAvatarX()));
	    	  y(Integer.toString(getAvatarY()));
	    	  width(Integer.toString(getAvatarWidth()));
	    	  height(Integer.toString(getAvatarHeight()));
	    	  filename(avatarFilename);
	    	  visibleToMouse(true);
	    	  onHoverEffect(new HoverEffectBuilder("imageSize") {{
	    		  name("imageSize");
	    		  effectParameter("maxSize", "20%");
	    		  hoverFalloffType(HoverFalloffType.linear);
	    		  hoverFalloffConstraint(HoverFalloffConstraint.vertical);
	    		  hoverWidth("100%");
	    		  hoverHeight("100%");
	    	  }});
	    	  onStartHoverEffect(new HoverEffectBuilder("show") {{
	    		  effectParameter("targetElement", "submenu_" + getNom());
	    	  }});
	    	  onEndHoverEffect(new HoverEffectBuilder("hide") {{
	    		  effectParameter("targetElement", "submenu_" + getNom());
	    		  hoverFalloffType(HoverFalloffType.linear);
	    		  hoverFalloffConstraint(HoverFalloffConstraint.vertical);
	    		  hoverWidth("200%");
	    	  }});
	    	  interactOnClick("megaPersoSelected(" + getNom() + ")");
		}};
		
		PanelBuilder p = new PanelBuilder() {{
		      id("submenu_" + getNom());
		      visible(false);
		      childLayoutAbsolute();
		      image(new ImageBuilder() {{
		    	  x(Integer.toString(getAvatarX() + getAvatarWidth()));
		    	  y(Integer.toString(getAvatarY()));
		    	  width(Integer.toString(openServerImg.getWidth() * 50/openServerImg.getHeight()));
		    	  height("50");
		    	  filename("resources/img/add-server.png");
		    	  visibleToMouse(true);
		    	  interactOnClick("megaPersoActionSelected(" + getNom() + ", OUVRIR_SERVEUR)");
		      }});
		      image(new ImageBuilder() {{
		    	  x(Integer.toString(getAvatarX() + getAvatarWidth()));
		    	  y(Integer.toString(getAvatarY() + 50));
		    	  width(Integer.toString(runawayImg.getWidth() * 50/runawayImg.getHeight()));
		    	  height("50");
		    	  filename("resources/img/run_away.png");
		    	  visibleToMouse(true);
		    	  interactOnClick("megaPersoActionSelected(" + getNom() + ", FUIR)");
		      }});
		}};
	
		Screen mainScreen = nifty.getScreen("mainScreen");
		Element panel = mainScreen.findElementByName("menuPanel");
		panel.add(i.build(nifty, mainScreen, panel));
		panel.add(p.build(nifty, mainScreen, panel));
	}
	
	@Override
	public void update(int delta) {
		if(!isDead()){
			super.update(delta);
	        if (action != null) {
	        	action.update(delta);
	        }
	        particleEmitter.setX(this.getX());
	        particleEmitter.setY(this.getY());
		}
	}
	
	public static ParticleSystem getParticleSystem() {
		if (particleSystem == null) {
			Image image = null;
			try {
				image = new Image("resources/img/dollard.png");
			} catch (SlickException e) {
				e.printStackTrace();
			}
			particleSystem = new ParticleSystem(image);
			particleSystem.setPosition(0, 0);
		}

		return particleSystem;
	}
	
	@Override
	public void kill() {
		setDead(true);
		desactivateParticleEmitter();
		System.out.println(Context.getMainScreen().findElementByName(getNom()));
		Context.getMainScreen().findElementByName(getNom()).hide();
		Context.getMainScreen().findElementByName("submenu_" + getNom()).hide();
	}
	
	public void activateParticleEmitter(){
		particleEmitter.setEnabled(true);
	}
	
	public void desactivateParticleEmitter(){
		particleEmitter.setEnabled(false);
	}
	
	public ParticleEmitter getParticleEmitter(){
		return (ParticleEmitter)particleEmitter;
	}

	public long getTimestampDenierAchat() {
		return timestampDenierAchat;
	}

	public void setTimestampDenierAchat(long timestampDenierAchat) {
		this.timestampDenierAchat = timestampDenierAchat;
	}

	public int getFrais() {
		return frais;
	}

	public void setFrais(int frais) {
		this.frais = frais;
	}	
}