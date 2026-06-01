package com.kop.api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class RequestHttpFilter extends OncePerRequestFilter {
    @Value("${spring.application.api-key:}")
    private String apiKey;

    private static final String X_API_KEY_HEADER = "X-API-Key";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Skip API key validation for health check and actuator endpoints
        String path = request.getRequestURI();
        if (isPublicEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // If API key is not configured (development mode), allow all requests
        if (apiKey == null || apiKey.trim().isEmpty()) {
            logger.warn("API key not configured. Allowing request in development mode.");
            filterChain.doFilter(request, response);
            return;
        }

        // Get X-API-Key from header
        String requestApiKey = request.getHeader(X_API_KEY_HEADER);

        // Validate API key
        if (requestApiKey == null || requestApiKey.trim().isEmpty()) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, 
                "Missing API key", "API_KEY_MISSING", 
                "X-API-Key header is required");
            return;
        }

        if (!requestApiKey.equals(apiKey)) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, 
                "Invalid API key", "API_KEY_INVALID", 
                "The provided API key is invalid");
            return;
        }

        // API key is valid, proceed with the request
        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String path) {
        return path.equals("/actuator/health") || 
               path.equals("/actuator") ||
               path.startsWith("/actuator/health") ||
               path.equals("/error");
    }

    private void sendErrorResponse(HttpServletResponse response, int status, 
                                   String error, String code, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("status", status);
        errorResponse.put("error", error);
        errorResponse.put("code", code);
        errorResponse.put("message", message);
        errorResponse.put("path", "");  // Will be filled by framework if needed

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
