package dse_0207.iss_service.EventAggregation;

import dse_0207.iss_service.Event.Event;

import java.util.List;

public interface EventAggregation {
    Event aggregate(List<Event> events);
}
