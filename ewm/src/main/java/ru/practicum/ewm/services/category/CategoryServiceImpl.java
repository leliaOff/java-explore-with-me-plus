package ru.practicum.ewm.services.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.NewCategoryDto;
import ru.practicum.ewm.exceptions.DataException;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.mappers.CategoryMapper;
import ru.practicum.ewm.models.Category;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.repository.EventRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.toModel(newCategoryDto);
        categoryRepository.save(category);
        log.info("Category saved: {}", category);
        return CategoryMapper.toDto(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {

        categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));
        if (eventRepository.existsByCategoryId(categoryId)) {
            throw new DataException("Events in this category still exist");
        }

        categoryRepository.deleteById(categoryId);
        log.info("Category deleted, ID : {}", categoryId);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));
        if (categoryRepository.existsByNameAndIdNot(categoryDto.getName(), categoryId)) {
            throw new DataException("Category name already exists");
        }
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return CategoryMapper.toDto(category);
    }
}
