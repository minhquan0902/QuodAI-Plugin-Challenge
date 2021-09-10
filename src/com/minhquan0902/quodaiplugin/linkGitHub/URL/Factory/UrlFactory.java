package com.minhquan0902.quodaiplugin.linkGitHub.URL.Factory;

import com.minhquan0902.quodaiplugin.linkGitHub.Git.Exception.RemoteException;
import com.minhquan0902.quodaiplugin.linkGitHub.UI.LineSelection;
import com.minhquan0902.quodaiplugin.linkGitHub.URL.Factory.Exception.UrlFactoryException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.net.URL;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.*;

public interface UrlFactory {

    URL createUrlToCommit(@NotNull final Remote remote, @NotNull final Commit commit) throws UrlFactoryException, RemoteException;

    URL createUrlToFileOnBranch(
            @NotNull final Remote remote,
            @NotNull final File file,
            @NotNull final Branch branch,
            @Nullable final LineSelection lineSelection
    ) throws UrlFactoryException, RemoteException;

    URL createUrlToFileAtCommit(
            @NotNull final Remote remote,
            @NotNull final File file,
            @NotNull final Commit commit,
            @Nullable final LineSelection lineSelection
    ) throws UrlFactoryException, RemoteException;

    boolean supports(@NotNull final RemoteHost host);
}
