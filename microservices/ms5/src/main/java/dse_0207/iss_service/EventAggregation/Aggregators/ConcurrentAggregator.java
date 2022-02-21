package dse_0207.iss_service.EventAggregation.Aggregators;

import dse_0207.iss_service.Event.Event;

import java.util.List;

public class ConcurrentAggregator implements Runnable {

    private Aggregator aggregator;
    private List<Event> events;

    public ConcurrentAggregator(Aggregator aggregator, List<Event> events) {
        this.aggregator = aggregator;
        this.events = events;
    }

    @Override
    public void run() {
        aggregator.performAggregation(events);
    }
}


