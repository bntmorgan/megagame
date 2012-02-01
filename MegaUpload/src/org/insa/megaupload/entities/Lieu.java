package org.insa.megaupload.entities;

import java.awt.Color;
import java.util.ArrayList;

import org.insa.megaupload.example.Context;
import org.insa.megaupload.example.CoolFireEmitter;
import org.insa.megaupload.example.CoolLinearParticleEmitter;
import org.insa.megaupload.game.Drawable;
import org.insa.megaupload.game.Updatable;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.effects.FireEmitter;

import de.lessvoid.nifty.Nifty;

public class Lieu implements Updatable, Drawable{
	private String nom;
	private int coutHeberg;
	private int risque;
	private int tempsMiseEnPlace;
	private int x;
	private int y;
	private ArrayList<Trajet> trajets;
	private ArrayList<Serveur> serveurs;
	
	//fonctionnalité de pare feu
	private PareFeu pareFeu;
	
	//gestionnaire de particules pour afficher de le pare feu de manière noobifiante
	private static ParticleSystem particleSystem = null; 	
	
	//emmeteur de particule pour le lieu
	private CoolFireEmitter particleEmitter;

	private static Image img, imgH;
	private static Image imgMu, imgMuH;

	public Lieu(String nom, int x, int y, int coutHeberg, int risque, int tempsMiseEnPlace) {
		this.nom = nom;
		this.x = x;
		this.y = y;
		this.coutHeberg = coutHeberg;
		this.risque = risque;
		this.tempsMiseEnPlace = tempsMiseEnPlace;
		this.trajets = new ArrayList<Trajet>();
		this.serveurs = new ArrayList<Serveur>();
		
		//initialisation de l'emtteur de particules
		particleEmitter = new CoolFireEmitter(this.getX(), this.getY(), 4f);
		getParticleSystem().addEmitter(particleEmitter);
		particleEmitter.setEnabled(false);
	}
	
	public static void init() throws SlickException {
		img = new Image("resources/img/City-30px.png");
		imgH = new Image("resources/img/City-40px.png");
		imgMu = new Image("resources/img/megaupload-logo-20px.png");
		imgMuH = new Image("resources/img/megaupload-logo-30px.png");
	}

	public String getNom() {
		return nom;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return img.getWidth();
	}

	public int getHeight() {
		return img.getWidth();
	}

	public int getCoutHeberg() {
		return coutHeberg;
	}

	public int getRisque() {
		return risque;
	}

	public int getTempsMiseEnPlace() {
		return tempsMiseEnPlace;
	}
	
	public ArrayList<Trajet> getTrajets() {
		return trajets;
	}

	public void addTrajet(Trajet trajet) {
		this.trajets.add(trajet);
	}
	
	public ArrayList<Serveur> getServeurs() {
		return serveurs;
	}
	
	public void delServeurs() {
		//décrémente le compteur global de serveurs
		Context.decCptServeurs(this.serveurs.size());
		Context.removeLieuAvecServeur(this);
		this.serveurs.removeAll(this.serveurs);
	}

	public void addServeur(Serveur serveur) {
		this.serveurs.add(serveur);
		if (this.serveurs.size() == 1) {
			Context.addLieuAvecServeur(this);
		}
		Context.incCptServeurs();
	}

	public void draw(Graphics g) {
		if (Context.getHoveredLieu() == this) {
			if(this.serveurs.isEmpty()){
				imgH.draw(x - imgH.getWidth() / 2, y - imgH.getHeight() / 2);				
			}
			else{
				imgMuH.draw(x - imgMuH.getWidth() / 2, y - imgMuH.getHeight() / 2);	
			}

			g.drawString(this.nom, x, y + imgH.getHeight() / 2);
			if (this.serveurs.size() <= 1)
				g.drawString(Integer.toString(this.serveurs.size()) + " server" , x, y + imgH.getHeight());
			else
				g.drawString(Integer.toString(this.serveurs.size()) + " servers" , x, y + imgH.getHeight());
				
		} else {
			if(this.serveurs.isEmpty()){
				img.draw(x - img.getWidth() / 2, y - img.getHeight() / 2);				
			}
			else{
				imgMu.draw(x - imgMu.getWidth() / 2, y - imgMu.getHeight() / 2);
			}

		}
	}
	
	@Override
	public String toString() {
		return this.nom;
	}	

	public static ParticleSystem getParticleSystem() {
		if (particleSystem == null) {
			Image image = null;
			try {
				image = new Image("resources/img/kim.png");
			} catch (SlickException e) {
				e.printStackTrace();
			}
			particleSystem = new ParticleSystem(image);
			particleSystem.setPosition(0, 0);
		}
		return particleSystem;
	}

	@Override
	public void update(int delta) {
		//mise a jour du pare feu
		if(pareFeu != null){
			pareFeu.decrementTime(delta);
			if(pareFeu.getTempsRestant() <= 0){
				//on supprime le pare feu
				pareFeu = null;
				//on desactive les particules
				desactivateParticleEmitter();
			}
		}
	}
	
	public void addPareFeu(){
		pareFeu = new PareFeu();
		activateParticleEmitter();
	}
	
	public void addPareFeu(PareFeu pareFeu){
		this.pareFeu = pareFeu;
		activateParticleEmitter();
	}
	
	public void removePareFeu(){
		pareFeu = null;
		desactivateParticleEmitter();
	}
	
	public boolean isPareFeu(){
		return pareFeu != null;
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
}
