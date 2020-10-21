package com.publicissapient.anoroc.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "business_scenario")
public class BusinessScenario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "description")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @NonNull
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    @NonNull
    private LocalDateTime updatedAt;

    @JoinTable(name = "business_scenario_features",
            joinColumns = @JoinColumn(name = "business_scenario_id"),
            inverseJoinColumns = @JoinColumn(name = "features_id"))
    @ManyToMany(fetch = FetchType.LAZY)
    private List<FeatureEntity> features;

    @ManyToMany(mappedBy = "businessScenarios")
    private List<Run> runs = new ArrayList<>();

}
