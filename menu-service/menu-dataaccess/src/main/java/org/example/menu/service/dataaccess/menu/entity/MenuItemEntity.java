package org.example.menu.service.dataaccess.menu.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.domain.valueobject.VatRate;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu_items")
@Entity
public class MenuItemEntity {
    @Id
    private UUID id;
    private String name;
    private String shortName;
    private String description;
    private VatRate vatRate;
    private BigDecimal grossPrice;
    private boolean active;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID")
    private CategoryEntity category;
}
