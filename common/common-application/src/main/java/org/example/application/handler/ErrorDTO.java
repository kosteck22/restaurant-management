package org.example.application.handler;

import java.util.Date;
import java.util.List;

public record ErrorDTO(
        String path,
        String message,
        int statusCode,
        Date timestamp
) {}
