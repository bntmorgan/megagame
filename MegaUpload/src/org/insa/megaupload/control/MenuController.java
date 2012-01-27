package org.insa.megaupload.control;

import org.newdawn.slick.state.StateBasedGame;

import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * Menu.
 * @author void
 */
public class MenuController implements ScreenController {

	/**
	 * the nifty instance.
	 */
	private Nifty nifty;

	/**
	 * the screen this menu belongs to.
	 */
	private Screen screen;

	private StateBasedGame game;

	/**
	 * bind.
	 * @param niftyParam niftyParam
	 * @param screenParam screenParam
	 */
	public void bind(final Nifty niftyParam, final Screen screenParam) {
		this.nifty = niftyParam;
		this.screen = screenParam;
		hideIfThere("thumbHelloWorld");
		hideIfThere("thumbHint");
		hideIfThere("thumbMouse");
		hideIfThere("thumbMenu");
		hideIfThere("thumbDragAndDrop");
		hideIfThere("thumbTextAlign");
		hideIfThere("thumbTextField");
		hideIfThere("thumbDropDownList");
		hideIfThere("thumbScrollpanel");
		hideIfThere("thumbMultiplayer");
		hideIfThere("thumbConsole");
		hideIfThere("thumbCredits");
		hideIfThere("thumbExit");
	}

	private void hideIfThere(final String elementName) {
		Element element = screen.findElementByName(elementName);
		if (element != null) {
			element.hide();
		}
	}

	/**
	 * just goto the next screen.
	 */
	public final void onStartScreen() {
	}

	public final void onEndScreen() {
	}

	public void start() {
		game.enterState(1);
	}

	public void rules() {
		nifty.gotoScreen("menu");
	}

	public void credits() {
		nifty.gotoScreen("outro");
	}

	public void exit() {
		nifty.createPopupWithId("popupExit", "popupExit");
		nifty.showPopup(screen, "popupExit", null);
	}

	/**
	 * popupExit.
	 * @param exit exit string
	 */
	public void popupExit(final String exit) {
		nifty.closePopup("popupExit", new EndNotify() {
			public void perform() {
				if ("yes".equals(exit)) {
					nifty.setAlternateKey("fade");
					nifty.exit();
				}
			}
		});
	}

	/**
	 * @return the game
	 */
	public StateBasedGame getGame() {
		return game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(StateBasedGame game) {
		this.game = game;
	}
}
