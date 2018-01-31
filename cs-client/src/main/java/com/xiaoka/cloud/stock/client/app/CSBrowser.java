package com.xiaoka.cloud.stock.client.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaoka.cloud.stock.client.app.bridge.JSBridge;
import com.xiaoka.cloud.stock.client.app.util.UIViewUtils;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class CSBrowser {
	private Logger logger = LoggerFactory.getLogger(CSBrowser.class);
	/**
	 */
	private WebView webView;
	/**
	 */
	private WebEngine engine;

	private Runnable loadingFinishedHook;

	private Boolean enableDebugMode = false;

	public CSBrowser(Runnable loadingFinishedHook) {
		this(loadingFinishedHook, false);
	}

	/**
	 * 
	 * @param loadingFinishedHook
	 *            浏览器初始化结束后事件钩子
	 * @param enableDebugMode
	 *            是否调试
	 */
	public CSBrowser(Runnable loadingFinishedHook, Boolean enableDebugMode) {
		this.loadingFinishedHook = loadingFinishedHook;
		this.enableDebugMode = enableDebugMode;
		initialize();
	}

	public void loadUrl(String url) {
		// url = "res/client/app/bridge/demo/test.html";
		if (StringUtils.startsWithAny(url, "http://", "https://")) {
			engine.load(url);
		} else {
			engine.load(Thread.currentThread().getContextClassLoader().getResource(url).toExternalForm());
		}
	}

	WebView getWebView() {
		return webView;
	}

	private void initialize() {
		webView = new WebView();
		webView.autosize();
		engine = webView.getEngine();
		Objects.requireNonNull(engine);

		// enable java script
		engine.setJavaScriptEnabled(true);

		// 支持Alert函数
		engine.setOnAlert((eventArgs) -> {
			String message = eventArgs.getData();
			showAlert(message);
		});

		// 添加加载完成钩子
		registerLoadingFinishedHook();
		// 加载初始内容
		engine.loadContent(createInitialBrowserContent());
		// 删除Cookies
		java.net.CookieHandler.setDefault(new java.net.CookieManager());

	}

	private void showAlert(String message) {
		Runnable alertRunnable = () -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("页面消息");
			alert.setHeaderText(message);
			alert.showAndWait();
		};
		Platform.runLater(alertRunnable);
	}

	private void registerLoadingFinishedHook() {
		JSBridge bridge = new JSBridge();
		ReadOnlyObjectProperty<State> state = engine.getLoadWorker().stateProperty();
		state.addListener((ObservableValue<? extends State> obv, State oldState, State newState) -> {
			if (newState == Worker.State.SUCCEEDED) {
				// JS桥接
				this.injectJSBridge(bridge);
				// 加载 Firebug
				if (enableDebugMode) {
					injectFireBug();
				}
				if (loadingFinishedHook != null) {
					loadingFinishedHook.run();
				}
			} else if (newState == Worker.State.FAILED) {
				logger.error("页面加载失败");
			}

		});
	}

	private void injectJSBridge(JSBridge bridge) {
		JSObject win = UIViewUtils.findJSObject(webView.getEngine(), "window");
		win.setMember("$JSBridge$", bridge);
		String bridgeScript = getJavaScriptLibraryFromFile("res/client/app/bridge/JSBridge.js");
		if (bridgeScript != null) {
			engine.executeScript(bridgeScript);
		}
	}

	private void injectFireBug() {
		String fireBugCommand = "if (!document.getElementById('FirebugLite')){" + "E = document['createElement' + 'NS'] && "
				+ "document.documentElement.namespaceURI;E = E ? " + "document['createElement' + 'NS'](E, 'script') : "
				+ "document['createElement']('script');" + "E['setAttribute']('id', 'FirebugLite');"
				+ "E['setAttribute']('src', 'https://getfirebug.com/' + 'firebug-lite.js' + '#startOpened');"
				+ "E['setAttribute']('FirebugLite', '4');"
				+ "(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);"
				+ "E = new Image;" + "E['setAttribute']('src', 'https://getfirebug.com/' + '#startOpened');" + "}";

		webView.getEngine().executeScript(fireBugCommand);
	}

	private String createInitialBrowserContent() {
		String htmlContent = "<!DOCTYPE html><meta charset=\"utf-8\">" //
				+ "<body style='text-align:center;margin-top:200px'><b>应用启动中……</b></body>";
		return htmlContent;
	}

	private String getJavaScriptLibraryFromFile(String fileName) {
		StringBuilder libraryContents = new StringBuilder();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF8"));
			String line = reader.readLine();
			while (line != null) {
				libraryContents.append(line).append("\n");
				line = reader.readLine();
			}
		} catch (IOException exception) {
			return null;
		}
		return libraryContents.toString();

	}
}
