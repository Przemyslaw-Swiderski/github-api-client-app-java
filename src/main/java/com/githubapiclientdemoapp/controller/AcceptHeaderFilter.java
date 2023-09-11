package com.githubapiclientdemoapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AcceptHeaderFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    public AcceptHeaderFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (MediaType.APPLICATION_XML_VALUE.equals(request.getHeader(HttpHeaders.ACCEPT))) {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("status", HttpStatus.NOT_ACCEPTABLE.toString());
            errorDetails.put("message", "Only application/json media type is supported");

            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            objectMapper.writeValue(response.getWriter(), errorDetails);
            log.warn("Client requested response in xml format - returning HTTP 406");
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
