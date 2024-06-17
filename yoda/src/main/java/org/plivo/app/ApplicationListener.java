package org.plivo.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ApplicationListener implements ServletContextListener {
    private Injector injector;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        injector = Guice.createInjector(Stage.PRODUCTION, new ApplicationModule());
        servletContextEvent.getServletContext().setAttribute(Injector.class.getName(), injector);
    }
}
