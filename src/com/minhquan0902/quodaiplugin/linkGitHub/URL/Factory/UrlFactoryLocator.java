package com.minhquan0902.quodaiplugin.linkGitHub.URL.Factory;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.minhquan0902.quodaiplugin.linkGitHub.Preferences;
import com.minhquan0902.quodaiplugin.linkGitHub.URL.Factory.Exception.UrlFactoryException;

import java.util.ArrayList;
import java.util.List;

public class UrlFactoryLocator {

    private List<UrlFactory> factories = new ArrayList<>();
    private Preferences preferences;

    public static UrlFactoryLocator getInstance(Project project) {
        return ServiceManager.getService(project, UrlFactoryLocator.class);
    }

    public UrlFactoryLocator(Project project) {

        this.preferences = Preferences.getInstance(project);


        this.factories.add(ServiceManager.getService(GitHubUrlFactory.class));

    }

    public UrlFactory locate() throws UrlFactoryException {
        for (UrlFactory factory : this.factories) {
            if (factory.supports(this.preferences.getRemoteHost())) {
                return factory;
            }
        }

        throw UrlFactoryException.unsupportedRemoteHost(this.preferences.getRemoteHost());
    }

}
