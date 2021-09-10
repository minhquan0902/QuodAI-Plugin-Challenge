package com.minhquan0902.quodaiplugin.linkGitHub.Git.Exception;

import com.minhquan0902.quodaiplugin.linkGitHub.Exception.MainException;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.Remote;
import org.jetbrains.annotations.NotNull;

public class RemoteException extends MainException {
    private RemoteException(@NotNull final String message)
    {
        super(message);
    }

    public static RemoteException remoteNotFound(@NotNull final String remoteName)
    {
        return new RemoteException(String.format("Could not find the remote '%s' in the repository", remoteName));
    }

    public static RemoteException urlNotFoundForRemote(@NotNull final Remote remote)
    {
        return new RemoteException(String.format("Could not determine the URL for the remote '%s'", remote.name()));
    }

}
