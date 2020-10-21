package com.publicissapient.anoroc.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "runs")
@Entity
@Builder
@ToString(exclude = {"features","businessScenarios"})
public class Run implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "details")
    private String details;

    @Column(name = "error_description")
    private String errorDescription;

    @Column(name = "report_url")
    private String reportUrl;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "status")
    private RunStatus status;

    @ManyToMany
    @JoinTable(
            name = "runs_features",
            joinColumns = @JoinColumn(name = "run_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private List<FeatureEntity> features;

    @ManyToMany
    @JoinTable(
            name = "business_scenario_run",
            joinColumns = @JoinColumn(name = "run_id"),
            inverseJoinColumns = @JoinColumn(name = "business_scenario_id")
    )
    private List<BusinessScenario> businessScenarios;


    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "run_type")
    private RunType runType;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    @NonNull
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    @NonNull
    private LocalDateTime updatedAt;

    @NonNull
    @JoinColumn(columnDefinition = "environment_id", referencedColumnName = "id")
    @ManyToOne
    private Environment environment;

}
