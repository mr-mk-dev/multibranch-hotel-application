package me.manishcodes.hotelapplication.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.awt.font.ImageGraphicAttribute;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        String traceId = (String) request.getAttribute("traceId");
        ErrorResponse error = new ErrorResponse(LocalDateTime.now()
                , 500, "Internal server error: " + ex.getMessage(),
                request.getRequestURI(), traceId);
        logger.error("[TraceID: {}] Unhandled Exception: {}",
                traceId, ex.getMessage(), ex);
        return ResponseEntity.status(500).body(error);
    }

    @ExceptionHandler(HotelAppException.class)
    public ResponseEntity<ErrorResponse> handleHotelAppException(HotelAppException ex, HttpServletRequest request) {
        String traceId = (String) request.getAttribute("traceId");
        ErrorResponse error = new ErrorResponse(LocalDateTime.now()
                , ex.getStatusCode(), ex.getMessage(),
                request.getRequestURI(), traceId);
        logger.error("[TraceID: {}] {}", traceId, ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleHotelNotFound(HotelNotFoundException ex, HttpServletRequest request) {
        String traceId = (String) request.getAttribute("traceId");
        ErrorResponse errorResponse =
                new ErrorResponse(LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Unable to " + "find hotel : " + ex.getMessage(), request.getRequestURI(), traceId);
        logger.error("[TraceID: {}] HotelNotFound Exception: {}",
                traceId, ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorResponse);
    }

    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<ErrorResponse> handleImageUpload(ImageUploadException ex, HttpServletRequest request) {
        String traceId = (String) request.getAttribute("traceId");
        ErrorResponse errorResponse =
                new ErrorResponse(LocalDateTime.now(),
                        HttpStatus.SERVICE_UNAVAILABLE.value(),
                        ex.getMessage() + " Reason : " + ex.getCause(), request.getRequestURI(), traceId);
        logger.error("[TraceID: {}] ImageUpload Exception: {}",
                traceId, ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE.value()).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        String traceId = (String) request.getAttribute("traceId");
        ErrorResponse errorResponse =
                new ErrorResponse(LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage() + " Reason " + ex.getCause(), request.getRequestURI(), traceId);
        logger.error("[TraceID: {}] UserNotFound Exception: {}",
                traceId, ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        String traceId = (String) request.getAttribute("traceId");
        ErrorResponse errorResponse =
                new ErrorResponse(LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage() + " Cause : " + ex.getCause(), request.getRequestURI(), traceId);
        logger.error("[TraceID: {}] Data Integrity Violation  " +
                "Exception: {}", traceId, ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorResponse);
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessResource(DataAccessResourceFailureException ex, HttpServletRequest request) {
        String traceId = (String) request.getAttribute("traceId");
        ErrorResponse errorResponse =
                new ErrorResponse(LocalDateTime.now(),
                        HttpStatus.SERVICE_UNAVAILABLE.value(),
                        ex.getMessage() + " Cause : " + ex.getCause(), request.getRequestURI(), traceId);
        logger.error("[TraceID: {}] Data Access Resource Failure " +
                "Exception {}", traceId, ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE.value()).body(errorResponse);
    }
}
