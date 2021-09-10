package com.minhquan0902.quodaiplugin.linkGitHub.Git.Exception;

import com.minhquan0902.quodaiplugin.linkGitHub.Exception.MainException;
import org.jetbrains.annotations.NotNull;

public class BranchException extends MainException {
    private BranchException(@NotNull final String message)
    {
        super(message);
    }

    public static BranchException couldNotFetchBranchesFromRemoteRepository(@NotNull final String reason)
    {
        return new BranchException(String.format("Could not fetch branches from the remote repository - '%s'", reason));
    }
}
