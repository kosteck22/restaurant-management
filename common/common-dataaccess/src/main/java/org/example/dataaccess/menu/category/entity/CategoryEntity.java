package org.example.dataaccess.menu.category.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.dataaccess.menu.entity.MenuItemEntity;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
@Entity
public class CategoryEntity {
    @Id
    private UUID id;
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<MenuItemEntity> items;
}
