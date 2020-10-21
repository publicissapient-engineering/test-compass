package com.publicissapient.anoroc.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "environment")
@ToString(exclude = {"runs"})
public class Environment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(name = "karate_content")
    private String karateContent;

    @Column(name = "anoroc_content")
    private String anorocContent;

    @Column(name = "settings_content")
    private String settingsContent;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @NonNull
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, updatable = true)
    @UpdateTimestamp
    @NonNull
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "environment")
    private List<Run> runs;

}