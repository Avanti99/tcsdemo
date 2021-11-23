package com.example.student.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.student.entities.AuthDetails;
import com.example.student.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/token")
	@ResponseBody
	public String GenerateToken(@RequestBody AuthDetails authdetails) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authdetails.getUsername(), authdetails.getPassword()));
			
		} catch (Exception e) {
			System.out.println("Invalid username or password");
			
		}
		return jwtUtil.generateToken(authdetails.getUsername());	
	}


	@PostMapping("/token")
	public String GenerateToken(@ModelAttribute AuthDetails authdetails,HttpServletRequest request,HttpServletResponse response) throws Exception {
		    try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authdetails.getUsername(), authdetails.getPassword()));
				String token=jwtUtil.generateToken(authdetails.getUsername());
				Cookie deleteServletCookie = new Cookie("Authorization", null);
				deleteServletCookie.setMaxAge(0);
				response.addCookie(deleteServletCookie);
				response.addCookie(new Cookie("Authorization", token));
			    return "redirect:/user/home";
			} catch (Exception e) {
				return "redirect:/login";
			} 	
	}
}
