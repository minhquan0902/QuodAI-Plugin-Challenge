package com.minhquan0902.quodaiplugin.linkGitHub.UI.Extensions;

import com.intellij.ide.SelectInContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.minhquan0902.quodaiplugin.linkGitHub.LinkGitHub;
import com.minhquan0902.quodaiplugin.linkGitHub.Preferences;

public class BrowserSelectInTarget implements com.intellij.ide.SelectInTarget {

    public boolean canSelect(SelectInContext context)
    {
        return Preferences.getInstance(context.getProject()).isEnabled();
    }


    public void selectIn(SelectInContext context, boolean requestFocus)
    {
        Project project  = context.getProject();
        VirtualFile file = context.getVirtualFile();

        LinkGitHub.getInstance(project).openFile(file, null, null);
    }


    public String getToolWindowId()
    {
        return null;
    }


    public String getMinorViewId()
    {
        return null;
    }


    public float getWeight()
    {
        return 0;
    }


    public String toString()
    {
        return "Browser (GitLink)";
    }
}
