package com.myproject.jobportal.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.myproject.jobportal.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException{
		
		String authHeader=request.getHeader("Authorization");
		String token=null;
		String email=null;
		
		if(authHeader !=null && authHeader.startsWith("Bearer")) {
			token=authHeader.substring(7);
			try {
				email=jwtUtil.extractEmail(token);
				System.out.println("Extracted email from token " + email);
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("Token parsing error "+ e.getMessage());
			}
		}
		
		if(email !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails=customUserDetailsService.loadUserByUsername(email);
			if(jwtUtil.validateToken(token)) {
				UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		
		filterChain.doFilter(request, response);
		
	}
}
