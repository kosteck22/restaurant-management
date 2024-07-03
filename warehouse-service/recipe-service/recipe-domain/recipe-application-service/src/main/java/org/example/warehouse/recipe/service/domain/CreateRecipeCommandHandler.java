package org.example.warehouse.recipe.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.recipe.service.domain.dto.create.CreateRecipeCommand;
import org.example.warehouse.recipe.service.domain.dto.create.CreateRecipeResponse;
import org.example.warehouse.recipe.service.domain.dto.create.RecipeItemCommand;
import org.example.warehouse.recipe.service.domain.entity.MenuItem;
import org.example.warehouse.recipe.service.domain.entity.Product;
import org.example.warehouse.recipe.service.domain.entity.Recipe;
import org.example.warehouse.recipe.service.domain.exception.RecipeDomainException;
import org.example.warehouse.recipe.service.domain.mapper.RecipeDataMapper;
import org.example.warehouse.recipe.service.domain.ports.output.repositorty.MenuItemRepository;
import org.example.warehouse.recipe.service.domain.ports.output.repositorty.ProductRepository;
import org.example.warehouse.recipe.service.domain.ports.output.repositorty.RecipeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CreateRecipeCommandHandler {
    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;
    private final MenuItemRepository menuItemRepository;
    private final RecipeDataMapper recipeDataMapper;
    private final RecipeDomainService recipeDomainService;

    public CreateRecipeCommandHandler(RecipeRepository recipeRepository,
                                      ProductRepository productRepository,
                                      MenuItemRepository menuItemRepository,
                                      RecipeDataMapper recipeDataMapper,
                                      RecipeDomainService recipeDomainService) {
        this.recipeRepository = recipeRepository;
        this.productRepository = productRepository;
        this.menuItemRepository = menuItemRepository;
        this.recipeDataMapper = recipeDataMapper;
        this.recipeDomainService = recipeDomainService;
    }

    @Transactional
    public CreateRecipeResponse createRecipe(CreateRecipeCommand createRecipeCommand) {
        checkMenuItem(createRecipeCommand.menuItemId());
        checkProducts(createRecipeCommand);
        Recipe recipe = recipeDataMapper.createRecipeCommandToRecipe(createRecipeCommand);
        recipeDomainService.validateAndInitiate(recipe);
        saveRecipe(recipe);
        log.info("Recipe is created with id: {}", recipe.getId().getValue());

        return new CreateRecipeResponse(recipe.getId().getValue(), "Recipe created successfully.");
    }

    private void saveRecipe(Recipe recipe) {
        Recipe recipeResult = recipeRepository.save(recipe);
        if (recipeResult == null) {
            log.error("Could not save recipe!");
            throw new RecipeDomainException("Could not save recipe!");
        }
        log.info("Recipe is saved with id: {}", recipeResult.getId().getValue());
    }

    private void checkProducts(CreateRecipeCommand createRecipeCommand) {
        List<UUID> productIds = createRecipeCommand.items().stream()
                .map(RecipeItemCommand::productId)
                .collect(Collectors.toList());
        Map<UUID, Product> productIdProductMap = productRepository.findProductsById(productIds).stream()
                .collect(Collectors.toMap(product -> product.getId().getValue(), Function.identity()));

        productIds.forEach(productId -> {
            if (!productIdProductMap.containsKey(productId)) {
                log.warn("There is no product with id: {}", productId);
                throw new RecipeDomainException("There is no product with id: %s".formatted(productId));
            }
        });
    }

    private void checkMenuItem(UUID menuItemId) {
        Optional<MenuItem> menuItem = menuItemRepository.findMenuItem(menuItemId);
        if (menuItem.isEmpty()) {
            log.warn("Could not find menu item with id: {}", menuItemId);
            throw new RecipeDomainException("Could not find menu item with id: " + menuItemId);

        }
    }
}
