package net.twitter;

import io.javalin.Javalin;
import io.javalin.core.validation.JavalinValidation;
import net.twitter.infra.App;
import net.twitter.infra.Configuration;
import net.twitter.provider.JdbiProvider;
import net.twitter.route.Routes;
import org.apache.commons.lang3.time.DateUtils;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class TwitterApp implements App {

    private static final Logger log = LoggerFactory.getLogger(App.class);
    private Javalin javalin;

    public void init(int port) {
        setupFlyway();
        setupJdbi();
        configureJavalin(port);
    }

    @Override
    public void start() {
        javalin.start();
    }

    private void setupFlyway() {
        Flyway flyway = Flyway.configure().dataSource(
                Configuration.instance().getDbUrl(),
                Configuration.instance().getDbUser(),
                Configuration.instance().getDbPassword()
        ).load();
        flyway.migrate();
    }

    private void configureJavalin(int port) {
        this.javalin = Javalin.create(config -> {
            config.showJavalinBanner = true;

            config.server(() -> {
                Server server = new Server(port);
                HttpConfiguration httpConfig = new HttpConfiguration();
                httpConfig.addCustomizer(new org.eclipse.jetty.server.ForwardedRequestCustomizer());
                HttpConnectionFactory connectionFactory = new HttpConnectionFactory(httpConfig);
                ServerConnector connector = new ServerConnector(server, connectionFactory);
                connector.setPort(port);
                server.setConnectors(new ServerConnector[]{connector});
                return server;
            });
        });
        JavalinValidation.register(Date.class, (string) -> {
            try {
                return Date.from(Instant.ofEpochMilli(Long.parseLong(string)));
            } catch (NumberFormatException ex) {
                log.debug(ex.getMessage());
            }
            try {
                return DateUtils.parseDate(string, new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyy-MM-dd"});
            } catch (ParseException ex) {
                log.debug(ex.getMessage());
            }
            return null;
        });
        this.javalin.routes(new Routes());
    }

    private void setupJdbi() {
        JdbiProvider.instance().init();
    }

    @Override
    public Javalin getJavalin() {
        return javalin;
    }

}
