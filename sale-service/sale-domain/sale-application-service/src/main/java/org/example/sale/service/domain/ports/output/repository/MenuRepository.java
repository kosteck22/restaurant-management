package org.example.sale.service.domain.ports.output.repository;

import org.example.sale.service.domain.entity.Menu;

import java.util.Optional;

public interface MenuRepository {
    Optional<Menu> findMenuInformation(Menu menu);
}
