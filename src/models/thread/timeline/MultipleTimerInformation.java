package models.thread.timeline;

import interfaces.thread.TriggerInterface;
import models.application.ApplicationModel;

class MultipleTimerInformation<T> extends ApplicationModel {
    private final T attached_object;
    
    private final TriggerInterface trigger;
    
    private final long interval;
    
    public MultipleTimerInformation(TriggerInterface trigger, T attached_object, long interval) {
        this.trigger = trigger;
        this.attached_object = attached_object;
        this.interval = interval;
    }
    
    public TriggerInterface getTrigger() {
        return trigger;
    }
    
    public T getAttachedObject() {
        return attached_object;
    }
    
    public long getInterval() {
        return interval;
    }
}
