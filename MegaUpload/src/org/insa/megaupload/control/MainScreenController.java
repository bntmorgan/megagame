package org.insa.megaupload.control;

import org.insa.megaupload.entities.MegaPerso;
import org.insa.megaupload.entities.Personnage;
import org.insa.megaupload.example.Action;
import org.insa.megaupload.example.Context;
import org.insa.megaupload.game.GameplayState;
import org.newdawn.slick.state.StateBasedGame;

import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Console;
import de.lessvoid.nifty.controls.ConsoleExecuteCommandEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.mapping.DefaultInputMapping;
import de.lessvoid.nifty.screen.KeyInputHandler;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * ConsoleDemoStartScreen.
 * 
 * @author void
 */
public class MainScreenController implements ScreenController, KeyInputHandler {
	private Nifty nifty;
	private Screen screen;
	private Element consolePopup;
	private boolean consoleVisible = false;
	private boolean allowConsoleToggle = true;
	private boolean firstConsoleShow = true;
	private Console infoConsole;
	private Console actionConsole;

	private StateBasedGame game;
	private GameplayState gameplayState;
	private static final int CONSOLE_COLUMNS = 36;
	
	public void bind(final Nifty newNifty, final Screen newScreen) {
		nifty = newNifty;
		screen = newScreen;
		screen.addKeyboardInputHandler(new DefaultInputMapping(), this);
		consolePopup = nifty.createPopup("consolePopup");
	}

	public void onStartScreen() {
		infoConsole = screen.findNiftyControl("consoleInfo", Console.class);
		this.addInfoText("Game started! Good luck and beware of the millenium :)");
		actionConsole = screen.findNiftyControl("consoleAction", Console.class);
		for (Personnage p : Context.getPersonnages()) {
			if (p instanceof MegaPerso) {
				((MegaPerso) p).startNifty(nifty);
			}
		}
	}

	public void onEndScreen() {
	}

	public void back() {
		toggleConsole();
	}

	public boolean keyEvent(final NiftyInputEvent inputEvent) {
		if (inputEvent == NiftyInputEvent.ConsoleToggle) {
			toggleConsole();
			return true;
		} else {
			return false;
		}
	}

	private void toggleConsole() {
		if (allowConsoleToggle) {
			allowConsoleToggle = false;
			if (consoleVisible) {
				closeConsole();
			} else {
				openConsole();
			}
		}
	}

	private void openConsole() {
		nifty.showPopup(screen, consolePopup.getId(),
				consolePopup.findElementByName("console#textInput"));
		screen.processAddAndRemoveLayerElements();

		if (firstConsoleShow) {
			firstConsoleShow = false;
			Console console = screen.findNiftyControl("consoleAction",
					Console.class);
			console.output("Hello dude type cool commands here");
		}

		consoleVisible = true;
		allowConsoleToggle = true;
	}

	private void closeConsole() {
		nifty.closePopup(consolePopup.getId(), new EndNotify() {
			@Override
			public void perform() {
				consoleVisible = false;
				allowConsoleToggle = true;
			}
		});
	}

	@NiftyEventSubscriber(id = "consoleAction")
	public void onConsoleCommand(final String id,
			final ConsoleExecuteCommandEvent command) {
		Console console = command.getConsole();
		console.output("your input was: " + command.getCommandLine() + " ["
				+ command.getArgumentCount() + " parameter(s)]");
		if ("exit".equals(command.getCommand())) {
			back();
		}
		if ("killdamothafucka".equals(command.getCommand()) || "kdm".equals(command.getCommand())) {
			gameplayState.returnToMenu();
		}
		
	};
	
	public void addActionText(String text){		
		actionConsole.output(text);
	}
	
	public void addInfoText(String text) {
		/*for (String str : StringUtil.getParts(text,CONSOLE_COLUMNS)) {
			infoConsole.output(str);
		}*/
		String curLine = "";
		for (String word : text.split(" ")) {
			if (curLine.length() + word.length() > CONSOLE_COLUMNS) {
				infoConsole.output(curLine);
				curLine = word + " ";
			} else {
				curLine += word + " ";
			}
		}
		
		if (!curLine.equals("")) {
			infoConsole.output(curLine);
		}
	}
	
	
	private MegaPerso getSelectedMegaPerso(String nom) {
		for (Personnage p : Context.getPersonnages()) {
			if (p instanceof MegaPerso && ((MegaPerso) p).getNom().equals(nom)) {
				return ((MegaPerso) p);
			}
		}
		return null;
	}

	public void megaPersoSelected(String nom) {
		Context.setSelectedPerso(getSelectedMegaPerso(nom));
	}

	public void megaPersoActionSelected(String nom, String action) {
		Action a = Action.valueOf(action);
		Context.setSelectedAction(null);
		if (a == Action.OUVRIR_SERVEUR) {
			getSelectedMegaPerso(nom).ouvrirServeur();
		} else if (a == Action.FUIR){
			getSelectedMegaPerso(nom).seDeplacer(Context.getCarte().getLieux().get(Context.rand(Context.getCarte().getLieux().size() - 1)));
		}
		Context.setSelectedAction(null);
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

	/**
	 * @param gameplayState the gameplayState to set
	 */
	public void setGameplayState(GameplayState gameplayState) {
		this.gameplayState = gameplayState;
	}
	
	/**
	 * pupop de win
	 * @param exit
	 */
	public void popupExit(final String exit) {
		nifty.closePopup("popupWin", new EndNotify() {
			public void perform() {
				if ("yes".equals(exit)) {
					gameplayState.closePopup();
					gameplayState.setPopupOn(false);
					gameplayState.returnToMenu();
				}
			}
		});
	}
	
}
