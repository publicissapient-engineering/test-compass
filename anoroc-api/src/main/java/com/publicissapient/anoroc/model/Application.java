package com.publicissapient.anoroc.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application")
@Data
@ToString(exclude = {"features","batchList","globalObject"})
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @NonNull
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, updatable = true)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
    List<FeatureEntity> features;

    @ManyToMany(mappedBy = "application")
    private List<Batch> batchList;

    @Transient
    private long featureCount;

    @OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
    private List<GlobalObject> globalObject = new ArrayList<>();



}
