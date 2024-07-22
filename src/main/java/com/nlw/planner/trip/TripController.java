package com.nlw.planner.trip;

import com.nlw.planner.activitie.ActivityDetailResponse;
import com.nlw.planner.activitie.ActivityRequest;
import com.nlw.planner.activitie.ActivityResponse;
import com.nlw.planner.activitie.ActivityService;
import com.nlw.planner.link.LinkDetailResponse;
import com.nlw.planner.link.LinkRequest;
import com.nlw.planner.link.LinkResponse;
import com.nlw.planner.link.LinkService;
import com.nlw.planner.participant.ParticipantDetailsResponse;
import com.nlw.planner.participant.ParticipantRequest;
import com.nlw.planner.participant.ParticipantResponse;
import com.nlw.planner.participant.ParticipantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
@AllArgsConstructor
public class TripController {

    private final TripRepository repository;
    private final ParticipantService participantService;
    private final ActivityService activityService;
    private final LinkService linkService;

    @PostMapping()
    public ResponseEntity<TripResponse> createTrip(@RequestBody TripRequest request, UriComponentsBuilder uriBuilder) {
        Trip tripSaved = this.repository.save(new Trip(request));
        this.participantService.registerParticipantsToEvent(request.guestEmails(), tripSaved);

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

        if (tripNullable.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Trip trip = tripNullable.get();
        trip.setDestination(request.destination());
        trip.setStartsAt(parseToLocalDateTime(request.startsAt()));
        trip.setEndsAt(parseToLocalDateTime(request.endsAt()));
        this.repository.save(trip);

        return ResponseEntity.ok(trip);
    }

    @GetMapping("/{tripId}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID tripId) {
        Optional<Trip> tripNullable = this.repository.findById(tripId);

        if (tripNullable.isPresent()) {
            Trip trip = tripNullable.get();
            trip.setConfirmed(true);

            this.repository.save(trip);

            /*
               Dispara confirmaçao para convidados da viagem.
               Sera implementando posteriormente.
             */
            this.participantService.sendConfirmationToParticipants(tripId);
            return ResponseEntity.ok(trip);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantResponse> inviteParticipant(@PathVariable UUID id,
                                                                 @RequestBody ParticipantRequest request) {

        Optional<Trip> tripNullable = this.repository.findById(id);

        if (tripNullable.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Trip trip = tripNullable.get();
        var participantResponse = this.participantService
                .registerParticipantToEvent(request.email(), trip);

        if (trip.isConfirmed()) {
            this.participantService.sendConfirmationToParticipant(request.email());
        }

        return ResponseEntity.ok(participantResponse);
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantDetailsResponse>> getAllParticipants(@PathVariable UUID id) {
        var participants = this.participantService.getAllParticipantsFromTrip(id)
                .stream()
                .map(p -> new ParticipantDetailsResponse(p.getName(), p.getEmail(), p.isConfirmed()))
                .toList();

        return participants.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(participants);
    }

    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID id,
                                                             @RequestBody ActivityRequest request) {

        Optional<Trip> tripNullable = this.repository.findById(id);

        if(tripNullable.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var trip = tripNullable.get();
        var activityResponse = this.activityService.registerActivity(request, trip);
        return ResponseEntity.ok(activityResponse);
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityDetailResponse>> getAllActivities(@PathVariable UUID id) {
        var activities = this.activityService.getAllActivitiesFromId(id);
        return activities.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(activities);
    }

    @PostMapping("/{id}/links")
    public ResponseEntity<LinkResponse> registerLink(@PathVariable UUID id,
                                                     @RequestBody LinkRequest request) {

        Optional<Trip> tripNullable = this.repository.findById(id);

        if(tripNullable.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var trip = tripNullable.get();
        var linkResponse = this.linkService.registerLink(request, trip);
        return ResponseEntity.ok(linkResponse);
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkDetailResponse>> getAllLinks(@PathVariable UUID id) {
        var links = this.linkService.getAllLinksFromTrip(id);
        return links.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(links);
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
