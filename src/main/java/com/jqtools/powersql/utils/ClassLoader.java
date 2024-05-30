package com.jqtools.powersql.utils;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;

public class ClassLoader extends URLClassLoader {

    public ClassLoader (URL[] urls)
    {
        super (urls);
    }

    public void addFile (String path) throws MalformedURLException
    {
    	addURL(new File(path).toURI().toURL());
    }
}
