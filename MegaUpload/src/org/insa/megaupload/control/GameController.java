package org.insa.megaupload.control;

import org.newdawn.slick.state.StateBasedGame;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class GameController implements ScreenController {
	private Nifty nifty;
	private Screen screen;
	private StateBasedGame game;

	@Override
	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;
	}

	@Override
	public void onEndScreen() {
	}

	@Override
	public void onStartScreen() {
		game.enterState(1);
	}
	
	public StateBasedGame getGame() {
		return game;
	}

	public void setGame(StateBasedGame game) {
		this.game = game;
	}
}
