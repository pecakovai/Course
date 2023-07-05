package com.example.courses_finki.entity.file;

import com.example.courses_finki.entity.subject.SubjectEntity;
import jakarta.persistence.*;
import org.springframework.http.MediaType;

@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "contentType")
    private String contentType;

    @Lob
    @Column(name = "data")
    private byte[] data;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @ManyToOne(fetch = FetchType.EAGER)
    private SubjectEntity subject;


    public FileEntity() {
    }

    public FileEntity(String fileName, String contentType, byte[] data, FileType fileType, SubjectEntity subject) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.data = data;
        this.fileType = fileType;
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }

}
