package me.manishcodes.hotelapplication.log;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger logging = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Generate unique ID per request
        String traceId = UUID.randomUUID().toString();
        request.setAttribute("traceId", traceId);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            // Log the exception and rethrow so GlobalExceptionHandler can handle it
            logging.error("[TraceID: {}] Exception during request: {} {}", traceId, request.getMethod(), request.getRequestURI(), ex);
            throw ex;
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            logging.info("[TraceID: {}] {} {} -> {} ({} ms)",
                    traceId,
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    duration);
        }
    }
}
