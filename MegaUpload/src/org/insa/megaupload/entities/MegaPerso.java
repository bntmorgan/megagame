package org.insa.megaupload.entities;

import org.insa.megaupload.actions.OuvertureServeur;
import org.insa.megaupload.example.Action;
import org.insa.megaupload.rules.ServeurRules;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

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
	private static Image moveImg;
	private static Image openServerImg;
	
	private String nom;
	private int nbServeursOuverts;
	private Action hoveredAction;
	private String avatarFilename;
	
	public MegaPerso(String nom, Lieu lieuInitial, String imgBig, String imgPawn) throws SlickException {
		super(lieuInitial, new Image(imgBig), new Image(imgPawn));

		this.avatarFilename = imgBig;
		this.nom = nom;
		this.nbServeursOuverts = 0;
		this.num = nbPersos;
		nbPersos++;
	}
	
	public static void init() throws SlickException {
		openServerImg = new Image("resources/img/Backup-IBM-Server-icon.png");
		moveImg = new Image("resources/img/plane.png");
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
				System.out.println("ouverture serveur");
				// XXX: constante
				setAction(new OuvertureServeur(this, 10000));
			} else {
				// TODO: affichage : pas asez d'argent ! veuillez vendre plus de comptes premium !
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
		    	  filename("resources/img/Backup-IBM-Server-icon.png");
		    	  visibleToMouse(true);
		    	  interactOnClick("megaPersoActionSelected(" + getNom() + ", OUVRIR_SERVEUR)");
		      }});
		      image(new ImageBuilder() {{
		    	  x(Integer.toString(getAvatarX() + getAvatarWidth()));
		    	  y(Integer.toString(getAvatarY() + 50));
		    	  width(Integer.toString(moveImg.getWidth() * 50/moveImg.getHeight()));
		    	  height("50");
		    	  filename("resources/img/plane.png");
		    	  visibleToMouse(true);
		    	  interactOnClick("megaPersoActionSelected(" + getNom() + ", SE_DEPLACER)");
		      }});
		}};
	
		Screen mainScreen = nifty.getScreen("mainScreen");
		Element panel = mainScreen.findElementByName("menuPanel");
		panel.add(i.build(nifty, mainScreen, panel));
		panel.add(p.build(nifty, mainScreen, panel));
	}

}