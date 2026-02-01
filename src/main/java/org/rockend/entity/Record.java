package org.rockend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "status", nullable = false)
    private RecordStatus status;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Record() { }


    public Record(String title, User user) {
        this.title = title;
        this.status = RecordStatus.ACTIVE;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
