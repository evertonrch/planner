package com.nlw.planner.participant;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    public void registerParticipantToEvent(List<String> participants, UUID tripId) {}
    public void sendConfirmationToParticipants(UUID tripId) {}
}
