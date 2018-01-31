package com.xiaoka.cloud.stock.client.app.util;

import javafx.scene.web.WebEngine;
import javafx.stage.Screen;
import netscape.javascript.JSObject;

public class UIViewUtils {

	/**
	 * Gets the visual screen width.
	 *
	 * @return The screen <b>Width</b> based on the <b>visual bounds</b> of the
	 *         Screen.These bounds account for objects in the native windowing
	 *         system such as task bars and menu bars. These bounds are
	 *         contained by Screen.bounds.
	 */
	public static double getVisualScreenWidth() {
		return Screen.getPrimary().getVisualBounds().getWidth();
	}

	/**
	 * Gets the visual screen height.
	 *
	 * @return The screen <b>Height</b> based on the <b>visual bounds</b> of the
	 *         Screen.These bounds account for objects in the native windowing
	 *         system such as task bars and menu bars. These bounds are
	 *         contained by Screen.bounds.
	 */
	public static double getVisualScreenHeight() {
		return Screen.getPrimary().getVisualBounds().getHeight();
	}

	public static JSObject findJSObject(WebEngine webEngine, String object) {
		try {
			return (JSObject) webEngine.executeScript(object);
		} catch (Exception e) {
			return null;
		}
	}
	
	
}
