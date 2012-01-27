package org.insa.megaupload.entities;

import org.insa.megaupload.actions.FermetureServeurs;
import org.insa.megaupload.example.Context;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.lessvoid.nifty.Nifty;

public class AgentFBI extends Personnage {
	private static int nbAgents = 0;
	private static Image imgBig;
	private static Image imgPawn;
	public static final String SPAWN_LIEU = "New York";
	
	protected final static double coefRand = 0.9;
	
	private MegaPerso poursuivi;
	private int num;
	
	public double getCoefRand() {
		return coefRand;
	}
	
	public static void init() throws SlickException {
		imgBig = new Image("resources/img/fbiguy.png");
		imgPawn = new Image("resources/img/fbi.png");
	}
	
	public AgentFBI() {
		super(Context.getCarte().getMapLieux().get(SPAWN_LIEU), imgBig, imgPawn);
		this.num = nbAgents;
		nbAgents++;
	}
	
	public void poursuivre(MegaPerso perso) {
		this.poursuivi = perso;
	}
	
	public MegaPerso getPoursuivi() {
		return this.poursuivi;
	}
	
	@Override
	public void update(int delta) {

		MegaPerso poursuivi = this.getPoursuivi();
		if (poursuivi != null) {
			if (this.getLieuActuel() == poursuivi.getLieuActuel()) {
				 this.arreter(poursuivi);
			} else if (this.getDeplacement() == null) {
				this.seDeplacer(poursuivi.getLieuActuel());
			} else if (this.getDeplacement().getCible() != poursuivi.getLieuActuel()) {
				this.seDeplacer(poursuivi.getLieuActuel());
			}
		}
		
		// S'il y a des serveurs sur la position actuelle, ils peuvent etre fermés
		if (!this.getLieuActuel().getServeurs().isEmpty() && !(this.action instanceof FermetureServeurs)) {
			double rand = Math.random();
			Lieu l = this.getLieuActuel();
			double risque = rand * l.getRisque();
			if (risque > 1) {
				setAction(new FermetureServeurs(this, this.getLieuActuel().getServeurs().size() * 10000));
			}
		}
		
		super.update(delta);
	}
	
	public void arreter(MegaPerso perso) {
		if (this.getLieuActuel() == perso.getLieuActuel()) {
			//System.out.println(perso.getNom() + " arrêté!");
			//this.poursuivi = null;
		}
	}
	
	public void fermerServeur() {
		this.getLieuActuel().delServeurs();
	}
	

	public int getAvatarX() {
		// XXX: constantes
		return 1280 - getAvatarWidth() - 10;
	}
	
	public int getAvatarY() {
		// XXX: constantes
		return 50 + (getAvatarHeight() + 10)*num;
	}
	
	public int getAvatarWidth() {
		return imgBig.getWidth() * getAvatarHeight() / imgBig.getHeight();
	}
	
	public int getAvatarHeight() {
		// XXX: constante
		return 75;
	}
	
	@Override
	public void draw(Graphics g, Nifty nifty) {
		super.draw(g, nifty);
		
		imgBig.draw(getAvatarX(), getAvatarY(), getAvatarWidth(), getAvatarHeight());
	}

}
