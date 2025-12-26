package com.home.tunnel.filter;

import com.home.common.commonication.dto.CommunicationRequestContentDto;
import com.home.common.commonication.dto.CommunicationResponseContentDto;
import com.home.common.commonication.dto.CommunicationTransactionDto;
import com.home.tunnel.service.LoggingService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import tools.jackson.databind.ObjectMapper;

@AllArgsConstructor
@Component
public class CommunicationRequestResponseLoggingFilter extends OncePerRequestFilter {

  private final ObjectMapper objectMapper;
  private final LoggingService loggingService;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    var uri = request.getRequestURI();
    if (uri == null || uri.isBlank()) {
      return false;
    }

    return uri.contains("/stream/");
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    ContentCachingRequestWrapper wrappedRequest =
        new ContentCachingRequestWrapper(request, 1024 * 1024);
    ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

    filterChain.doFilter(wrappedRequest, wrappedResponse);

    String transactionId = getTransactionId(wrappedRequest);
    logRequest(wrappedRequest, transactionId);
    logResponse(wrappedResponse, transactionId);

    wrappedResponse.copyBodyToResponse();
  }

  private String getTransactionId(ContentCachingRequestWrapper wrappedRequest) {
    try {
      return objectMapper
          .readTree(
              new String(
                  wrappedRequest.getContentAsByteArray(), wrappedRequest.getCharacterEncoding()))
          .path("header")
          .path("transactionId")
          .asString();
    } catch (Exception exception) {
      logger.error("ERROR in RequestResponseLoggingFilter::getTransactionId", exception);
      throw new RuntimeException(exception);
    }
  }

  private void logRequest(ContentCachingRequestWrapper wrappedRequest, String transactionId) {
    try {
      loggingService.logRequest(
          new CommunicationTransactionDto(
              null,
              transactionId,
              wrappedRequest.getRequestURL().toString(),
              wrappedRequest.getRemoteAddr(),
              LocalDateTime.now(),
              null),
          new CommunicationRequestContentDto(
              null,
              transactionId,
              new String(
                  wrappedRequest.getContentAsByteArray(), wrappedRequest.getCharacterEncoding())));
    } catch (Exception exception) {
      logger.error("ERROR in RequestResponseLoggingFilter::logRequestToDb", exception);
      throw new RuntimeException(exception);
    }
  }

  private void logResponse(ContentCachingResponseWrapper wrappedResponse, String transactionId) {
    try {
      loggingService.logResponse(
          transactionId,
          new CommunicationResponseContentDto(
              null,
              transactionId,
              new String(
                  wrappedResponse.getContentAsByteArray(),
                  wrappedResponse.getCharacterEncoding())));
    } catch (Exception exception) {
      logger.error("ERROR in RequestResponseLoggingFilter::logResponseToDb", exception);
      throw new RuntimeException(exception);
    }
  }
}
