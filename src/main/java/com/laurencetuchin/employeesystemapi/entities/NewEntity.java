package com.laurencetuchin.employeesystemapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "new_entity")
public class NewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}