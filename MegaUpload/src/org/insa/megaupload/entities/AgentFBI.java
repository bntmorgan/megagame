package org.insa.megaupload.entities;

import java.awt.Color;

import org.insa.megaupload.actions.Arreter;
import org.insa.megaupload.actions.FermetureServeurs;
import org.insa.megaupload.example.Context;
import org.insa.megaupload.example.CoolFireEmitter;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;

import de.lessvoid.nifty.Nifty;

public class AgentFBI extends Personnage {
	private static int nbAgents = 0;
	private static Image imgBig;
	private static Image imgPawn;
	public static final String SPAWN_LIEU = "New York";
	
	protected static double coefRand = 1;
	
	private MegaPerso poursuivi;
	private int num;
	
	private CoolFireEmitter particleEmitter;
    private static ParticleSystem particleSystem = null; 
	
	public double getCoefRand() {
		return coefRand;
	}
	
	public static double getFBICoefRand() {
		return coefRand;
	}
	
	public static void setFBICoefRand(double coef) {
		AgentFBI.coefRand = coef;
	}
	
	public static void init() throws SlickException {
		imgBig = new Image("resources/img/fbiguy.png");
		imgPawn = new Image("resources/img/fbi.png");
	}
	
	public AgentFBI() {
		super(Context.getCarte().getMapLieux().get(SPAWN_LIEU), imgBig, imgPawn);
		this.num = nbAgents;
		nbAgents++;
		
		particleEmitter = new CoolFireEmitter(this.getX(), this.getY(), 6f, Color.RED );
		getParticleSystem().addEmitter(particleEmitter);
		particleEmitter.setEnabled(false);
	}
	
	public void poursuivre(MegaPerso perso) {
		this.poursuivi = perso;
	}
	
	public MegaPerso getPoursuivi() {
		return this.poursuivi;
	}
	
	@Override
	public void update(int delta) {

		// Gestion des poursuites
		MegaPerso poursuivi = this.getPoursuivi();
		if (poursuivi != null) {
			if (this.getLieuActuel().equals(poursuivi.getLieuActuel()) && poursuivi.getDeplacement() == null) {				
				if (!(this.getAction() instanceof Arreter)) {
					this.setAction(new Arreter(this, 1000, poursuivi));
					this.activateParticleSystem();
				}
			} else if (this.getDeplacement() == null) {
				this.seDeplacer(poursuivi.getLieuActuel());
			} else if (this.getDeplacement().getCible() != poursuivi.getLieuActuel()) {
				this.seDeplacer(poursuivi.getLieuActuel());
			}
		} else if (!this.getLieuActuel().getServeurs().isEmpty()) {
			// S'il y a des serveurs sur la position actuelle, ils peuvent etre fermés
			if (!(this.action instanceof FermetureServeurs)) {
				double rand = Math.random();
				Lieu l = this.getLieuActuel();
				double risque = rand * l.getRisque();
				if (risque > 1) {
					setAction(new FermetureServeurs(this, this.getLieuActuel().getServeurs().size() * 10000));
					this.activateParticleSystem();
				}
			}
		} else {
			this.desactivateParticleSystem();
		}
		
        particleSystem.update(delta);
        particleEmitter.setX(this.getX());
        particleEmitter.setY(this.getY());
		
		super.update(delta);
	}
	
	public static ParticleSystem getParticleSystem() {
		if (particleSystem == null) {
			Image image = null;
			try {
				image = new Image("resources/img/star.png");
			} catch (SlickException e) {
				e.printStackTrace();
			}
			particleSystem = new ParticleSystem(image);
			particleSystem.setPosition(0, 0);
		}

		return particleSystem;
	}
	
	public void activateParticleSystem(){
		particleEmitter.setEnabled(true);
	}
	
	public void desactivateParticleSystem(){
		particleEmitter.setEnabled(false);
	}
	
	public ParticleEmitter getParticleEmitter(){
		return (ParticleEmitter)particleEmitter;
	}
	
	public void arreter(MegaPerso perso) {
		if (this.getLieuActuel() == perso.getLieuActuel()) {
			perso.kill();
			System.out.println(perso.getNom() + " arrêté!");
			this.poursuivi = null;
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
	public void draw(Graphics g) {
		if(!isDead()){
			super.draw(g);		
			imgBig.draw(getAvatarX(), getAvatarY(), getAvatarWidth(), getAvatarHeight());
		}
	}
	
	@Override
	public int getVitesse() {
		return super.getVitesse()+Context.getNiveau()+1;
	}

}
