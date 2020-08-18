package com.shmup.hiscores.filters;

import com.shmup.hiscores.models.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserFilterTest {

    private UserFilter userFilter = new UserFilter();

    @Mock(extraInterfaces = HttpServletRequest.class)
    private ServletRequest request;

    @Mock
    private FilterChain filterChain;

    @Mock
    private ServletResponse response;

    @Test
    public void should_return_guest_if_no_shmup_cookie() throws IOException, ServletException {
        userFilter.doFilter(request, response, filterChain);

        verify(request).setAttribute("player", Player.guest);
        verify(filterChain).doFilter(request, response);
    }

}