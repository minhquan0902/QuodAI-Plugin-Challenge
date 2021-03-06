package com.minhquan0902.quodaiplugin.linkGitHub.Git;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.Exception.BranchException;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.Exception.RemoteException;
import git4idea.GitLocalBranch;
import git4idea.GitRemoteBranch;
import git4idea.commands.Git;
import git4idea.commands.GitCommand;
import git4idea.commands.GitCommandResult;
import git4idea.commands.GitLineHandler;
import git4idea.repo.GitRemote;
import git4idea.repo.GitRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Repository {
    private final String defaultRemoteName;
    private final Branch defaultBranch;
    private final GitRepository repository;

    Repository(@NotNull final GitRepository repository, final @NotNull Branch defaultBranch, final @NotNull String defaultRemoteName)
    {
        this.repository        = repository;
        this.defaultBranch     = defaultBranch;
        this.defaultRemoteName = defaultRemoteName;
    }

    @NotNull
    Project project()
    {
        return this.repository.getProject();
    }

    @NotNull
    VirtualFile root()
    {
        return this.repository.getRoot();
    }

    /**
     * Takes a virtual file and returns a repository file
     * <p>
     * Unlike the virtual file a repository files root is from the repositories root e.g.
     * VirtualFile: /Users/Foo/Projects/acme-demo/src/bar.java
     * RepositoryFile: src/bar.java
     */
    @NotNull
    public File repositoryFileFromVirtualFile(VirtualFile file)
    {
        String pathRelativeToRepository = file.getPath().substring(this.root().getPath().length());

        return new File(pathRelativeToRepository, file.getName());
    }

    @NotNull
    public Branch currentBranch() throws RemoteException
    {
        GitLocalBranch localBranch = this.repository.getCurrentBranch();

        // If no current branch is found, or it does not exist in the origin repository and doesn't track a remote branch then
        // the branch not found exception is thrown.

        if (localBranch != null) {
            try {
                if (this.remote().hasBranch(this, localBranch)) {
                    return new Branch(localBranch.getName());
                }
            } catch (BranchException | IllegalAccessException exception) {
                GitRemoteBranch trackedBranch = localBranch.findTrackedBranch(this.repository);
                if (trackedBranch != null) {
                    return new Branch(trackedBranch.getName());
                }
            } catch (InstantiationException  e) {
                e.printStackTrace();
            }
        }

        return this.defaultBranch();
    }


    public boolean isCurrentCommitOnRemote() throws InstantiationException, IllegalAccessException {
        Commit commit = this.currentCommit();

        if (commit == null) {
            return false;
        }

        final GitLineHandler handler = new GitLineHandler(this.project(), this.root(), GitCommand.BRANCH);

        handler.addParameters("-r", "--contains", commit.hash());

        GitCommandResult result = Git.class.newInstance().runCommand(handler);

        if (!result.success()) {
            return false; // throw?
        }

        for(String output : result.getOutput()) {
            if (output.trim().startsWith(this.defaultRemoteName)) {
                return true;
            }
        }

        return false;
    }


    @Nullable
    public Commit currentCommit()
    {
        String revision = this.repository.getCurrentRevision();

        if (revision == null) {
            return null;
        }

        return new Commit(revision);
    }

    @NotNull
    public Remote remote() throws RemoteException
    {
        for (GitRemote remote : this.repository.getRemotes()) {
            if (remote.getName().equals(this.defaultRemoteName)) {
                return new Remote(remote);
            }
        }

        throw RemoteException.remoteNotFound(this.defaultRemoteName);
    }


    @NotNull
    private Branch defaultBranch()
    {
        return this.defaultBranch;
    }
}
