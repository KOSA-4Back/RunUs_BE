package com.fourback.runus.domains.members.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourback.runus.domains.members.domain.Member;
import com.fourback.runus.domains.members.service.MemberService;
import com.fourback.runus.global.security.util.JwtUtil;

@RestController
@RequestMapping("/api/members")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Member member) {
        logger.info("Register endpoint called with email: {}", member.getEmail());
        member.setCreatedAt(LocalDateTime.now());
        member.setUpdatedAt(LocalDateTime.now());
        memberService.registerMember(member);
        logger.info("User registered successfully with email: {}", member.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body("/register-success.html");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Member member) {
        logger.info("Login endpoint called with email: {}", member.getEmail());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword()));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());
            logger.info("User logged in successfully, token generated for email: {}", member.getEmail());
            
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            logger.warn("Login failed for email: {}, reason: user not found", member.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (BadCredentialsException e) {
            logger.warn("Login failed for email: {}, reason: bad credentials", member.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (AuthenticationException e) {
            logger.error("Authentication error for email: {}", member.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam("email") String email) {
        logger.info("Check email endpoint called with email: {}", email);
        boolean isTaken = memberService.isEmailTaken(email);
        return ResponseEntity.ok(isTaken);
    }
}
