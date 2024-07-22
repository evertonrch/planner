package com.nlw.planner.activitie;

import java.time.LocalDateTime;

public record ActivityDetailResponse(String title, LocalDateTime occursAt) {
}
