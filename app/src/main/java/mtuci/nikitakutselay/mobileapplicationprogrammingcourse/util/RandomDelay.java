package mtuci.nikitakutselay.mobileapplicationprogrammingcourse.util;

import java.util.Random;

public class RandomDelay {
    private static final long MIN_DELAY = 200L;
    private static final long MAX_DELAY = 1600L;

    private long minDelay;
    private long maxDelay;
    private Random generator = new Random(System.currentTimeMillis());

    public RandomDelay() {
        minDelay = MIN_DELAY;
        maxDelay = MAX_DELAY;
    }

    public RandomDelay(long minDelay, long maxDelay) {
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
    }

    public long getDelay() {
        return minDelay + ((long)(generator.nextDouble() * (maxDelay - minDelay)));
    }
}
