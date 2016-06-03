package com.entrepidea.java.objectpool;

public class Quote implements TimeValue {
    private final static int OPEN = 0;
    private final static int HIGH = 1;
    private final static int LOW = 2;
    private final static int CLOSE = 3;
    private final static int VOLUME = 4;
    private final static int AMOUNT = 5;
    private final static int CLOSE_ADJ = 6;
    private final static int WAP = 7;
    
    private final float[] values = new float[8];
   
    private long  time;
    private long sourceId;
    
    private boolean hasGaps;
    
    public Quote() {
    }
    
    public final long getTime() {
        return time;
    }
    
    public final void setTime(long time) {
        this.time = time;
    }
    
    public final float getAmount() {
        return values[AMOUNT];
    }
    
    public final float getClose() {
        return values[CLOSE];
    }
    
    public final float getClose_adj() {
        return values[CLOSE_ADJ];
    }
    
    public final float getHigh() {
        return values[HIGH];
    }
    
    public final float getLow() {
        return values[LOW];
    }
    
    public final float getOpen() {
        return values[OPEN];
    }
    
    public final long getSourceId() {
        return sourceId;
    }
    
    public final float getVolume() {
        return values[VOLUME];
    }
    
    public final float getWAP() {
        return values[WAP];
    }
    
    public final boolean hasGaps() {
        return hasGaps;
    }
    
    public final void setAmount(float amount) {
        this.values[AMOUNT] = amount;
    }
    
    public final void setClose(float close) {
        this.values[CLOSE] = close;
    }
    
    public final void setClose_adj(float close_adj) {
        this.values[CLOSE_ADJ] = close_adj;
    }
    
    public final void setHasGaps(boolean hasGaps) {
        this.hasGaps = hasGaps;
    }
    
    public final void setHigh(float high) {
        this.values[HIGH] = high;
    }
    
    public final void setLow(float low) {
        this.values[LOW] = low;
    }
    
    public final void setOpen(float open) {
        this.values[OPEN] = open;
    }
    
    public final void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }
    
    public final void setVolume(float volume) {
        this.values[VOLUME] = volume;
    }
    
    public final void setWAP(float wap) {
        this.values[WAP] = wap;
    }
    
    public final void reset() {
        time = 0;
        sourceId = 0;
        for (int i = 0; i < values.length; i++) {
            values[i] = 0;
        }
        hasGaps = false;
    }
}
