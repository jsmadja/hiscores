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

    @Value("${api.shmup-cookie-name}")
    private String shmupCookieName;

    @Value("${api.shmup-cookie-userid}")
    private String shmupUserId;

    @Autowired
    private PlayerService playerService;

    private boolean isDev() {
        return Boolean.parseBoolean(devMode);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Player player = Player.guest;
        Optional<Cookie> shmupCookie = isDev() ? Optional.of(new Cookie(shmupCookieName, shmupUserId)) : getShmupCookie((HttpServletRequest) request);
        if (shmupCookie.isPresent()) {
            long shmupUserId = Long.parseLong(shmupCookie.get().getValue());
            Player dbPlayer = playerService.findByShmupUserId(shmupUserId);
            if (dbPlayer != null) {
                dbPlayer.renewUpdateAt();
                playerService.update(dbPlayer);
                player = dbPlayer;
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
        return Arrays.stream(cookies).filter(c -> c.getName().equals(shmupCookieName)).findFirst();
    }
}
