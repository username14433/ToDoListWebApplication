package org.rockend.to_do_list_web_application.service;

import org.rockend.to_do_list_web_application.dao.RecordDao;
import org.rockend.to_do_list_web_application.entity.Record;
import org.rockend.to_do_list_web_application.entity.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {
    private final RecordDao recordDao;

    @Autowired
    public RecordService(RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    public List<Record> findAllRecords() {
        return recordDao.findAllRecords();
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
