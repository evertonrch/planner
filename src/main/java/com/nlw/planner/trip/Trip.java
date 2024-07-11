package com.nlw.planner.trip;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "tb_trips")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(length = 150, nullable = false)
    private String destination;

    @Column(nullable = false)
    private LocalDateTime endsAt;

    @Column(nullable = false)
    private LocalDateTime startsAt;

    @Column(nullable = false)
    private boolean isConfirmed;

    @Column(length = 150, nullable = false)
    private String ownerName;

    @Column(length = 150, nullable = false)
    private String ownerEmail;

    public Trip(TripRequest request) {
        this.destination = request.destination();
        this.ownerName = request.ownerName();
        this.ownerEmail = request.ownerEmail();
        this.startsAt = LocalDateTime.parse(request.startAt(), DateTimeFormatter.ISO_DATE_TIME);
        this.endsAt = LocalDateTime.parse(request.endsAt(), DateTimeFormatter.ISO_DATE_TIME);
        this.isConfirmed = false;
    }
}
