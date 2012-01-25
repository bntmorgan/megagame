/**
 * 
 */
package org.insa.megaupload.example;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;



/**
 * @author escudero
 *
 */
public class DrawAction {
	
	private Action action;
	
	private static Image img1;
	private static Image img2;
	

	public DrawAction() {
	}
	
	public static void init() throws SlickException {
		img1 = new Image("resources/img/Backup-IBM-Server-icon-30px.png");
		img2 = new Image("resources/img/plane.png");
	}
	
	public void draw(int num, Action action) throws SlickException {
		this.action = action;
		switch (this.action) {
			case SE_DEPLACER :
				img1.draw(110, 100*(1 + num)-25, img1.getWidth()*100/img2.getHeight(), 50);
				break ;
			case OUVRIR_SERVEUR :
				img2.draw(110, 100*(1 + num)+25, img2.getWidth()*100/img2.getHeight(), 50);
				break;
			default:
		}
		
	}
	
}
