package org.insa.megaupload.entities;

import java.util.Stack;

import org.insa.megaupload.actions.Action;
import org.insa.megaupload.actions.Deplacement;
import org.insa.megaupload.example.Context;
import org.insa.megaupload.game.Drawable;
import org.insa.megaupload.game.Updatable;
import org.insa.megaupload.rules.DeplacementRules;
import org.insa.megaupload.utils.Algo;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ParticleSystem;

import de.lessvoid.nifty.Nifty;

public abstract class Personnage implements Updatable, Drawable{
	protected Action action;
	protected Image imgBig;
	protected Image imgPawn;
	private Lieu lieuActuel;
	protected int num;
	private boolean dead;

	private static ParticleSystem particleSystem = null; 
    
	protected static double coefRand = 0;
	
	private int vitesse;
	
	public Personnage(Lieu lieuInitial, Image imgBig, Image imgPawn) {
		this.lieuActuel = lieuInitial;
		this.imgBig = imgBig;
		this.imgPawn = imgPawn;
		this.vitesse = 5;
		
		Context.addPersonnage(this);
	}
	
	public Action getAction() {
		return action;
	}

	public abstract int getAvatarHeight();
	
	public abstract int getAvatarWidth();
	
	public abstract int getAvatarX();
	
	public abstract int getAvatarY();
	
	public double getCoefRand() {
		return coefRand;
	}
	
	public Deplacement getDeplacement() {
		if (this.action instanceof Deplacement) {
			return ((Deplacement) this.action);
		} else {
			return null;
		}
	}

	public int getHeight() {
		return imgPawn.getHeight();
	}
	
	public Lieu getLieuActuel() {
		return lieuActuel;
	}
	
	public int getNum() {
		return num;
	}
	
	public int getVitesse() {
		return vitesse;
	}
	public int getWidth() {
		return imgPawn.getWidth();
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
	
	public void seDeplacer(Lieu l) {
		if (action == null) { // On ne fait rien
			Stack<Trajet> trajets = Algo.PCC(Context.getCarte(), this.getLieuActuel(), l, 1 - getCoefRand());
			if (!trajets.isEmpty()) {
				if (DeplacementRules.peutSeDeplacer(this, Deplacement.getDistance(trajets))) {
					setAction(new Deplacement(this, this.getLieuActuel(), l, trajets));
				
					// Affichage du cout du déplacement total 
					if (this instanceof MegaPerso) {
						float coutDeplacement = ((Deplacement)action).getDistance()*DeplacementRules.getCoutDeplacement();
						Context.getMainScreenController().addInfoText(((MegaPerso)this).getNom() + " takes plane $" + (new Float(coutDeplacement)).intValue());
					}
				} else {
					Context.getMainScreenController().addInfoText("Not enougth money ! Sell prenium accounts !");
				}
			}
		} else if (action instanceof Deplacement) { // On se déplace déjà : on ajuste le déplacement
			Lieu curCible = getDeplacement().getEtape().getCible(this.getLieuActuel());
			Stack<Trajet> trajets = Algo.PCC(Context.getCarte(), curCible, l, 1 - getCoefRand());
			if (!trajets.isEmpty()) {
				if (DeplacementRules.peutSeDeplacer(this, Deplacement.getDistance(trajets))) {
					trajets.push(getDeplacement().getEtape());
					getDeplacement().setEtapes(trajets);
					getDeplacement().setCible(l);
				}
				else {
					Context.getMainScreenController().addInfoText("Not enougth money ! Sell prenium accounts !");
				}
			}
		}
	}
	public void setAction(Action a) {
		this.action = a;
	}
	
	public void setLieuActuel(Lieu l) {
		this.lieuActuel = l;
	}

	public void update(int delta) {
		if(!isDead()){
	        if (action != null) {
	        	action.update(delta);
	        }
		}
	}
	
	public void kill(){
		dead = true;
	}

	
	public void draw(Graphics g) {
		if(!isDead()){
			imgPawn.draw(this.getX() - imgPawn.getWidth()/2, this.getY() - imgPawn.getHeight()/2, imgPawn.getWidth(), imgPawn.getHeight());
			
			// XXX: uniformiser draw/render
			if (action != null) {
				action.render(g);
			}
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
	
    /**
	 * @return the dead
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * @param dead the dead to set
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}
}


