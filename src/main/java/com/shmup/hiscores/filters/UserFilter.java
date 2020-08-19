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

@Deprecated
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

    private boolean isDev() {
        return Boolean.parseBoolean(devMode);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Player player;
        Optional<Cookie> shmupCookie = isDev() ? Optional.of(new Cookie(SHMUP_COOKIE_NAME, ANZYMUS_SHMUP_USER_ID.toString())) : getShmupCookie((HttpServletRequest) request);
        if (shmupCookie.isEmpty()) {
            player = Player.guest;
        } else {
            long shmupUserId = Long.parseLong(shmupCookie.get().getValue());
            player = playerService.findByShmupUserId(shmupUserId);
            if (player != null) {
                player.renewUpdateAt();
                playerService.update(player);
            } else {
                player = Player.guest;
            }
        }
        request.setAttribute("player", player);
        filterChain.doFilter(request, response);
    }

    private Optional<Cookie> getShmupCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }
        return Arrays.stream(cookies).filter(c -> c.getName().equals(SHMUP_COOKIE_NAME)).findFirst();
    }
}
