package com.gvstave.mistergift.admin.access;

import org.joda.time.DateTime;

public class Access {

    /** The time (in seconds). */
    private DateTime nextRequestAt = new DateTime();

    /** The request count. */
    private int requestCount = 1;

    /**
     *
     * @param nextRequestAt
     */
    public Access(DateTime nextRequestAt) {
        this.nextRequestAt = nextRequestAt;
    }

    public Access() { }

    /**
     *
     * @return
     */
    public DateTime getNextRequestAt() {
        return nextRequestAt;
    }

    /**
     *
     * @param nextRequestAt
     */
    public void setNextRequestAt(DateTime nextRequestAt) {
        this.nextRequestAt = nextRequestAt;
    }

    /**
     *
     * @param requestCount
     */
    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    /**
     *
     * @return
     */
    public int getRequestCount() {
        return requestCount;
    }
}
