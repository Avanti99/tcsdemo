package com.example.student.filter;

import java.io.IOException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.student.services.UserService;
import com.example.student.util.JwtUtil;


@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader=null;

		if (request.getCookies()!=null){
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals("Authorization")) {
					authorizationHeader=cookie.getValue();
					break;
				}
			  }
		}
		HttpServletRequest req = new HttpServletRequestWrapper(request) {
			@Override
			public String getRequestURI() {
			   String originalRequestURI = super.getRequestURI();
			   return originalRequestURI.replaceFirst(";[^?]*", "");
			}
		 };
		 System.out.println(req.getRequestURI());
		
		String token=null;
		String username = null;
		if (authorizationHeader != null) {
			token = authorizationHeader;
			username = jwtUtil.extractUsername(token);
		}
		
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userService.loadUserByUsername(username);
			try {
				if (jwtUtil.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					
				}
			} catch (Exception e) {
				System.out.print("User not authorized");
			}
			
			
			
		}
		filterChain.doFilter(req, response);
		

	}

}
