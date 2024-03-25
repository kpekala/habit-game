
package com.kpekala.habitgame.domain.common;

import java.time.LocalDateTime;

public record RestErrorResponse(int status, String message,
                                LocalDateTime timestamp) {}
