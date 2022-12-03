package com.laurencetuchin.employeesystemapi.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    private TaskStatus status;

    private LocalDateTime startDate;

    private LocalDateTime endDate;



}
