package com.minhquan0902.quodaiplugin.linkGitHub.UI.Action.Menu;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.RemoteHost;
import com.minhquan0902.quodaiplugin.linkGitHub.LinkGitHub;
import com.minhquan0902.quodaiplugin.linkGitHub.UI.LineSelection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BrowserMenuAction extends MenuAction {

    @Override
    protected void perform(@NotNull final Project project, @NotNull final VirtualFile file, @Nullable final LineSelection lineSelection) {
        LinkGitHub.getInstance(project).openFile(file, null, lineSelection);
    }

    @Override
    protected String displayName(@NotNull final RemoteHost remoteHost)
    {
        return String.format("Open in %s", remoteHost.toString());
    }
}
