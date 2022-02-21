package dse_0207.iss_service.EventAggregation.Aggregators;

import dse_0207.iss_service.Event.Event;
import dse_0207.iss_service.EventAggregation.EventAggregation;
import dse_0207.iss_service.Rest.RestPublisher;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public abstract class Aggregator implements PropertyChangeListener {
  protected PropertyChangeSupport _support;
  protected EventAggregation aggregation;
  protected Event aggregatedData;

  Aggregator(EventAggregation aggregation) {
    this._support = new PropertyChangeSupport(this);
    setAggregation(aggregation);
  }

  protected void addPropertyChangeListener(PropertyChangeListener pcl) {
    _support.addPropertyChangeListener(pcl);
  }

  protected Event getAggregatedData() {
    return aggregatedData;
  }

  public void performAggregation(List<Event> events) {
    this.setAggregatedData(this.aggregation.aggregate(events));
  }

  protected void setAggregatedData(Event aggregatedData) {
    _support.firePropertyChange("Simulator", this.aggregatedData, aggregatedData);
  }

  public void setAggregation(EventAggregation aggregation) {
    this.aggregation = aggregation;
  }

  public void propertyChange(PropertyChangeEvent e) {
    this.aggregatedData = (Event) e.getNewValue();
    try {
      RestPublisher.publishObservation(this.getAggregatedData());
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

}
