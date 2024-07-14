package com.nlw.planner.participant;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
@AllArgsConstructor
public class ParticipantController {

    private final ParticipantRepository participantRepository;

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> inviteParticipant(@PathVariable UUID id,
                                                          @RequestBody ParticipantRequest request) {

        Optional<Participant> participantNullable = this.participantRepository.findById(id);

        if(participantNullable.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Participant participant = participantNullable.get();
        participant.setConfirmed(true);
        participant.setName(request.name());

        this.participantRepository.save(participant);
        return ResponseEntity.ok(participant);
    }
}
