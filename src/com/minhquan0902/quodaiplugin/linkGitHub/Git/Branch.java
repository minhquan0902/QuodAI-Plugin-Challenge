package com.minhquan0902.quodaiplugin.linkGitHub.Git;

import org.jetbrains.annotations.NotNull;

public class Branch {

    private final String name;

    public Branch(@NotNull final String name) {
        this.name = name;
    }

    @NotNull
    public static Branch main()
    {
        return new Branch("main");
    }

    @NotNull
    public String toString()
    {
        return name;
    }
}
