package org.example.sale.service.dataaccess.menu.adapter;

import org.example.sale.service.domain.entity.Menu;
import org.example.sale.service.domain.ports.output.repository.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MenuRepositoryImpl implements MenuRepository {
    @Override
    public Optional<Menu> findMenuInformation(Menu menu) {
        return Optional.empty();
    }
}
