package org.rockend.service;

import org.rockend.entity.RecordStatus;
import org.rockend.entity.dto.RecordsContainerDTO;

public interface RecordService {
    RecordsContainerDTO findAllRecords(String filterMode);

    void saveRecord(String title);

    void updateRecordStatus(int id, RecordStatus newStatus);

    void deleteRecord(int id);
}
