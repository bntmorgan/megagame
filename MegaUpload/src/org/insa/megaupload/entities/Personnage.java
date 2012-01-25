package org.insa.megaupload.entities;

import java.util.Stack;

import org.insa.megaupload.example.Context;
import org.insa.megaupload.utils.Algo;
import org.newdawn.slick.Image;

public class Personnage {
	protected Image img;
	private Lieu lieuActuel;
	private Deplacement deplacement;
	
	public Personnage(Lieu lieuInitial, Image img) {
		this.lieuActuel = lieuInitial;
		this.img = img;
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
	
	public void seDeplacer(Lieu l){
		Stack<Trajet> trajets = (Stack<Trajet>) Algo.PCC(Context.getCarte(), this.getLieuActuel(), l);
		if (!trajets.isEmpty()) {
			this.deplacement = new Deplacement(this, this.getLieuActuel(), l, trajets);
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
		
		//System.out.println("x: " + x);
		
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
		
		//System.out.println("y: " + y);
		
		return y;
	}
	
	public void draw() {
		img.draw(this.getX() - 10, this.getY() - 10, 20, 20);
	}
}
