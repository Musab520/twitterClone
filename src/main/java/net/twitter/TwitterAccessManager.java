package net.twitter;

import io.javalin.core.security.AccessManager;
import io.javalin.core.security.RouteRole;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.Handler;
import io.javalin.http.HttpCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import net.twitter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static net.twitter.provider.JwtParserProvider.withJwtParser;

public class TwitterAccessManager implements AccessManager {

    public static TwitterAccessManager instance = null;

    public static TwitterAccessManager getInstance() {
        if (instance == null) {
            instance = new TwitterAccessManager();
        }
        return instance;
    }

    @Override
    public void manage(Handler handler, Context ctx, Set<RouteRole> routeRoles) throws Exception {

        String accessToken = ctx.sessionAttribute("accessToken");
        String idToken = ctx.sessionAttribute("idToken");

        if (accessToken == null) {
            try {
                String[] tokensFromHeaders = getTokensFromHeaders(ctx);
                accessToken = tokensFromHeaders[0];
                idToken = tokensFromHeaders[1];
                ctx.sessionAttribute("user_id", getUserIdFromIdToken(idToken));

            } catch (Exception ex) {
                accessToken = null;
                idToken = null;
                ctx.sessionAttribute("user_id", null);
            }
        }

        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(idToken)) {
            if (!ctx.path().startsWith("/login") && !ctx.path().startsWith("/callback")) {
                ctx.redirect("/login?redirectPath=" + ctx.path(), HttpCode.TEMPORARY_REDIRECT.getStatus());
            }
        }
        handler.handle(ctx);
    }

    private String[] getTokensFromHeaders(Context ctx) {
        String accessHeader = ctx.headerAsClass("Authorization", String.class).allowNullable().get();
        String userHeader = ctx.headerAsClass("User", String.class).allowNullable().get();
        String[] toReturn = {null, null};
        if (!StringUtils.isEmpty(accessHeader)) {
            toReturn[0] = accessHeader.substring(6).strip().trim();
            toReturn[1] = userHeader.strip().trim();
        }
        return toReturn;
    }

    private boolean validateAccessToken(String accessToken) {
        Jws<Claims> claims = withJwtParser(parser -> parser.parseClaims(accessToken));
        Date expiry = Date.from(Instant.ofEpochSecond(claims.getBody().get("exp", Long.class)));
        return expiry.after(new Date());
    }

    private String getUserIdFromIdToken(String idToken) {
        Jws<Claims> userClaims = withJwtParser(parser -> parser.parseClaims(idToken));
        return userClaims.getBody().getSubject();
    }
}
