package com.minhquan0902.quodaiplugin.linkGitHub.URL.Factory.Exception;

import com.minhquan0902.quodaiplugin.linkGitHub.Exception.MainException;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.RemoteHost;

public class UrlFactoryException extends MainException {
    private UrlFactoryException(String message)
    {
        super(message);
    }


    public static UrlFactoryException unsupportedRemoteHost(RemoteHost host)
    {
        return new UrlFactoryException(String.format("The remote host '%s' is not supported", host.name()));
    }


    public static UrlFactoryException cannotCreateUrl(String reason)
    {
        return new UrlFactoryException(String.format("Cannot create url (%s)", reason));
    }
}
