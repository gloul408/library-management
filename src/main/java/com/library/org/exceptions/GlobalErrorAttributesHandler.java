package com.library.org.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
public class GlobalErrorAttributesHandler extends DefaultErrorAttributes {

  @Override
  public Map<String, Object> getErrorAttributes(ServerRequest request,
      ErrorAttributeOptions options) {
    return createError(request);
  }

  private Map<String, Object> createError(ServerRequest request) {
    Throwable exception = getError(request);
    Map<String, Object> errorAttributes = new HashMap<>();
    if (exception instanceof LibraryAPIException)
    {
      LibraryAPIException libraryAPIException = (LibraryAPIException) exception;
      errorAttributes.put("message", libraryAPIException.getMessage());
      errorAttributes.put("status", libraryAPIException.getStatus());
    } else {
      errorAttributes.put("message", exception.getMessage());
      errorAttributes.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    return errorAttributes;
  }
}
