package com.store.local_store.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, updatable = false)
    private String name;

    // do I really need this
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<ProductEntity> products;
}
