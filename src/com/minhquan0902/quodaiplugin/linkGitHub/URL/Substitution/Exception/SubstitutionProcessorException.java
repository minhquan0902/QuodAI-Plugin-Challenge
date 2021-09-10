package com.minhquan0902.quodaiplugin.linkGitHub.URL.Substitution.Exception;

import com.minhquan0902.quodaiplugin.linkGitHub.Exception.MainException;
import org.jetbrains.annotations.NotNull;

public class SubstitutionProcessorException extends MainException {
    private SubstitutionProcessorException(@NotNull final String message)
    {
        super(message);
    }

    public static SubstitutionProcessorException cannotCreateUrlFromTemplate(@NotNull final String template)
    {
        return new SubstitutionProcessorException(
                String.format("Could not create a valid URL using the template (%s)", template)
        );
    }
}
