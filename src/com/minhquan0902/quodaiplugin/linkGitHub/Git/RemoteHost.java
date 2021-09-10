package com.minhquan0902.quodaiplugin.linkGitHub.Git;

import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public enum RemoteHost {

        GIT_HUB("GitHub", "/assets/github.png");


        private final String displayName;
        private final String icon;

        RemoteHost(@NotNull String displayName, @NotNull String icon)
        {
            this.displayName = displayName;
            this.icon        = icon;
        }

        @NotNull
        public String toString()
        {
            return displayName;
        }

        public boolean isGitHub()
        {
            return (this == GIT_HUB);
        }



        @NotNull
        public Icon icon()
        {
            return IconLoader.getIcon(icon);
        }

}
