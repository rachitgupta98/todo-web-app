package org.plivo.app;

import static com.google.inject.Scopes.SINGLETON;

import com.google.inject.AbstractModule;
import org.plivo.filter.CorsFilter;
import org.plivo.repository.TaskRepository;
import org.plivo.scheduler.TaskRecurrenceScheduler;
import org.plivo.services.TaskService;
import org.plivo.servlets.TodoTaskServlet;

public class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {
        binder().disableCircularProxies();
        binder().requireExplicitBindings();
        binder().requireExactBindingAnnotations();
        binder().requireAtInjectOnConstructors();

        bind(TaskService.class).in(SINGLETON);
        bind(TaskRepository.class).in(SINGLETON);
        bind(ApplicationListener.class).in(SINGLETON);
        requestStaticInjection(TodoTaskServlet.class);
        requestStaticInjection(CorsFilter.class);
    }
}
