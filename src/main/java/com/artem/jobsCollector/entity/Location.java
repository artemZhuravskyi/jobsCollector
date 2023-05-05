package com.artem.jobsCollector.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Location {

    @Id
    private String city;

    @OneToMany(mappedBy = "location", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("location")
    private List<Job> jobs;

    public Location(String city) {
        this.city = city;
    }
}
