package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.domain.model.Category;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {
    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @Test
    void givenNoCategories_whenGetAllCategories_thenReturnEmptyList() {
        // Arrange
        Mockito.when(categoryPersistencePort.getAll()).thenReturn(Collections.emptyList());

        // Act
        List<Category> categories = categoryUseCase.getAllCategories();

        // Assert
        assertTrue(categories.isEmpty());
    }

    @Test
    void givenCategoriesExist_whenGetAllCategories_thenReturnListOfCategories() {
        // Arrange
        List<Category> expectedCategories= Arrays.asList(
                new Category(1L, "papitas", "papitas chips"),
                new Category(2L, "platanitos", "monedas de platanitos")
        );
        Mockito.when(categoryPersistencePort.getAll()).thenReturn(expectedCategories);

        // Act
        List<Category> actualCategory= categoryUseCase.getAllCategories();

        // Assert
        assertEquals(expectedCategories.size(), actualCategory.size());
        assertTrue(actualCategory.containsAll(expectedCategories));
    }

}