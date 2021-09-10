package com.minhquan0902.quodaiplugin.linkGitHub.URL.Factory;

import com.minhquan0902.quodaiplugin.linkGitHub.Git.Exception.RemoteException;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.Remote;
import com.minhquan0902.quodaiplugin.linkGitHub.URL.Factory.Exception.UrlFactoryException;
import org.jetbrains.annotations.NotNull;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

abstract class AbstractUrlFactory implements UrlFactory {
    URL buildURL(@NotNull final Remote remote, @NotNull final String path, final String fragment) throws UrlFactoryException, RemoteException {
        URL host = remote.url();

        try {

            URI uri = new URI(host.getProtocol(), host.getHost(), path, null, fragment);

            return uri.toURL();

        } catch (URISyntaxException | MalformedURLException e) {
            throw UrlFactoryException.cannotCreateUrl(e.getMessage());
        }
    }
}
