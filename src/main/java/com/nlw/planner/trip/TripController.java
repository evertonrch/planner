package com.nlw.planner.trip;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<Trip> getTrip(@PathVariable("id") UUID tripId) {
        Optional<Trip> tripNullable = this.repository.findById(tripId);

        return tripNullable.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    private URI createUri(UUID id, UriComponentsBuilder uriBuilder) {
        return uriBuilder.path("/trips/{tripId}")
                .buildAndExpand(id)
                .toUri();
    }
}
