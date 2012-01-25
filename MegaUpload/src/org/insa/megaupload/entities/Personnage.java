package org.insa.megaupload.entities;

import java.util.Stack;

import org.insa.megaupload.example.Context;
import org.insa.megaupload.example.CoolFireEmitter;
import org.insa.megaupload.utils.Algo;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ParticleSystem;

public class Personnage {
	protected Image img;
	private Lieu lieuActuel;
	private Deplacement deplacement;
	private CoolFireEmitter particleEmitter;
	
    private static ParticleSystem particleSystem = null; 
    
	protected final static double coefRand = 0;
	
	public Personnage(Lieu lieuInitial, Image img) {
		this.lieuActuel = lieuInitial;
		this.img = img;
		
		particleEmitter = new CoolFireEmitter(this.getX(), this.getY(), 10);
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
			int avancement = this.deplacement.getAvancementEtape();
			x = source.getX() + (avancement * (cible.getX() - source.getX()))/100; 
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
			int avancement = this.deplacement.getAvancementEtape();
			y = source.getY() + (avancement * (cible.getY() - source.getY()))/100;  
		} else {
			y = this.lieuActuel.getY();
		}
		
		return y;
	}
	
	public void update(int delta){
		Deplacement d;
		
		d = this.getDeplacement();
		if (d != null) {
			int distanceTotale = d.getEtape().getDistance();
			double distanceParcourue = (double)(d.getAvancementEtape() * distanceTotale) / 100.;
			d.setAvancementEtape((int) (100.*(double)(distanceParcourue + 5)/(double)distanceTotale));
		}
		
        particleSystem.update(delta);
        particleEmitter.setX(this.getX());
        particleEmitter.setY(this.getY());	
        
	}
	
	public void draw() {
		particleSystem.render();
		img.draw(this.getX() - 10, this.getY() - 10, 20, 20);
	}
	
	private static ParticleSystem getParticleSystem(){

		if (particleSystem == null) {
			Image image = null;
			try {
				image = new Image("resources/img/dollard.png");
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			particleSystem = new ParticleSystem(image);
			particleSystem.setPosition(0, 0);
		}

		return particleSystem;
	}
	
}
