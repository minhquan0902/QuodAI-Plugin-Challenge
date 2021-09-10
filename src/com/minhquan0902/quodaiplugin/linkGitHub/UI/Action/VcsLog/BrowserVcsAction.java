package com.minhquan0902.quodaiplugin.linkGitHub.UI.Action.VcsLog;


import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.Commit;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.RemoteHost;
import com.minhquan0902.quodaiplugin.linkGitHub.LinkGitHub;

import org.jetbrains.annotations.NotNull;

public class BrowserVcsAction extends VcsLogAction {

    @Override
    protected void perform(@NotNull final Project project, @NotNull final Commit commit, @NotNull final VirtualFile file) {
        LinkGitHub.getInstance(project).openCommit(commit, file);
    }

    @Override
    protected String displayName(@NotNull final RemoteHost remoteHost)
    {
        return String.format("Open in %s", remoteHost.toString());
    }


}
