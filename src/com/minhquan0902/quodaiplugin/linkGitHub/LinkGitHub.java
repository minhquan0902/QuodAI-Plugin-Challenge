package com.minhquan0902.quodaiplugin.linkGitHub;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.minhquan0902.quodaiplugin.linkGitHub.Exception.MainException;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.Commit;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.Repository;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.RepositoryLocator;
import com.minhquan0902.quodaiplugin.linkGitHub.UI.ExceptionRenderer;
import com.minhquan0902.quodaiplugin.linkGitHub.UI.LineSelection;
import com.minhquan0902.quodaiplugin.linkGitHub.URL.Factory.UrlFactory;
import com.minhquan0902.quodaiplugin.linkGitHub.URL.Factory.UrlFactoryLocator;
import com.minhquan0902.quodaiplugin.linkGitHub.URL.Handler.BrowserHandler;
import com.minhquan0902.quodaiplugin.linkGitHub.URL.Modifier.UrlModifier;
import com.minhquan0902.quodaiplugin.linkGitHub.URL.Modifier.UrlModifierProvider;
import com.minhquan0902.quodaiplugin.linkGitHub.URL.Handler.URLHandler;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

public class LinkGitHub {

    private Logger logger = Logger.getInstance(ServiceManager.getService(Plugin.class).displayName());
    private final RepositoryLocator repositoryLocator;
    private final ExceptionRenderer exceptionRenderer;
    private final UrlModifierProvider urlModifierProvider;
    private final BrowserHandler browserHandler;
    private final Project project;
    private final UrlFactoryLocator urlFactoryLocator;
    private final Preferences preferences;

    LinkGitHub(Project project) {
        this.repositoryLocator = RepositoryLocator.getInstance(project);
        this.exceptionRenderer = ExceptionRenderer.getInstance();
        this.urlModifierProvider = ServiceManager.getService(UrlModifierProvider.class);
        this.browserHandler = BrowserHandler.getInstance();

        this.urlFactoryLocator = UrlFactoryLocator.getInstance(project);
        this.project = project;
        this.preferences = Preferences.getInstance(project);
    }

    public static LinkGitHub getInstance(Project project) {
        return ServiceManager.getService(project, LinkGitHub.class);
    }

    public void openFile(@NotNull final VirtualFile file, final Commit commit, final LineSelection lineSelection) {
        handleFile(this.browserHandler, file, commit, lineSelection);
    }



    public void openCommit(@NotNull final Commit commit, @NotNull final VirtualFile file) {
        handleCommit(this.browserHandler, commit, file);
    }



    private void handleFile(
            @NotNull final URLHandler urlHandler,
            @NotNull final VirtualFile file,
            final Commit commit,
            final LineSelection lineSelection
    ) {
        try {
            final Repository repository = repositoryLocator.locate(file);
            final UrlFactory urlFactory = urlFactoryLocator.locate();

            Task.Backgroundable task = new Task.Backgroundable(project, "GitLink - opening file")
            {
                @Override
                public void run(@NotNull ProgressIndicator indicator)
                {
                    try {

                        URL url;
                        Commit selectedCommit = commit;

                        // If we have not been give a commit to open explicitly then use the current commit. We fallback
                        // to using the branch if the commit does not exist on the remote repository (unless we have
                        // been asked not to check the remote see https://github.com/ben-gibson/GitLink/issues/97
                        // for details).
                        if ((selectedCommit == null) && (!preferences.shouldCheckCommitExistsOnRemote() || repository.isCurrentCommitOnRemote())) {
                            selectedCommit = repository.currentCommit();
                        }

                        if (selectedCommit != null) {
                            url = urlFactory.createUrlToFileAtCommit(
                                    repository.remote(),
                                    repository.repositoryFileFromVirtualFile(file),
                                    selectedCommit,
                                    lineSelection
                            );
                        } else {
                            url = urlFactory.createUrlToFileOnBranch(
                                    repository.remote(),
                                    repository.repositoryFileFromVirtualFile(file),
                                    repository.currentBranch(),
                                    lineSelection
                            );
                        }

                        LinkGitHub.this.handleUrl(urlHandler, url);

                    } catch (MainException e) {
                        LinkGitHub.this.exceptionRenderer.render(e);
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            };

            task.queue();

        } catch (MainException e) {
            exceptionRenderer.render(e);
        }
    }

    private void handleCommit(@NotNull final URLHandler urlHandler, @NotNull final Commit commit, @NotNull final VirtualFile file) {
        try {
            final Repository repository = repositoryLocator.locate(file);;
            final UrlFactory urlFactory = urlFactoryLocator.locate();

            Task.Backgroundable task = new Task.Backgroundable(project, "GitLink - Opening Commit")
            {
                @Override
                public void run(@NotNull ProgressIndicator indicator)
                {
                    try {

                        final URL url = urlFactory.createUrlToCommit(repository.remote(), commit);

                        LinkGitHub.this.handleUrl(urlHandler, url);

                    } catch (MainException e) {
                        LinkGitHub.this.exceptionRenderer.render(e);
                    }
                }
            };

            task.queue();

        } catch (MainException e) {
            exceptionRenderer.render(e);
        }
    }

    private void handleUrl(@NotNull final URLHandler urlHandler, @NotNull URL url) throws MainException {
        LinkGitHub.this.logger.info(String.format("Generated URL '%s'", url.toString()));

        for (UrlModifier urlModifier : LinkGitHub.this.urlModifierProvider.modifiers()) {
            if (preferences.isModifierEnabled(urlModifier)) {
                url = urlModifier.modify(url);
                LinkGitHub.this.logger.info(String.format(
                        "Applied modifier '%s' - '%s'",
                        urlModifier.name(),
                        url.toString()
                ));
            }
        }

        LinkGitHub.this.logger.info(String.format("Handling URL with handler '%s'", urlHandler.getClass().getSimpleName()));

        urlHandler.handle(url);
    }

}
