package com.shmup.hiscores.filters;

import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.services.PlayerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserFilter implements Filter {

    @Value("${api.dev-mode}")
    private String devMode;

    private static final Long ANZYMUS_SHMUP_USER_ID = 33489L;
    private static final String SHMUP_COOKIE_NAME = "phpbb3_axtcz_u";

    @Autowired
    private PlayerService playerService;

    private Player getPlayerFromCookie(HttpServletRequest request) {
        Optional<Cookie> shmupCookie = isDev() ? createDevCookie() : getShmupCookieFrom(request);
        if (shmupCookie.isEmpty()) {
            return Player.guest;
        }
        Player player = getConnectedPlayer(shmupCookie.get());
        player.renewUpdateAt();
        playerService.update(player);
        return player;
    }

    private Optional<Cookie> createDevCookie() {
        return Optional.of(new Cookie(SHMUP_COOKIE_NAME, ANZYMUS_SHMUP_USER_ID.toString()));
    }

    private Player getConnectedPlayer(Cookie shmupCookie) {
        Long shmupUserId = getShmupUserIdFrom(shmupCookie);
        Player player = playerService.findByShmupUserId(shmupUserId);
        if (player == null) {
            player = playerService.createNewPlayer(shmupUserId);
        }
        return player;
    }

    private Long getShmupUserIdFrom(Cookie shmupCookie) {
        return Long.parseLong(shmupCookie.getValue());
    }

    private Optional<Cookie> getShmupCookieFrom(HttpServletRequest request) {
        return Arrays.stream(request.getCookies()).filter(c -> c.getName().equals(SHMUP_COOKIE_NAME)).findFirst();
    }

    private boolean isDev() {
        return Boolean.parseBoolean(devMode);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Player player = this.getPlayerFromCookie((HttpServletRequest) request);
        request.setAttribute("player", player);
        filterChain.doFilter(request, response);
    }
}
