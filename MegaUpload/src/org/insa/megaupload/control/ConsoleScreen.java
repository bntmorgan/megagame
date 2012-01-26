package org.insa.megaupload.control;

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
public class ConsoleScreen implements ScreenController, KeyInputHandler {
	private Nifty nifty;
	private Screen screen;
	private Element consolePopup;
	private boolean consoleVisible = false;
	private boolean allowConsoleToggle = true;
	private boolean firstConsoleShow = true;

	public void bind(final Nifty newNifty, final Screen newScreen) {
		nifty = newNifty;
		screen = newScreen;
		screen.addKeyboardInputHandler(new DefaultInputMapping(), this);
		consolePopup = nifty.createPopup("consolePopup");
	}

	public void onStartScreen() {
		Console console = screen.findNiftyControl("consoleInfo", Console.class);
		console.output("Game started HFGL");
	}

	public void onEndScreen() {
	}

	public void back() {
		// nifty.fromXml("all/intro.xml", "menu");
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
		
		//TODO: pour test
		Console consoleInfo = screen.findNiftyControl("consoleInfo", Console.class);
		consoleInfo.output("your input was: " + command.getCommandLine() + " ["
				+ command.getArgumentCount() + " parameter(s)]");
	}
}
