package org.rockend.service;

import org.rockend.entity.User;
import org.rockend.repository.RecordRepository;
import org.rockend.entity.Record;
import org.rockend.entity.RecordStatus;
import org.rockend.entity.dto.RecordsContainerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecordServiceImpl implements RecordService {
    private final RecordRepository recordRepository;
    private final UserService userService;

    @Autowired
    public RecordServiceImpl(RecordRepository recordRepository,  UserService userService) {
        this.recordRepository = recordRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public RecordsContainerDTO findAllRecords(String filterMode) {

        User currentUser = userService.getCurrentUser();
        List<Record> records = currentUser.getRecords().stream()
                .sorted(Comparator.comparingInt(r -> r.getId()))
                .collect(Collectors.toList());
        int numberOfDoneRecords = (int) records.stream()
                .filter(record -> record.getStatus() == RecordStatus.DONE).count();
        int numberOfActiveRecords = (int) records.stream()
                .filter(record -> record.getStatus() == RecordStatus.ACTIVE).count();
        if (filterMode == null || filterMode.isBlank()){
             return new RecordsContainerDTO(records, numberOfDoneRecords, numberOfActiveRecords, currentUser.getName());
        }

        String filterModeInUpperCase = filterMode.toUpperCase();
        List<String> allowedFilterModes = Arrays.stream(RecordStatus.values())
                .map(record -> record.name())
                .toList();

        if (allowedFilterModes.contains(filterModeInUpperCase)){
            List<Record> filteredRecords =  records.stream()
                    .filter(record -> record.getStatus() == RecordStatus.valueOf(filterModeInUpperCase))
                    .collect(Collectors.toList());
            return new RecordsContainerDTO(filteredRecords, numberOfDoneRecords, numberOfActiveRecords, currentUser.getName());
        }else {
            return new  RecordsContainerDTO(records, numberOfDoneRecords, numberOfActiveRecords, currentUser.getName());
        }
    }


    public void saveRecord(String title) {
        if (title != null && !title.isBlank()) {
            User currentUser =  userService.getCurrentUser();
            recordRepository.save(new Record(title, currentUser));
        }
    }

    public void updateRecordStatus(int id, RecordStatus newStatus){
        recordRepository.update(id, newStatus);
    }

    public void deleteRecord(int id) {
        recordRepository.deleteById(id);
    }

}
