package com.gvstave.mistergift.api.configuration;

import com.gvstave.mistergift.api.filter.ContentLengthFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

/**
 * .
 */
public class ApiApplicationInitializer implements WebApplicationInitializer {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        // Configure the application.configuration
        String serverRole = Optional.ofNullable(System.getProperty("server.role")).orElse("dev");
        String properties = String.format("/WEB-INF/classes/WEB-INF/%s.properties", serverRole);
        servletContext.setInitParameter("application.configuration", properties);

        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApiWebConfiguration.class);

        // Manage the lifecycle of the root application context
        servletContext.addListener(new ContextLoaderListener(rootContext));
        addSpringServlet("api", servletContext, "/*", ApiWebConfiguration.class);

        // adds spring filters
        addSpringCorsFilter("api-cors", servletContext);
        addSpringContentLengthFilter("content-length", servletContext);

        servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"))
            .addMappingForUrlPatterns(null, false, "/*");
    }

    /**
     * Adds new Spring servlet.
     *
     * @param name           The servlet name.
     * @param servletContext The servlet context.
     * @param mapping        The mapping.
     * @param configurations The configurations.
     */
    private void addSpringServlet(String name, ServletContext servletContext, String mapping, Class<?>... configurations) {

        // Create the application context of the dispatcher servlet
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(configurations);

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(name, new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(mapping);
        dispatcher.setRunAsRole("${server.role}");
        dispatcher.setAsyncSupported(true);
    }

    /**
     * Adds new Spring CORS filter.
     *
     * @param name           The filter name.
     * @param servletContext The servlet context.
     */
    private void addSpringCorsFilter(String name, ServletContext servletContext) {
        // setup of Cors filter allowing requesting endpoints
        CorsFilter corsFilter = new CorsFilter(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration
                .setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "X-Requested-With", "X-MG-AUTH"));
            corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
            corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setMaxAge((long) 3600);
            return corsConfiguration;
        });

        servletContext.addFilter(name, corsFilter).addMappingForUrlPatterns(null, false, "/*");
    }

    /**
     * Adds new Content length filter.
     *
     * @param name           The filter name.
     * @param servletContext The servlet context.
     */
    private void addSpringContentLengthFilter(String name, ServletContext servletContext) {
        servletContext.addFilter(name, new ContentLengthFilter()).addMappingForUrlPatterns(null, false, "/*");
    }

}