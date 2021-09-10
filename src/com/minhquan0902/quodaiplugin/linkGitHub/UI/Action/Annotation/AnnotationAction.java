package com.minhquan0902.quodaiplugin.linkGitHub.UI.Action.Annotation;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.annotate.FileAnnotation;
import com.intellij.openapi.vcs.history.VcsRevisionNumber;
import com.intellij.openapi.vfs.VirtualFile;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.Commit;
import com.minhquan0902.quodaiplugin.linkGitHub.UI.Action.Action;
import com.minhquan0902.quodaiplugin.linkGitHub.UI.LineSelection;
import org.jetbrains.annotations.NotNull;

public abstract class AnnotationAction extends Action {

    private FileAnnotation annotation;
    private int lineNumber = -1;

    public AnnotationAction(@NotNull final FileAnnotation annotation) {
        this.annotation = annotation;
    }

    protected abstract void perform(
            @NotNull Project project,
            @NotNull Commit commit,
            @NotNull VirtualFile file,
            @NotNull LineSelection lineSelection
    );

    @Override
    public void actionPerformed(final Project project, @NotNull final AnActionEvent event) {

        if (lineNumber < 0) {
            return;
        }

        VirtualFile file = event.getData(CommonDataKeys.VIRTUAL_FILE);

        VcsRevisionNumber revisionNumber = this.annotation.getLineRevisionNumber(lineNumber);

        if (file == null || project == null || revisionNumber == null) {
            return;
        }

        perform(project, new Commit(revisionNumber.asString()), file, new LineSelection(this.lineNumber + 1));
    }


    protected boolean shouldActionBeEnabled(@NotNull final AnActionEvent event) {
        return (event.getData(CommonDataKeys.VIRTUAL_FILE) != null);
    }

    public void consume(@NotNull final Integer integer) {
        lineNumber = integer;
    }
}
