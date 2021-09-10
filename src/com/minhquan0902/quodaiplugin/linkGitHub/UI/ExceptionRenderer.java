package com.minhquan0902.quodaiplugin.linkGitHub.UI;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.components.ServiceManager;
import com.minhquan0902.quodaiplugin.linkGitHub.Exception.MainException;
import com.minhquan0902.quodaiplugin.linkGitHub.Plugin;
import org.jetbrains.annotations.NotNull;

public class ExceptionRenderer {
    private final Plugin plugin;

    public ExceptionRenderer() {
        this.plugin = ServiceManager.getService(Plugin.class);
    }

    public static ExceptionRenderer getInstance() {
        return ServiceManager.getService(ExceptionRenderer.class);
    }

    public void render(@NotNull final MainException exception) {
        Notifications.Bus.notify(new Notification(
                plugin.displayName(),
                plugin.toString(),
                exception.getMessage(),
                NotificationType.ERROR
        ));
    }
}
