package com.minhquan0902.quodaiplugin.linkGitHub.URL.Factory;

import com.intellij.serviceContainer.NonInjectable;
import com.minhquan0902.quodaiplugin.linkGitHub.Git.RemoteHost;
import com.minhquan0902.quodaiplugin.linkGitHub.URL.Substitution.URLTemplateProcessor;
import org.jetbrains.annotations.NotNull;

public class GitHubUrlFactory extends TemplatedUrlFactory {
    public GitHubUrlFactory() {
        super(
                "{remote:url}/blob/{branch}/{file:path}/{file:name}#L{line:start}-L{line:end}",
                "{remote:url}/blob/{commit}/{file:path}/{file:name}#L{line:start}-L{line:end}",
                "{remote:url}/commit/{commit}"
        );
    }

    @NonInjectable
    public GitHubUrlFactory(@NotNull final URLTemplateProcessor processor) {
        super(
                processor,
                "{remote:url}/blob/{branch}/{file:path}/{file:name}#L{line:start}-L{line:end}",
                "{remote:url}/blob/{commit}/{file:path}/{file:name}#L{line:start}-L{line:end}",
                "{remote:url}/commit/{commit}"
        );
    }

    @Override
    public boolean supports(@NotNull final RemoteHost host) {
        return host.isGitHub();
    }
}
