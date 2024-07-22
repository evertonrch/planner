package com.nlw.planner.link;

import com.nlw.planner.trip.Trip;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LinkService {

    private final LinkRepository repository;

    public LinkResponse registerLink(LinkRequest request, Trip trip) {
        Link link = new Link(request.title(), request.url(), trip);
        Link saved = this.repository.save(link);
        return new LinkResponse(saved.getId());
    }

    public List<LinkDetailResponse> getAllLinksFromTrip(UUID tripId) {
        return this.repository.findByTripId(tripId)
                .stream()
                .map(l -> new LinkDetailResponse(l.getTitle(), l.getUrl()))
                .toList();
    }


}
