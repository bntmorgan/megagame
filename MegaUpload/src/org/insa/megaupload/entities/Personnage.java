package org.insa.megaupload.entities;

import java.awt.Color;
import java.sql.Timestamp;
import java.util.Stack;

import org.insa.megaupload.actions.Action;
import org.insa.megaupload.actions.Deplacement;
import org.insa.megaupload.example.Context;
import org.insa.megaupload.example.CoolFireEmitter;
import org.insa.megaupload.rules.DeplacementRules;
import org.insa.megaupload.utils.Algo;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ParticleSystem;

public abstract class Personnage {
	protected Image imgBig;
	protected Image imgPawn;
	private Lieu lieuActuel;
	protected Action action;
	private CoolFireEmitter particleEmitter;
	private int vitesse;
	protected int num;
	private int frais;
	private Timestamp timestampDenierAchat;
	
    private static ParticleSystem particleSystem = null; 
    
	protected final static double coefRand = 0;
	
	public Personnage(Lieu lieuInitial, Image imgBig, Image imgPawn) {
		this.lieuActuel = lieuInitial;
		this.imgBig = imgBig;
		this.imgPawn = imgPawn;
		this.vitesse = 5;
		this.frais = 0;
		this.timestampDenierAchat = new Timestamp(0);
		
		particleEmitter = new CoolFireEmitter(this.getX(), this.getY(), 6f, Color.GREEN );
		getParticleSystem().addEmitter(particleEmitter);	
	}
	
	public int getNum() {
		return num;
	}
	
	public int getVitesse() {
		return vitesse;
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
	
	public void setAction(Action a) {
		this.action = a;
	}
	
	public Deplacement getDeplacement() {
		if (this.action instanceof Deplacement) {
			return ((Deplacement) this.action);
		} else {
			return null;
		}
	}
	
	public void seDeplacer(Lieu l) {
		if (action == null) { // On ne fait rien
			Stack<Trajet> trajets = (Stack<Trajet>) Algo.PCC(Context.getCarte(), this.getLieuActuel(), l, 1 - getCoefRand());
			if (!trajets.isEmpty()) {
				setAction(new Deplacement(this, this.getLieuActuel(), l, trajets));
			}
		} else if (action instanceof Deplacement) { // On se déplace déjà : on ajuste le déplacement
			Lieu curCible = getDeplacement().getEtape().getCible(this.getLieuActuel());
			Stack<Trajet> trajets = (Stack<Trajet>) Algo.PCC(Context.getCarte(), curCible, l, 1 - getCoefRand());
			if (!trajets.isEmpty()) {
				if (DeplacementRules.peutSeDeplacer(Deplacement.getDistance(trajets))) {
					trajets.push(getDeplacement().getEtape());
					getDeplacement().setEtapes(trajets);
					getDeplacement().setCible(l);
				}
			}
		}
	}
	
	public int getX() {
		int x;
		
		if (getDeplacement() != null) {
			Trajet etape = getDeplacement().getEtape();
			Lieu source = this.getLieuActuel();
			Lieu cible = etape.getCible(source);
			double avancement = getDeplacement().getAvancementEtape();
			x = (int) (source.getX() + (avancement * (cible.getX() - source.getX()))/100); 
		} else {
			x = this.lieuActuel.getX();
		}
		
		return x;
	}
	
	public int getY() {
		int y;
		
		if (getDeplacement() != null) {
			Trajet etape = getDeplacement().getEtape();
			Lieu source = this.getLieuActuel();
			Lieu cible = etape.getCible(source);
			double avancement = getDeplacement().getAvancementEtape();
			y = (int) (source.getY() + (avancement * (cible.getY() - source.getY()))/100);  
		} else {
			y = this.lieuActuel.getY();
		}
		
		return y;
	}
	
	public void update(int delta) {
        if (action != null) {
        	action.update(delta);
        }
        
        particleSystem.update(delta);
        particleEmitter.setX(this.getX());
        particleEmitter.setY(this.getY());
	}
	
	public void draw(Graphics g) {
		imgPawn.draw(this.getX() - imgPawn.getWidth()/2, this.getY() - imgPawn.getHeight()/2, imgPawn.getWidth(), imgPawn.getHeight());
		
		// XXX: uniformiser draw/render
		if (action != null) {
			action.render(g);
		}
		
		if (this.timestampDenierAchat.after(new Timestamp(System.currentTimeMillis()-1*1000))) {
			String str = String.valueOf(-this.frais) + "$";
			int x = this.getX() - ((imgPawn.getWidth()/2 + g.getFont().getWidth(str))/2);
			int y = this.getY() + imgPawn.getHeight()/2;
			g.drawString(str, x, y);
		}
	}
	
	public abstract int getAvatarX();
	public abstract int getAvatarY();
	public abstract int getAvatarWidth();
	public abstract int getAvatarHeight();
	
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
}
