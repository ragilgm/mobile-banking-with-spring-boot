package com.finalproject.ragil.finalproject.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.finalproject.ragil.finalproject.config.MyUserDetailServices;
@Component
public class RequestFilter extends OncePerRequestFilter {

	@Autowired
	private MyUserDetailServices myuserDetailservices;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String username = null, sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					username = cookie.getValue();
//					System.out.println("username adalah " + username);
				}
				if (cookie.getName().equals("JSESSIONID")) {
					sessionID = cookie.getValue();
//					System.out.println("session anda adalah " + sessionID);
				}
			}
		}
		
		if (username != null && sessionID!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.myuserDetailservices.loadUserByUsername(username);
			if(username.equals(userDetails.getUsername())) {
				System.out.println("called");
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				System.out.println(authenticationToken);
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}

		filterChain.doFilter(request, response);
	}

}
