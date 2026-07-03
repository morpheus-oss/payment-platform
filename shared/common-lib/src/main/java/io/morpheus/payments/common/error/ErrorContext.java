package io.morpheus.payments.common.error;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Immutable diagnostic context associated with a platform error.
 *
 * <p>The context is intended for structured logging, tracing and observability. It should never
 * contain secrets or personally identifiable information.
 */
public record ErrorContext(Map<String, Object> attributes) {

  public static final ErrorContext EMPTY = new ErrorContext(Map.of());

  public ErrorContext {
    Objects.requireNonNull(attributes, "attributes must not be null");
    attributes = Collections.unmodifiableMap(new LinkedHashMap<>(attributes));
  }

  public static ErrorContext empty() {
    return EMPTY;
  }

  public static ErrorContext of(String key, Object value) {
    Objects.requireNonNull(key, "key must not be null");

    return new ErrorContext(Map.of(key, value));
  }
}
