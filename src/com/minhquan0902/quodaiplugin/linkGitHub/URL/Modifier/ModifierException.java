package com.minhquan0902.quodaiplugin.linkGitHub.URL.Modifier;


import java.net.URL;
import com.minhquan0902.quodaiplugin.linkGitHub.Exception.MainException;
import org.jetbrains.annotations.NotNull;



public class ModifierException extends MainException {

    private ModifierException(@NotNull final  String message)
    {
        super(message);
    }

    public static ModifierException invalidUrlAfterModification(@NotNull final URL url)
    {
        return new ModifierException(String.format("The url '%s' became invalid after modification", url));
    }
}
