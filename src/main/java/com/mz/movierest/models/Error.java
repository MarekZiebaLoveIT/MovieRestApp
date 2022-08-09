package com.mz.movierest.models;

import java.time.LocalDateTime;
import java.util.List;

public record Error(List<String> message,
                    LocalDateTime timestamp,
                    Integer status,
                    String error) {
}
