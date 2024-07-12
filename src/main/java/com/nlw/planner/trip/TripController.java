package com.nlw.planner.trip;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
@AllArgsConstructor
public class TripController {

    private final TripRepository repository;

    @PostMapping()
    public ResponseEntity<TripResponse> createTrip(@RequestBody TripRequest request, UriComponentsBuilder uriBuilder) {
        Trip tripSaved = this.repository.save(new Trip(request));

        URI uri = createUri(tripSaved.getId(), uriBuilder);
        return ResponseEntity.created(uri)
                .body(new TripResponse(tripSaved.getId(), tripSaved.getOwnerName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable("id") UUID tripId) {
        Optional<Trip> tripNullable = this.repository.findById(tripId);

        return tripNullable.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID tripId,
                                           @RequestBody TripRequest request) {
        
        Optional<Trip> tripNullable = this.repository.findById(tripId);

        if(tripNullable.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        System.out.println(request);

        Trip updatedTrip = tripNullable.get();
        updatedTrip.setDestination(request.destination());
        updatedTrip.setStartsAt(parseToLocalDateTime(request.startsAt()));
        updatedTrip.setEndsAt(parseToLocalDateTime(request.endsAt()));
        this.repository.save(updatedTrip);

        return ResponseEntity.ok(updatedTrip);
    }

    private LocalDateTime parseToLocalDateTime(String textDate) {
        return LocalDateTime.parse(textDate, DateTimeFormatter.ISO_DATE_TIME);
    }

    private URI createUri(UUID id, UriComponentsBuilder uriBuilder) {
        return uriBuilder.path("/trips/{tripId}")
                .buildAndExpand(id)
                .toUri();
    }
}
