package com.BarcelonaSC.BarcelonaApp.models.firebase;

/**
 * Created by Carlos on 02/02/2018.
 */

public class Count {

    long count = 1;
    long limit = 1;

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public Count(long limit) {
        this.limit = limit;
    }

    public long getCount() {
        return count;
    }

    public long increment() {
        return count++;
    }

    public void reset() {
        count = 0;
    }

    public boolean verificateLimit() {
        if (count >= limit) {
            count++;
            return true;
        } else {
            count++;
            return false;
        }

    }

}
