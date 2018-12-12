package com.BarcelonaSC.BarcelonaApp.ui.calendar.mvp;

public class AuxFinderMatch {


    long todayDate = 0;
    long dateDistancePast = 0;
    long dateDistanceFuture = 0;
    int posItem = 0;
    int posGroup = 0;
    int allItemUntilNow = 0;
    boolean wasFound = false;

    public AuxFinderMatch() {
    }

    public long getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(long todayDate) {
        this.todayDate = todayDate;
    }

    public long getDateDistancePast() {
        return dateDistancePast;
    }

    public void setDateDistancePast(long dateDistancePast) {
        this.dateDistancePast = dateDistancePast;
    }

    public long getDateDistanceFuture() {
        return dateDistanceFuture;
    }

    public void setDateDistanceFuture(long dateDistanceFuture) {
        this.dateDistanceFuture = dateDistanceFuture;
    }

    public int getPosItem() {
        return posItem;
    }

    public void setPosItem(int posItem) {
        this.posItem = posItem;
    }

    public int getPosGroup() {
        return posGroup;
    }

    public void setPosGroup(int posGroup) {
        this.posGroup = posGroup;
    }

    public int getAllItemUntilNow() {
        return allItemUntilNow;
    }

    public void setAllItemUntilNow(int allItemUntilNow) {
        this.allItemUntilNow = allItemUntilNow;
    }

    public boolean isWasFound() {
        return wasFound;
    }

    public void setWasFound(boolean wasFound) {
        this.wasFound = wasFound;
    }

    @Override
    public String toString() {
        return "AuxFinderMatch{" +
                "todayDate=" + todayDate +
                ", dateDistancePast=" + dateDistancePast +
                ", dateDistanceFuture=" + dateDistanceFuture +
                ", posItem=" + posItem +
                ", posGroup=" + posGroup +
                ", allItemUntilNow=" + allItemUntilNow +
                ", wasFound=" + wasFound +
                '}';
    }

}
