package com.shmup.hiscores.filters;

import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.services.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserFilterTest {

    @InjectMocks
    private UserFilter userFilter;

    @Mock
    private PlayerService playerService;

    @Mock(extraInterfaces = HttpServletRequest.class)
    private ServletRequest request;

    @Mock
    private FilterChain filterChain;

    @Mock
    private ServletResponse response;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(userFilter, "shmupCookieName", "shmupCookieName");
    }

    @Test
    public void should_set_guest_player_request_attribute_if_no_shmup_cookie() throws IOException, ServletException {
        userFilter.doFilter(request, response, filterChain);

        verify(request).setAttribute("player", Player.guest);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void should_set_real_player_request_attribute_if_there_is_valid_shmup_cookie() throws IOException, ServletException {
        Player player = mock(Player.class);
        Cookie[] cookies = new Cookie[]{new Cookie("shmupCookieName", "1")};
        when(((HttpServletRequest) request).getCookies()).thenReturn(cookies);
        when(playerService.findByShmupUserId(1L)).thenReturn(player);

        userFilter.doFilter(request, response, filterChain);

        verify(player).renewUpdateAt();
        verify(playerService).update(player);
        verify(request).setAttribute("player", player);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void should_set_guest_player_request_attribute_if_there_is_valid_shmup_cookie_but_player_does_not_exist() throws IOException, ServletException {
        Cookie[] cookies = new Cookie[]{new Cookie("shmupCookieName", "1")};
        when(((HttpServletRequest) request).getCookies()).thenReturn(cookies);

        userFilter.doFilter(request, response, filterChain);

        verify(request).setAttribute("player", Player.guest);
        verify(filterChain).doFilter(request, response);
    }

}