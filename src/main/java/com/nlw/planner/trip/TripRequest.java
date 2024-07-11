package com.nlw.planner.trip;

import java.util.List;

public record TripRequest(String destination,
                          String startAt,
                          String endsAt,
                          List<String> guestEmails,
                          String ownerName,
                          String ownerEmail) {
}
