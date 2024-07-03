package org.example.recipe.service.application.rest;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.recipe.service.domain.dto.create.CreateRecipeCommand;
import org.example.warehouse.recipe.service.domain.dto.create.CreateRecipeResponse;
import org.example.warehouse.recipe.service.domain.ports.input.service.RecipeApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/recipes", produces = "application/vnd.api.v1+json")
public class RecipeController {
    private final RecipeApplicationService recipeApplicationService;

    public RecipeController(RecipeApplicationService recipeApplicationService) {
        this.recipeApplicationService = recipeApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateRecipeResponse> createRecipe(@RequestBody CreateRecipeCommand createRecipeCommand) {
        log.info("Creating recipe for menu item: {}", createRecipeCommand.menuItemId());
        CreateRecipeResponse createRecipeResponse = recipeApplicationService.createRecipe(createRecipeCommand);
        log.info("Recipe created with id: {}", createRecipeResponse.recipeId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createRecipeResponse);
    }
}
