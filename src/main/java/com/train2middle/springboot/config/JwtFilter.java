package com.train2middle.springboot.config;

import com.train2middle.springboot.model.CustomUserDetails;
import com.train2middle.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

	public static final String AUTHORIZATION = "Authorization";

	private final JwtProvider jwtProvider;
	private final UserService userService;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		logger.info("do filter...");
		Optional<String> token = getTokenFromRequest((HttpServletRequest) servletRequest);
		if (token.isPresent() && jwtProvider.validateToken(token.get())) {
			String userLogin = jwtProvider.getLoginFromToken(token.get());
			CustomUserDetails customUserDetails = userService.loadUserByUsername(userLogin);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, null);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	private Optional<String> getTokenFromRequest(HttpServletRequest request) {
		String bearer = request.getHeader(AUTHORIZATION);
		if (hasText(bearer) && bearer.startsWith("Bearer ")) {
			return Optional.of(bearer.substring(7));
		}
		return Optional.empty();
	}
}
