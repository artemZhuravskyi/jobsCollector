package com.artem.jobsCollector.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Job {

    @Id
    private String slug;
    @ManyToOne(cascade = CascadeType.ALL)
    private Company company;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("jobs")
    private Location location;

    private String title;
    private boolean remote;
    private int createdAt;
}
