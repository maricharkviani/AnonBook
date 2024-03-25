package com.example.anonbook;

import com.google.protobuf.compiler.PluginProtos;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class Application {
    public static void main(String[] args) throws LifecycleException {

        Tomcat tomcat = new Tomcat();
        tomcat.enableNaming();
        tomcat.setPort(8080);
        tomcat.getConnector();

        String ctxPath = "/anonBook";
        String webappDir = new PluginProtos.CodeGeneratorResponse.File("src/main/webapp").getAbsolutePath();
        StandardContext ctx = (StandardContext) tomcat.addWebapp(ctxPath, webappDir);

        PluginProtos.CodeGeneratorResponse.File additionWebInfClasses = new PluginProtos.CodeGeneratorResponse.File("build/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }
}