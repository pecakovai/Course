package com.example.courses_finki.entity.subject;

import com.example.courses_finki.entity.file.FileEntity;
import com.example.courses_finki.entity.semester.SemesterEntity;
import com.example.courses_finki.entity.user.UserEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "subject")
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    /**
     *     - Subject name should be in the next format : [name] - [year/year], ex. Operating Systems - 2022/2023
     *     - We can have more subjects with name Operating Systems, but all are for different year
     */


    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserEntity> professors;

    @ManyToOne(fetch = FetchType.EAGER)
    private SemesterEntity semester;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "subject")
    private List<FileEntity> files;

    public SubjectEntity() {
    }

    public SubjectEntity(String name) {
        this.name = name;
    }

    public SubjectEntity(String name, List<UserEntity> professors, SemesterEntity semester) {
        this.name = name;
        this.professors = professors;
        this.semester = semester;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserEntity> getProfessors() {
        return professors;
    }

    public void setProfessors(List<UserEntity> professors) {
        this.professors = professors;
    }

    public SemesterEntity getSemester() {
        return semester;
    }

    public void setSemester(SemesterEntity semester) {
        this.semester = semester;
    }

    public List<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntity> files) {
        this.files = files;
    }
}
