package com.example.courses_finki.entity.semester;

import jakarta.persistence.*;

@Entity
@Table(name = "semester")
public class SemesterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private SemesterType type;

    @Column(name = "year")
    private int year;

    public SemesterEntity() {
    }

    public SemesterEntity(SemesterType type, int year) {
        this.type = type;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SemesterType getType() {
        return type;
    }

    public void setType(SemesterType type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
