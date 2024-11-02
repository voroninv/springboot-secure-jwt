package com.base.jwt.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSqlException(SQLException ex) {
        LOGGER.error(ex.getClass().getSimpleName() + ": " + ex.getMessage());

        return new ResponseEntity<>("Sql exception.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        LOGGER.error(ex.getClass().getSimpleName() + ": " + ex.getMessage());

        return new ResponseEntity<>("Bad credentials exception.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<Object> handleExistingUserException(ExistingUserException ex) {
        LOGGER.error(ex.getClass().getSimpleName() + ": " + ex.getMessage());

        return new ResponseEntity<>("User already exists.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UsernameNotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<Object> handleUsernameNotFoundException(RuntimeException ex) {
        LOGGER.error(ex.getClass().getSimpleName() + ": " + ex.getMessage());

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<Object> handleAccountStatusException(AccountStatusException ex) {
        LOGGER.error(ex.getClass().getSimpleName() + ": " + ex.getMessage());

        return new ResponseEntity<>("The account is locked.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        LOGGER.error(ex.getClass().getSimpleName() + ": " + ex.getMessage());

        return new ResponseEntity<>("You are not authorized to access this resource.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        LOGGER.error(ex.getClass().getSimpleName() + ": " + ex.getMessage());

        return new ResponseEntity<>("Could not execute statement.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleSignatureException(SignatureException ex) {
        LOGGER.error(ex.getClass().getSimpleName() + ": " + ex.getMessage());

        return new ResponseEntity<>("The JWT signature is invalid.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
        LOGGER.error(ex.getClass().getSimpleName() + ": " + ex.getMessage());

        return new ResponseEntity<>("The JWT token has expired.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        ex.printStackTrace();
        LOGGER.error(ex.getClass().getSimpleName() + ": " + ex.getMessage());

        return new ResponseEntity<>("Unknown internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}