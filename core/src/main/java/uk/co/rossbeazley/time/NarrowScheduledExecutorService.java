package uk.co.rossbeazley.time;

import java.util.concurrent.TimeUnit;

public interface NarrowScheduledExecutorService {
    Cancelable scheduleAtFixedRate(Runnable command, long period, TimeUnit unit);

    interface Cancelable {
        void cancel();
    }
}