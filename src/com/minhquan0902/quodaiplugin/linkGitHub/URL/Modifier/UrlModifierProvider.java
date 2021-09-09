package com.minhquan0902.quodaiplugin.linkGitHub.URL.Modifier;

import java.util.Arrays;
import java.util.List;


public class UrlModifierProvider {

    private  List<UrlModifier> modifiers;

    public void UrlModifierProvider()
    {
        this.modifiers = Arrays.asList(
                new HttpsUrlModifier()
        );
    }

    public List<UrlModifier> modifiers()
    {
        return this.modifiers;
    }
}
