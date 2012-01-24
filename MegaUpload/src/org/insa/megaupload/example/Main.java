/**
 * 
 */
package org.insa.megaupload.example;

import org.insa.megaupload.entities.Carte;
import org.insa.megaupload.entities.Lieu;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author fougeane
 *
 */
public class Main extends BasicGame {
	private boolean mousePressed;
	private int mouseX;
	private int mouseY;
	
	public Main () {
		super("MegaUpload");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Main());
			app.setDisplayMode(1280, 667, false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		Carte c = new Carte();
		Context.setCarte(c);
		Lieu.setImages(new Image("resources/img/point-bleu.png"), new Image("resources/img/point-orange.png"));
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		Context.getCarte().draw(g);
		g.drawString("Welcome to MegaUpload!", 10, 50);
		g.setColor(Color.red);
		if (this.mousePressed) {
			int r = 6;
			g.fillOval(this.mouseX - r/2, this.mouseY - r/2, r, r);
			g.drawString("Mouse pressed: " + this.mouseX + ", " + this.mouseY, 10, 75);
		}
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		Lieu selectedLieu = null;
		for (Lieu l : Context.getCarte().getLieux()) {
			if (newx >= l.getX() - l.getWidth()/2 && newx <= l.getX() + l.getWidth()/2 &&
					newy >= l.getY() - l.getHeight()/2 && newy <= l.getY() + l.getHeight()/2) {
				selectedLieu = l;
				break;
			}
		}
		Context.setSelectedLieu(selectedLieu);
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		this.mousePressed = true;
		this.mouseX = x;
		this.mouseY = y;
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		this.mousePressed = false;
	}

}
