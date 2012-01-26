package org.insa.megaupload.actions;

import org.insa.megaupload.entities.Personnage;
import org.newdawn.slick.Graphics;

public abstract class Action {
	protected Personnage perso;
	protected int tempsTotal;
	protected int tempsRestant;
	
	public Action(Personnage perso, int tempsTotal) {
		this.perso = perso;
		this.tempsTotal = tempsTotal;
		this.tempsRestant = tempsTotal;
	}
	
	public void update(int delta) {
		if (tempsRestant <= 0) {
			finished();
			perso.setAction(null);
		} else {
			tempsRestant -= delta;
		}
	}
	
	public void render(Graphics g) {		
		g.drawRect(perso.getAvatarX(), perso.getAvatarY() + perso.getAvatarHeight() - 10, perso.getAvatarWidth(), 10);
		int filledWidth = (int) (perso.getAvatarWidth() * ((double)(tempsTotal - tempsRestant)/(double)tempsTotal));
		g.fillRect(perso.getAvatarX(), perso.getAvatarY() + perso.getAvatarHeight() - 10, filledWidth, 10);
	}
	
	public abstract void finished();
}
