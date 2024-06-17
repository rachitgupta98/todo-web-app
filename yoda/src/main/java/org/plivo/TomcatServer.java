package org.plivo;

import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http2.Http2Protocol;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.plivo.app.ApplicationListener;
import org.plivo.filter.CorsFilter;
import org.plivo.scheduler.TaskRecurrenceScheduler;
import org.plivo.servlets.TodoTaskServlet;

@Slf4j
public class TomcatServer {
    private static final String DEFAULT_SERVLET = "DEFAULT";
    private static final String CONTEXT_PATH = "/plivo";
    private static final String DISPLAY_NAME = "plivo";
    private static final String MAX_THREADS = "maxThreads";
    private static final String ACCEPT_COUNT = "acceptCount";
    private static final String MIN_SPARE_THREADS = "minSpareThreads";

    public static Tomcat create(int port) {
        var scheduler = new TaskRecurrenceScheduler();
        scheduler.init();
        var tomcat = new Tomcat();
        var connector = new Connector();
        connector.setPort(port);
        setAdminConnectorAttributes(connector);
        tomcat.getService().addConnector(connector);


        var webappDirLocation = ".";
        var absolutePath = new File(webappDirLocation).getAbsolutePath();
        var context = tomcat.addContext(CONTEXT_PATH, absolutePath);
        Tomcat.addServlet(context, DEFAULT_SERVLET, new DefaultServlet());
        context.addServletMappingDecoded("/", DEFAULT_SERVLET);

        Tomcat.addServlet(context, "TodoServlet", new TodoTaskServlet());
        context.addServletMappingDecoded("/api/task", "TodoServlet");
        applyFilter(context);
        context.setDisplayName(DISPLAY_NAME);
        context.addLifecycleListener(new Tomcat.FixContextListener());
        context.addApplicationListener(ApplicationListener.class.getName());

        return tomcat;
    }

    private static void setAdminConnectorAttributes(Connector connector) {
        connector.setProperty(MAX_THREADS, "10");
        connector.setProperty(ACCEPT_COUNT, "20");
        connector.setProperty(MIN_SPARE_THREADS, "2");
        connector.addUpgradeProtocol(new Http2Protocol());
        connector.setProperty("keepAliveTimeout", "-1");
        connector.setProperty("maxKeepAliveRequests", "-1");
        connector.setProperty("connectionTimeout", "20000");
    }

    private static void applyFilter(Context context) {
        var corsFilter = new FilterDef();
        corsFilter.setFilterName("CorsFilter");
        corsFilter.setFilterClass(CorsFilter.class.getName());
        var filterMap = new FilterMap();
        filterMap.setFilterName("CorsFilter");
        filterMap.addURLPattern("/*");
        context.addFilterDef(corsFilter);
        context.addFilterMap(filterMap);
    }
}
