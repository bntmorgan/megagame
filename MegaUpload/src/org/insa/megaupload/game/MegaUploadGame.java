/**
 * 
 */
package org.insa.megaupload.game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author garfunk
 *
 */
public class MegaUploadGame extends StateBasedGame {
	
	public MegaUploadGame(String name) {
		super(name);
	}
	
	public static void main(String argv[]) {
		try {
			AppGameContainer app;
			app = new AppGameContainer(new MegaUploadGame("MegaUploadFougeanoob"));
			app.setDisplayMode(1280, 667, false);
			app.setMaximumLogicUpdateInterval(10);
			app.setMinimumLogicUpdateInterval(10);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		this.addState(new MainMenuState("menu"));
		this.addState(new GameplayState());
	}

}
