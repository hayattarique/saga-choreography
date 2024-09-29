package org.boot.commondtos.event;

import java.time.LocalDate;
import java.util.UUID;

public interface Event {
    UUID getEventId();
    LocalDate getDate();
}
