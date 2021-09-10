package com.minhquan0902.quodaiplugin.linkGitHub.UI.Settings;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.minhquan0902.quodaiplugin.linkGitHub.Plugin;
import com.minhquan0902.quodaiplugin.linkGitHub.Preferences;
import com.minhquan0902.quodaiplugin.linkGitHub.URL.Modifier.UrlModifierProvider;

import javax.swing.*;
public class ConfigurableSettings implements Configurable {
    private final Project project;
    private com.minhquan0902.quodaiplugin.linkGitHub.UI.Settings.Settings ui;


    public ConfigurableSettings(Project project)
    {
        this.project = project;
    }


    public JComponent createComponent()
    {
        ui = new com.minhquan0902.quodaiplugin.linkGitHub.UI.Settings.Settings(
                Preferences.getInstance(project),
                ServiceManager.getService(UrlModifierProvider.class).modifiers(),
                ServiceManager.getService(Plugin.class)
        );

        return ui.getRootPanel();
    }


    @Override
    public void disposeUIResources()
    {
        ui = null;
    }


    public String getHelpTopic()
    {
        return ServiceManager.getService(Plugin.class).displayName();
    }


    public String getDisplayName()
    {
        return ServiceManager.getService(Plugin.class).displayName();
    }


    public boolean isModified()
    {
        return ui.isModified();
    }


    public void apply() throws ConfigurationException
    {
        ui.apply();
    }


    public void reset()
    {
        ui.reset();
    }

}
