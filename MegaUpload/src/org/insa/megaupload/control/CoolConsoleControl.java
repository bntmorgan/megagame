package org.insa.megaupload.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.AbstractController;
import de.lessvoid.nifty.controls.Console;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.Color;
import de.lessvoid.xml.xpp3.Attributes;

/**
 * A Nifty Control that represents a input console.
 * 
 * @author void
 * @deprecated Please use {@link de.lessvoid.nifty.controls.Console} when
 *             accessing NiftyControls.
 */

public class CoolConsoleControl extends AbstractController implements Console {
	private Nifty nifty;
	private Screen screen;
	private Element element;
	private ListBox<String> listBox;
	private Color standardColor = null;
	private Color errorColor = new Color("#f00a");

	@SuppressWarnings("unchecked")
	@Override
	public void bind(final Nifty niftyParam, final Screen screenParam,
			final Element newElement, final Properties properties,
			final Attributes controlDefinitionAttributes) {
		super.bind(newElement);
		this.nifty = niftyParam;
		this.screen = screenParam;
		this.element = newElement;
		this.listBox = element.findNiftyControl("#listBox", ListBox.class);
		initialFill();
	}

	@Override
	public void init(final Properties parameter,
			final Attributes controlDefinitionAttributes) {
		super.init(parameter, controlDefinitionAttributes);
	}

	@Override
	public void onStartScreen() {

	}

	@Override
	public boolean inputEvent(final NiftyInputEvent inputEvent) {
		return false;
	}

	@Override
	public void output(final String value) {
		out(value, standardColor);
	}

	@Override
	public void output(final String value, final Color color) {
		out(value, color);
	}

	@Override
	public void outputError(final String value) {
		out(value, errorColor);
	}

	@Override
	public String[] getConsoleContent() {
		return listBox.getItems().toArray(new String[0]);
	}

	@Override
	public void clear() {
		listBox.clear();
		initialFill();
	}

	@Override
	public void changeColors(final Color standardColor, final Color errorColor) {
		this.standardColor = standardColor;
		this.errorColor = errorColor;
	}

	private void initialFill() {
	}

	private void out(final String param, final Color color) {
		String value = nifty.specialValuesReplace(param);

		String[] lines = value.split("\n");
		List<String> list = new ArrayList<String>(lines.length);
		for (String line : lines) {
			if (color != null) {
				list.add("\\" + color.getColorString() + "#" + line);
			} else {
				list.add(line);
			}
		}
		listBox.addAllItems(list);
		listBox.showItemByIndex(listBox.itemCount() - 1);
	}

	@Override
	public TextField getTextField() {
		return null;
	}

}
