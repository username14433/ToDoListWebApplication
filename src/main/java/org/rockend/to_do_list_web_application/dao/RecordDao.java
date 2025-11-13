package org.rockend.to_do_list_web_application.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.transaction.annotation.Transactional;
import org.rockend.to_do_list_web_application.entity.Record;
import org.rockend.to_do_list_web_application.entity.RecordStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RecordDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Record> findAllRecords() {
        Query query = entityManager.createQuery("SELECT r FROM Record r ORDER BY r.id ASC");
        List<Record> records = query.getResultList();
        return new ArrayList<>(records);
    }

    public void saveRecord(Record record) {
        entityManager.persist(record);
    }

    public void updateRecordStatus(int id, RecordStatus newStatus) {
        Query query = entityManager.createQuery("UPDATE Record r SET r.status = :status WHERE r.id = :id");
        query.setParameter("id", id);
        query.setParameter("status", newStatus);
        query.executeUpdate();
    }

    public void deleteRecord(int id) {
        Query query = entityManager.createQuery("DELETE FROM Record r WHERE r.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
