package com.minhquan0902.quodaiplugin.linkGitHub.URL.Modifier;

import java.net.URL;

public interface UrlModifier {
    URL modify(URL url) throws ModifierException;

    String name();
}
