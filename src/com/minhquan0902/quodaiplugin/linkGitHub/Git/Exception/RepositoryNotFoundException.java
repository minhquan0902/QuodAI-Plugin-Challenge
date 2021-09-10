package com.minhquan0902.quodaiplugin.linkGitHub.Git.Exception;

import com.minhquan0902.quodaiplugin.linkGitHub.Exception.MainException;

// Thrown when cannot found any repository
public class RepositoryNotFoundException extends MainException {

    public RepositoryNotFoundException()
    {
        super("Git repository not found, has the root been registered in Preferences → Version Control?");
    }
}
