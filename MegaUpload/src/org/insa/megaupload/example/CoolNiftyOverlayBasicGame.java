package org.insa.megaupload.example;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.slick2d.input.SlickInputSystem;
import de.lessvoid.nifty.slick2d.input.SlickSlickInputSystem;
import de.lessvoid.nifty.slick2d.render.SlickRenderDevice;
import de.lessvoid.nifty.slick2d.sound.SlickSoundDevice;
import de.lessvoid.nifty.slick2d.time.LWJGLTimeProvider;
import de.lessvoid.nifty.spi.time.TimeProvider;

/**
 * This class implements a Slick Basic game with a NiftyGUI Overlay.
 * 
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public abstract class CoolNiftyOverlayBasicGame extends BasicGame {
  /**
   * The one and only Nifty GUI.
   */
  private Nifty niftyGUI;

  /**
   * Forward constructor to set the title of the game.
   * 
   * @param title
   *          the title of the game
   */
  protected CoolNiftyOverlayBasicGame(final String title) {
    super(title);
  }

  /**
   * Get the instance of the NiftyGUI that is used to render this screen.
   * 
   * @return the instance of the NiftyGUI
   */
  public final Nifty getNifty() {
    return niftyGUI;
  }

  /**
   * Initialize the game and the GUI.
   */
  @Override
  public final void init(final GameContainer container) throws SlickException {
    initGameAndGUI(container);

    /*if (niftyGUI == null) {
      throw new IllegalArgumentException("NiftyGUI is not initializied!");
    }*/
  }

  /**
   * Initialize the game. This function is called during
   * {@link #init(GameContainer)}. During this call its needed to initialize the
   * Nifty GUI with own options by calling
   * {@link #initNifty(GameContainer, SlickRenderDevice, SlickSoundDevice, TimeProvider)}
   * .
   * 
   * @param container
   *          the game container that displays the game
   * @throws SlickException
   *           in case initializing the game goes wrong
   */
  protected abstract void initGameAndGUI(final GameContainer container) throws SlickException;

  /**
   * Initialize the Nifty GUI for this game.
   * 
   * @param container
   *          the container used to display the game
   * @param renderDevice
   *          the render device that is supposed to be used to render the GUI
   * @param soundDevice
   *          the sound device that is supposed to be used
   * @param inputSystem
   *          the input system that is supposed to be used
   * @param timeProvider
   *          the time provider that is supposed to be used
   * @throws IllegalStateException
   *           in case this function was called before
   */
  protected final void initNifty(
      final GameContainer container,
      final SlickRenderDevice renderDevice,
      final SlickSoundDevice soundDevice,
      final SlickInputSystem inputSystem,
      final TimeProvider timeProvider) {
    if (niftyGUI != null) {
      throw new IllegalStateException("The NiftyGUI was already initialized. Its illegal to do so twice.");
    }

    final Input input = container.getInput();
    inputSystem.setInput(input);

    niftyGUI = new Nifty(renderDevice, soundDevice, inputSystem, timeProvider);

    /* Slick automatically adds the game as input listener. Undo this. */
    //input.removeListener(this);
    input.removeListener(inputSystem);
    input.addListener(inputSystem);

    prepareNifty(niftyGUI);
  }

  /**
   * Initialize the Nifty GUI for this game. This function will use the default
   * {@link de.lessvoid.nifty.tools.TimeProvider}.
   * 
   * @param container
   *          the container used to display the game
   * @param renderDevice
   *          the render device that is supposed to be used to render the GUI
   * @param soundDevice
   *          the sound device that is supposed to be used
   * @param inputSystem
   *          the input system that is supposed to be used
   * @throws IllegalStateException
   *           in case this function was called before
   */
  protected final void initNifty(
      final GameContainer container,
      final SlickRenderDevice renderDevice,
      final SlickSoundDevice soundDevice,
      final SlickInputSystem inputSystem) {
    initNifty(container, renderDevice, soundDevice, inputSystem, new LWJGLTimeProvider());
  }

  /**
   * Initialize the Nifty GUI for this game. This function will use the default
   * {@link de.lessvoid.nifty.tools.TimeProvider}. Also it will use the render
   * and sound devices that are provided with this library.
   * 
   * @param container
   *          the container used to display the game
   * @param inputSystem
   *          the input system that is supposed to be used
   * @throws IllegalStateException
   *           in case this function was called before
   * @see de.lessvoid.nifty.slick2d.render.SlickRenderDevice
   * @see de.lessvoid.nifty.slick2d.sound.SlickSoundDevice
   */
  protected final void initNifty(final GameContainer container, final SlickInputSystem inputSystem) {
    initNifty(container, new SlickRenderDevice(container), new SlickSoundDevice(), inputSystem);
  }

  /**
   * Initialize the Nifty GUI for this game. This function will use the default
   * {@link de.lessvoid.nifty.tools.TimeProvider}. Also it will use the render
   * and sound devices that are provided with this library. As for the input it
   * will forward all input to the Slick {@link org.newdawn.slick.InputListener}
   * that is implemented in this class.
   * 
   * @param container
   *          the container used to display the game
   * @throws IllegalStateException
   *           in case this function was called before
   * @see de.lessvoid.nifty.slick2d.render.SlickRenderDevice
   * @see de.lessvoid.nifty.slick2d.sound.SlickSoundDevice
   * @see de.lessvoid.nifty.slick2d.input.SlickSlickInputSystem
   */
  protected final void initNifty(final GameContainer container) {
    initNifty(container, new SlickSlickInputSystem(this));
  }

  /**
   * This function should be used to prepare the actual GUI and the controllers
   * of the Nifty GUI. It is called right after the Nifty GUI got initialized.
   * 
   * @param nifty
   *          the Nifty GUI that got initialized
   */
  protected abstract void prepareNifty(Nifty nifty);

  /**
   * Render the game.
   */
  @Override
  public final void render(final GameContainer container, final Graphics g) throws SlickException {
    renderGame(container, g);

    if (niftyGUI != null) {
      niftyGUI.render(false);
    }
  }

  /**
   * This function is supposed to be used to render the game. It is called
   * during the call of the {@link #render(GameContainer, Graphics)} function.
   * 
   * @param container
   *          the container that displays the game
   * @param g
   *          the graphics instance that is used to draw the game
   * @throws SlickException
   *           in case anything goes wrong during the rendering
   */
  protected abstract void renderGame(GameContainer container, Graphics g) throws SlickException;

  /**
   * Update the game.
   */
  @Override
  public final void update(final GameContainer container, final int delta) throws SlickException {    
    updateGame(container, delta);

    if (niftyGUI != null) {
      niftyGUI.update();
    }
  }

  /**
   * This function is supposed to be used to update the state of the game. It
   * called during the call of the {@link #update(GameContainer, int)} function.
   * 
   * @param container
   *          the container that displays the game
   * @param delta
   *          the time since the last update
   * @throws SlickException
   *           in case anything goes wrong during the update
   */
  protected abstract void updateGame(GameContainer container, int delta) throws SlickException;
}