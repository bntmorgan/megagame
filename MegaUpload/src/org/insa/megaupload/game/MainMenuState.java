/**
 * 
 */
package org.insa.megaupload.game;

import org.newdawn.slick.state.StateBasedGame;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.slick2d.NiftyBasicGameState;
import org.insa.megaupload.control.MenuController;

/**
 * @author garfunk
 *
 */
public class MainMenuState extends NiftyBasicGameState {

	public MainMenuState(String screen) {
		super(screen);
	}
	
	@Override
	protected void prepareNifty(Nifty nifty, StateBasedGame game) {
		nifty.fromXml("resources/layout/intro.xml", "menu");
		((MenuController) nifty.getCurrentScreen().getScreenController()).setGame(game);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
