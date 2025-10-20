package org.rockend.to_do_list_web_application.service;

import org.rockend.to_do_list_web_application.dao.RecordDao;
import org.rockend.to_do_list_web_application.entity.Record;
import org.rockend.to_do_list_web_application.entity.RecordStatus;
import org.rockend.to_do_list_web_application.entity.dto.RecordsContainerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecordService {
    private final RecordDao recordDao;

    @Autowired
    public RecordService(RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    public RecordsContainerDTO findAllRecords(String filterMode) {
        List<Record> records = recordDao.findAllRecords();
        int numberOfDoneRecords = (int) records.stream()
                .filter(record -> record.getStatus() == RecordStatus.DONE).count();
        int numberOfActiveRecords = (int) records.stream()
                .filter(record -> record.getStatus() == RecordStatus.ACTIVE).count();
        if (filterMode == null || filterMode.isBlank()){
             return new RecordsContainerDTO(records, numberOfDoneRecords, numberOfActiveRecords);
        }

        String filterModeInUpperCase = filterMode.toUpperCase();
        List<String> allowedFilterModes = Arrays.stream(RecordStatus.values())
                .map(record -> record.name())
                .toList();

        if (allowedFilterModes.contains(filterModeInUpperCase)){
            List<Record> filteredRecords =  records.stream()
                    .filter(record -> record.getStatus() == RecordStatus.valueOf(filterModeInUpperCase))
                    .collect(Collectors.toList());
            return new RecordsContainerDTO(filteredRecords, numberOfDoneRecords, numberOfActiveRecords);
        }else {
            return new  RecordsContainerDTO(records, numberOfDoneRecords, numberOfActiveRecords);
        }
    }

    public void saveRecord(String title) {
        if (title != null && !title.isBlank()) {
            recordDao.saveRecord(new Record(title));
        }
    }

    public void updateRecordStatus(int id, RecordStatus newStatus){
        recordDao.updateRecordStatus(id, newStatus);
    }

    public void deleteRecord(int id) {
        recordDao.deleteRecord(id);
    }

}
