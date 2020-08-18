package com.shmup.hiscores.filters;

import com.shmup.hiscores.models.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static org.mockito.Mockito.verify;

public class UserFilterTest {

    private UserFilter userFilter = new UserFilter();

    @Test
    public void should_return_guest_if_no_shmup_cookie() throws IOException, ServletException {
        ServletRequest request = Mockito.mock(HttpServletRequest.class);
        ServletResponse response = Mockito.mock(ServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);
        userFilter.doFilter(request, response, filterChain);

        verify(request).setAttribute("player", Player.guest);
    }

}