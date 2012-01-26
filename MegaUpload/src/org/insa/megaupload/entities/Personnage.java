package org.insa.megaupload.entities;

import java.awt.Color;
import java.util.Stack;

import org.insa.megaupload.example.Context;
import org.insa.megaupload.example.CoolFireEmitter;
import org.insa.megaupload.rules.DeplacementRules;
import org.insa.megaupload.utils.Algo;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ParticleSystem;

public class Personnage {
	protected Image imgBig;
	protected Image imgPawn;
	private Lieu lieuActuel;
	private Deplacement deplacement;
	private CoolFireEmitter particleEmitter;
	private int vitesse;
	
    private static ParticleSystem particleSystem = null; 
    
	protected final static double coefRand = 0;
	
	public Personnage(Lieu lieuInitial, Image imgBig, Image imgPawn) {
		this.lieuActuel = lieuInitial;
		this.imgBig = imgBig;
		this.imgPawn = imgPawn;
		this.vitesse = 5;
		
		particleEmitter = new CoolFireEmitter(this.getX(), this.getY(), 6f, Color.GREEN );
		getParticleSystem().addEmitter(particleEmitter);
		
	}
	
	public double getCoefRand() {
		return coefRand;
	}

	public Lieu getLieuActuel() {
		return lieuActuel;
	}
	
	public void setLieuActuel(Lieu l) {
		this.lieuActuel = l;
	}
	
	public Deplacement getDeplacement() {
		return deplacement;
	}
	
	public void setDeplacement(Deplacement d) {
		this.deplacement = d;
	}
	
	public void seDeplacer(Lieu l) {
		//si pas en train de se déplacer
		if (this.getDeplacement() == null) {
			Stack<Trajet> trajets = (Stack<Trajet>) Algo.PCC(Context.getCarte(), this.getLieuActuel(), l, 1 - getCoefRand());
			if (!trajets.isEmpty()) {
				this.deplacement = new Deplacement(this, this.getLieuActuel(), l, trajets);
			}
		}
		else {
			Lieu curCible = this.deplacement.getEtape().getCible(this.getLieuActuel());
			Stack<Trajet> trajets = (Stack<Trajet>) Algo.PCC(Context.getCarte(), curCible, l, 1 - getCoefRand());
			if (!trajets.isEmpty()) {
				trajets.push(this.deplacement.getEtape());
				this.deplacement.setEtapes(trajets);
				this.deplacement.setCible(l);
			}
		}
	}
	
	public int getX() {
		int x;
		
		if (this.deplacement != null) {
			Trajet etape = this.deplacement.getEtape();
			Lieu source = this.getLieuActuel();
			Lieu cible = etape.getCible(source);
			double avancement = this.deplacement.getAvancementEtape();
			x = (int) (source.getX() + (avancement * (cible.getX() - source.getX()))/100); 
		} else {
			x = this.lieuActuel.getX();
		}
		
		return x;
	}
	
	public int getY() {
		int y;
		
		if (this.deplacement != null) {
			Trajet etape = this.deplacement.getEtape();
			Lieu source = this.getLieuActuel();
			Lieu cible = etape.getCible(source);
			double avancement = this.deplacement.getAvancementEtape();
			y = (int) (source.getY() + (avancement * (cible.getY() - source.getY()))/100);  
		} else {
			y = this.lieuActuel.getY();
		}
		
		return y;
	}
	
	public void update(int delta){
		Deplacement d = this.getDeplacement();
		
		if (d != null) {
			if (this instanceof MegaPerso && d.getAvancementEtape() == 0 ){
				int distanceTotale = d.getEtape().getDistance();
				Context.decCptThunes((int) (distanceTotale*DeplacementRules.getCoutDeplacement()));
			}
			
			int tempsTotal = d.getEtape().getTemps();
			double tempsParcouru = (d.getAvancementEtape() * tempsTotal) / 100.;
			d.setAvancementEtape(100 * (tempsParcouru + vitesse)/tempsTotal);
		}
		
        particleSystem.update(delta);
        particleEmitter.setX(this.getX());
        particleEmitter.setY(this.getY());	
	}
	
	public void draw(Graphics g) {
		imgPawn.draw(this.getX() - 10, this.getY() - 10, 20, 20);
	}
	
	public static ParticleSystem getParticleSystem(){

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
	
}
