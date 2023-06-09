package com.example.antifraud.repository.entity;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class StolenCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(View.UserView.class)
    private Long id;
    @Column
    private String number;

    public StolenCard(String number) {
        this.number = number;
    }
}
