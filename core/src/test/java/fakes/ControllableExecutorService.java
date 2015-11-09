package fakes;

import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;

public class ControllableExecutorService implements NarrowScheduledExecutorService {


    public Cancelable cancelable;
    public Runnable NO_COMMAND = new Runnable() {
        @Override
        public void run() {
        }
    };
    public Runnable scheduledCommand = NO_COMMAND;


    @Override
    public Cancelable scheduleAtFixedRate(Runnable command, long period, TimeUnit unit) {
        scheduledCommand = command;
        cancelable = new Cancelable() {
            @Override
            public void cancel() {
                cancelable = null;
                scheduledCommand = NO_COMMAND;
            }
        };
        return cancelable;
    }
}
