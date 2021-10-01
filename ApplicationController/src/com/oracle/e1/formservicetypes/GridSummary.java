package com.oracle.e1.formservicetypes;

public class GridSummary {
    
    private int records;
    private boolean moreRecords;
    
    public GridSummary() {
        super();
    }


    public void setRecords(int records) {
        this.records = records;
    }

    public int getRecords() {
        return records;
    }

    public void setMoreRecords(boolean moreRecords) {
        this.moreRecords = moreRecords;
    }

    public boolean isMoreRecords() {
        return moreRecords;
    }
}
