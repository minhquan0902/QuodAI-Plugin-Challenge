package com.minhquan0902.quodaiplugin.linkGitHub.Git;

import org.jetbrains.annotations.NotNull;


public class Commit {

    private final String hash;

    public Commit(@NotNull final String hash) {
        this.hash = hash;
    }

    @NotNull
    public String shortHash()
    {
        return hash.substring(0, 6);
    }

    @NotNull
    public String hash()
    {
        return hash;
    }
}
