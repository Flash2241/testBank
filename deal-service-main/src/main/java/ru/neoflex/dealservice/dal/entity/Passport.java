package ru.neoflex.dealservice.dal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
public class Passport {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(name = "id", nullable = false)
        private Long id;
        private String series;
        private String number;
        private String issueBranch;
        private Date issueDate;
}
