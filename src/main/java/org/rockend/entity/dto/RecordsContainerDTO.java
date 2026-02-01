package org.rockend.entity.dto;

import org.rockend.entity.Record;

import java.util.List;

public class RecordsContainerDTO {
    private final String userName;
    private final List<Record> records;
    private final int numberOfDoneRecords;
    private final int numberOfActiveRecords;

    public RecordsContainerDTO(List<Record> records, int numberOfDoneRecords,
                               int numberOfActiveRecords, String userName) {
        this.records = records;
        this.numberOfDoneRecords = numberOfDoneRecords;
        this.numberOfActiveRecords = numberOfActiveRecords;
        this.userName = userName;
    }

    public List<Record> getRecords() {
        return records;
    }

    public int getNumberOfDoneRecords() {
        return numberOfDoneRecords;
    }

    public int getNumberOfActiveRecords() {
        return numberOfActiveRecords;
    }

    public String getUserName() {
        return userName;
    }
}
