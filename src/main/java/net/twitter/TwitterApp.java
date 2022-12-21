package net.twitter;

import io.javalin.Javalin;
import io.javalin.core.validation.JavalinValidation;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.json.JavalinJackson;
import io.javalin.plugin.rendering.vue.JavalinVue;
import net.twitter.dto.UserDto;
import net.twitter.infra.App;
import net.twitter.infra.Configuration;
import net.twitter.provider.JdbiProvider;
import net.twitter.route.Routes;
import net.twitter.service.UserService;
import org.apache.commons.lang3.StringUtils;
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
import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.post;
import static net.twitter.infra.Auth0Plugin.useAuth0Plugin;

public class TwitterApp implements App {

    private static final Logger log = LoggerFactory.getLogger(App.class);
    private Javalin javalin;

    public void init(int port) {
        setupFlyway();
        setupJdbi();
        configureJavalin(port);
        setupJavalinVue();
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

            config.jsonMapper(new JavalinJackson());
            config.addStaticFiles(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/public";
                staticFiles.location = Location.CLASSPATH;
                staticFiles.precompress = false;
                staticFiles.aliasCheck = null;
                staticFiles.skipFileFunction = req -> false;
            });
            config.enableWebjars();
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
            config.accessManager(TwitterAccessManager.getInstance());
            useAuth0Plugin(config, auth0config -> {
                auth0config.setClientId(Configuration.instance().getAuth0ClientId());
                auth0config.setClientSecret(Configuration.instance().getAuth0ClientSecret());
                auth0config.setDomain(Configuration.instance().getAuth0Domain());
                auth0config.setScopes(Configuration.instance().getRequiredScope());
                auth0config.setAuth0Callback((token, jwsClaims) -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(jwsClaims.getSubject());
                    userDto.setEmail(jwsClaims.get("email", String.class));
                    userDto.setUsername(jwsClaims.get("email", String.class).split("@")[0]);
                    if (UserService.getInstance().findUser(jwsClaims.getSubject()) == null) {
                        UserService.getInstance().addUser(userDto);
                    }
                    UserService.getInstance().updateUser(jwsClaims.getSubject(), userDto);
                });
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

    private void setupJavalinVue() {
        if (!StringUtils.isEmpty(System.getProperty("production"))) {
            JavalinVue.rootDirectory(config -> {
                config.externalPath("vue");
            });
        }
        JavalinVue.stateFunction = ctx -> {
            HashMap<String, String> userDetails = new HashMap<>();
            userDetails.put("user_id", ctx.sessionAttribute("user_id"));
            userDetails.put("email", ctx.sessionAttribute("email"));

            UserDto currentUser = UserService.getInstance().findUser(ctx.sessionAttribute("user_id"));
            return Map.of("userDetails", userDetails);
        };
    }

}
