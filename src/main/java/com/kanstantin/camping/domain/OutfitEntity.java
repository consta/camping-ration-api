package com.kanstantin.camping.domain;


 import lombok.Data;
import org.dozer.Mapping;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Constraint;
 import java.util.Date;
 import java.util.List;
import java.util.Set;

    @Entity
    @Table(name = "outfit")
    @Data
    public class OutfitEntity {
        @Id
        @Column
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column
        private String name;

        @Column
        private Integer countPeople;

        @Column
        private Date startDate;

        @Column
        private Date endDate;

        @Column
        private Integer countDays;

        @Column
        private Float carbohydrates;

        @Column
        private String description;




    }

