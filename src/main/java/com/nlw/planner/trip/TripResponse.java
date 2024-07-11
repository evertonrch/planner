package com.nlw.planner.trip;

import java.util.UUID;

public record TripResponse(UUID tripId, String ownerName) {
}
