package org.rockend.service;

import org.rockend.repository.RecordRepository;
import org.rockend.entity.Record;
import org.rockend.entity.RecordStatus;
import org.rockend.entity.dto.RecordsContainerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecordService {
    private final RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Transactional(readOnly = true)
    public RecordsContainerDTO findAllRecords(String filterMode) {
        /*Добавляем сортировку для записей по их id
          В качестве первого аргумента у Sort.by указываем направление сортировки (по возрастанию или по убыванию)
          В качестве второго аргумента указываем строки, по которым будет сортировать
        */
        List<Record> records = recordRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
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

    //Заменяем все наши "кастомные методы для работы с БД теми, которые предоставляет интерфейс JpaRepository"
    //Кастомным в данном случае остаётся только update


    public void saveRecord(String title) {
        if (title != null && !title.isBlank()) {
            recordRepository.save(new Record(title));
        }
    }

    public void updateRecordStatus(int id, RecordStatus newStatus){
        recordRepository.update(id, newStatus);
    }

    public void deleteRecord(int id) {
        recordRepository.deleteById(id);
    }

}
