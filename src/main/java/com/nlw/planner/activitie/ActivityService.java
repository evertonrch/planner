package com.nlw.planner.activitie;

import com.nlw.planner.trip.Trip;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ActivityService {

    private final ActivityRepository repository;

    public ActivityResponse registerActivity(ActivityRequest request, Trip trip) {
        Activity activity = new Activity(request.title(), request.occursAt(), trip);
        Activity saved = this.repository.save(activity);
        return new ActivityResponse(saved.getId());
    }

    public List<ActivityDetailResponse> getAllActivitiesFromId(UUID tripId) {
        return repository.findByTripId(tripId)
                .stream()
                .map(a -> new ActivityDetailResponse(a.getTitle(), a.getOccursAt()))
                .toList();
    }
}
