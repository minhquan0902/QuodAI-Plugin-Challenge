package com.minhquan0902.quodaiplugin.linkGitHub.UI.Action.Annotation.Commit;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.annotate.FileAnnotation;
import com.intellij.openapi.vfs.VirtualFile;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.Commit;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.RemoteHost;
import com.minhquan0902.quodaiplugin.linkGitHub.LinkGitHub;
import com.minhquan0902.quodaiplugin.linkGitHub.UI.Action.Annotation.AnnotationAction;
import com.minhquan0902.quodaiplugin.linkGitHub.UI.LineSelection;
import org.jetbrains.annotations.NotNull;

public class BrowserCommitAnnotationAction extends AnnotationAction {
    public BrowserCommitAnnotationAction(@NotNull final FileAnnotation annotation) {
        super(annotation);
    }

    @Override
    protected String displayName(@NotNull final RemoteHost remoteHost) {
        return String.format("Open in %s", remoteHost.toString());
    }

    @Override
    protected void perform(
            @NotNull final Project project,
            @NotNull final Commit commit,
            @NotNull final VirtualFile file,
            @NotNull final LineSelection lineSelection
    ) {
        LinkGitHub.getInstance(project).openCommit(commit, file);
    }
}