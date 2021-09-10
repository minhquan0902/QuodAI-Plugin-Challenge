package com.minhquan0902.quodaiplugin.linkGitHub.UI.Extensions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.vcs.annotate.FileAnnotation;
import com.intellij.openapi.vcs.annotate.UpToDateLineNumberListener;
import com.minhquan0902.quodaiplugin.linkGitHub.UI.Action.Annotation.Commit.BrowserCommitAnnotationAction;
import com.minhquan0902.quodaiplugin.linkGitHub.UI.Action.Annotation.File.BrowserFileAnnotationAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AnnotationGutterActionProvider implements com.intellij.openapi.vcs.annotate.AnnotationGutterActionProvider {

    @NotNull
    @Override
    public AnAction createAction(@NotNull FileAnnotation annotation) {
        return new FileAndCommitGroup(annotation);
    }

    private static class FileAndCommitGroup extends ActionGroup implements UpToDateLineNumberListener {
        private BrowserCommitAnnotationAction browserCommitAnnotationAction;

        private BrowserFileAnnotationAction browserFileAnnotationAction;

        private Group fileGroup;
        private Group commitGroup;

        FileAndCommitGroup(@NotNull FileAnnotation annotation) {
            super("GitLink", true);

            browserCommitAnnotationAction    = new BrowserCommitAnnotationAction(annotation);

            browserFileAnnotationAction      = new BrowserFileAnnotationAction(annotation);


            fileGroup = new Group(
                    "File",
                    new AnAction[] {
                            browserFileAnnotationAction,

                    }
            );

            commitGroup = new Group(
                    "Commit",
                    new AnAction[] {
                            browserCommitAnnotationAction,

                    }
            );
        }

        @NotNull
        @Override
        public AnAction[] getChildren(@Nullable AnActionEvent e) {
            return new AnAction[]{fileGroup, commitGroup};
        }

        @Override
        public void consume(Integer integer) {
            browserCommitAnnotationAction.consume(integer);

            browserFileAnnotationAction.consume(integer);

        }
    }

    private static class Group extends ActionGroup {
        private final AnAction[] childGroups;

        Group(@NotNull String name, @NotNull AnAction[] children) {
            super(name, true);

            childGroups = children;
        }

        @NotNull
        @Override
        public AnAction[] getChildren(@Nullable AnActionEvent e) {
            return childGroups;
        }
    }

}
