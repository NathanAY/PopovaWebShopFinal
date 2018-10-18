package net.proselyte.springsecurityapp.model;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "instructions")
public class Instruction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "categories")
    private ArrayList<String> categories;

    @Column(name = "tags")
    private ArrayList<String> tags;

    @Column(name = "rating")
    private int rating;

    @Column(name = "steps")
    private ArrayList<Object> steps;

    @Column(name = "autor")
    private String autor;

    @Column(name = "creationDate")
    private String creationDate;

    public Instruction( ) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
