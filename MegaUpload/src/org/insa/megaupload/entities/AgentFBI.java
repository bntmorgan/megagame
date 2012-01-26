package org.insa.megaupload.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AgentFBI extends Personnage {
	private static int nbAgents = 0;
	private static Image imgBig;
	private static Image imgPawn;
	
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
	
	public AgentFBI(Lieu lieuInitial) {
		super(lieuInitial, imgBig, imgPawn);
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
		
		//s'il y a des serveurs sur la position actuelle, ils peuvent etre fermés
		if(!this.getLieuActuel().getServeurs().isEmpty()){
			double rand = Math.random();
			Lieu l = this.getLieuActuel();
			double risque = rand * l.getRisque();
			if( risque > 1){
				l.delServeurs();
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
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		
		int height = 75;
		int width = imgBig.getWidth()*height/imgBig.getHeight();
		imgBig.draw(1280 - width - 10, 50 + (height+10)*num, width, height);
	}

}
