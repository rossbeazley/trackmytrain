package fakes;

import uk.co.rossbeazley.trackmytrain.android.Clock;

public class ClockStub implements Clock {

    long nextReportedTime;

    public ClockStub() {
        nextReportedTime = 0;
    }

    public void advanceBy(int i) {
        nextReportedTime+=i;
    }

    @Override
    public long time() {
        return nextReportedTime;
    }
}
