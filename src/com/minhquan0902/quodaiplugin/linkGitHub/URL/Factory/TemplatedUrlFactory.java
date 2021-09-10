package com.minhquan0902.quodaiplugin.linkGitHub.URL.Factory;

import com.intellij.serviceContainer.NonInjectable;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.Branch;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.Commit;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.Exception.RemoteException;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.File;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.Remote;
import com.minhquan0902.quodaiplugin.linkGitHub.UI.LineSelection;
import com.minhquan0902.quodaiplugin.linkGitHub.URL.Factory.Exception.UrlFactoryException;
import com.minhquan0902.quodaiplugin.linkGitHub.URL.Substitution.Exception.SubstitutionProcessorException;
import com.minhquan0902.quodaiplugin.linkGitHub.URL.Substitution.URLTemplateProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.net.URL;

public abstract class TemplatedUrlFactory implements UrlFactory {

    private final URLTemplateProcessor urlTemplateProcessor;
    private final String fileAtBranchUrlTemplate;
    private final String fileAtCommitUrlTemplate;
    private final String commitUrlTemplate;

    public TemplatedUrlFactory(
            @NotNull final String fileAtBranchUrlTemplate,
            @NotNull final String fileAtCommitUrlTemplate,
            @NotNull final String commitUrlTemplate
    ) {
        this.urlTemplateProcessor    = URLTemplateProcessor.getInstance();
        this.fileAtBranchUrlTemplate = fileAtBranchUrlTemplate;
        this.fileAtCommitUrlTemplate = fileAtCommitUrlTemplate;
        this.commitUrlTemplate       = commitUrlTemplate;
    }

    @NonInjectable
    public TemplatedUrlFactory(
            @NotNull final URLTemplateProcessor processor,
            @NotNull final String fileAtBranchUrlTemplate,
            @NotNull final String fileAtCommitUrlTemplate,
            @NotNull final String commitUrlTemplate
    ) {
        this.urlTemplateProcessor    = processor;
        this.fileAtBranchUrlTemplate = fileAtBranchUrlTemplate;
        this.fileAtCommitUrlTemplate = fileAtCommitUrlTemplate;
        this.commitUrlTemplate       = commitUrlTemplate;
    }

    @Override
    public URL createUrlToCommit(@NotNull final Remote remote, @NotNull final Commit commit) throws UrlFactoryException, RemoteException {
        try {
            return urlTemplateProcessor.process(commitUrlTemplate, remote, commit, null, null);
        } catch (SubstitutionProcessorException e) {
            throw UrlFactoryException.cannotCreateUrl(e.getMessage());
        }
    }

    @Override
    public URL createUrlToFileOnBranch(
            @NotNull final Remote remote,
            @NotNull final File file,
            @NotNull final Branch branch,
            @Nullable final LineSelection lineSelection
    ) throws UrlFactoryException, RemoteException {
        try {
            return urlTemplateProcessor.process(fileAtBranchUrlTemplate, remote, branch, file, lineSelection);
        } catch (SubstitutionProcessorException e) {
            throw UrlFactoryException.cannotCreateUrl(e.getMessage());
        }
    }

    @Override
    public URL createUrlToFileAtCommit(
            @NotNull final Remote remote,
            @NotNull final File file,
            @NotNull final Commit commit,
            @Nullable final LineSelection lineSelection
    ) throws UrlFactoryException, RemoteException {
        try {
            return urlTemplateProcessor.process(fileAtCommitUrlTemplate, remote, commit, file, lineSelection);
        } catch (SubstitutionProcessorException e) {
            throw UrlFactoryException.cannotCreateUrl(e.getMessage());
        }
    }
}
