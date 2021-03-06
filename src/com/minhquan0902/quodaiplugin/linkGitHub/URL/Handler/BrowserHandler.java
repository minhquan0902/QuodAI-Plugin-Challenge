package com.minhquan0902.quodaiplugin.linkGitHub.URL.Handler;

import com.intellij.ide.browsers.BrowserLauncher;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.serviceContainer.NonInjectable;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

public class BrowserHandler implements URLHandler {

    private final BrowserLauncher browserLauncher;

    public BrowserHandler() {
        this.browserLauncher = BrowserLauncher.getInstance();
    }

    @NonInjectable
    public BrowserHandler(@NotNull BrowserLauncher browserLauncher) {
        this.browserLauncher = browserLauncher;
    }

    public static BrowserHandler getInstance() {
        return ServiceManager.getService(BrowserHandler.class);
    }

    @Override
    public void handle(URL url) {
        browserLauncher.open(url.toString());
    }
}
