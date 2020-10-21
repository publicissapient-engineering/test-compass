package com.publicissapient.anoroc.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "features")
@Entity
@Builder
@ToString(exclude = {"runs","businessScenarios"})
public class FeatureEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "content")
    private String content;

    @Column(name = "xpath")
    private String xPath;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "feature_type")
    private FeatureType featureType;

    @ManyToMany(mappedBy = "features")
    private List<Run> runs = new ArrayList<>();

    @ManyToMany(mappedBy = "features", fetch = FetchType.LAZY)
    private List<BusinessScenario> businessScenarios = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(columnDefinition = "application_id", referencedColumnName = "id")
    private Application application;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "include_features",joinColumns = @JoinColumn(name = "feature_id", referencedColumnName = "id"))
    @Column(name = "include_feature_id")
    private List<Long> includeFeatures = new ArrayList<>();

}
