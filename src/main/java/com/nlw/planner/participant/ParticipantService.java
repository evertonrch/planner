package com.nlw.planner.participant;

import com.nlw.planner.trip.Trip;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public void registerParticipantsToEvent(List<String> participantsToInvite, Trip trip) {
        var participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();
        this.participantRepository.saveAll(participants);
    }

    public ParticipantResponse registerParticipantToEvent(String email, Trip trip) {
        Participant participant = new Participant(email, trip);
        this.participantRepository.save(participant);
        return new ParticipantResponse(participant.getId());
    }
    public void sendConfirmationToParticipants(UUID tripId) {}

    public void sendConfirmationToParticipant(String email) {}

    public List<Participant> getAllParticipantsFromTrip(UUID id) {
        return this.participantRepository.findByTripId(id);
    }
}
