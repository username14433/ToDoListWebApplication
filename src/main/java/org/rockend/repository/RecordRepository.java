package org.rockend.repository;

import org.rockend.entity.Record;
import org.rockend.entity.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {
    /*
    Удалён Entity Manager
    Удалены все методы связанные с работой с БД через entity Manager
    Теперь RecordDao - это не класс, а интерфейс
    Этот интерфейс наследуется от интерфейса JpaRepository, для которого нужно передать два дженерика -
    Первый - это название Entity класса, с которым мы будем работать в данном интерфейсе
    Второй - это тип данных первичного ключа таблицы, с которой мы будем работать в этом интерфейсе
    Интерфейс переименован из RecordDao в RecordRepository
    */


    //Чтобы обновлять статус записи эффективнее - всего за один запрос к БД, пишем кастомный метод update
    //Аннотация Modifying указывает на то, что данный метод изменяет данные
    @Modifying
    @Query("UPDATE Record r SET r.status = :status WHERE r.id = :id")
    void update(int id, @Param("status") RecordStatus newStatus);

    /*
    Создаём кастомный метод для поиска всех записей с конкретным статусом
    Магия в том, что мы не предоставляем никакой реализации для этого метода.
    Spring Data по названию метода сама понимает чего мы хотим и предоставляем реализацию для этого
    Но нужно понимать, что в таком случае у метода должно быть название строго удовлетворяющие определённым правилам
    К счастью Idea отлично знает все эти правила и подсказывает, какие они могут быть
    */
    List<Record> findAllByStatus(RecordStatus status);
}
