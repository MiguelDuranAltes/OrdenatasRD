package es.udc.asi.notebook_rest.model.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.asi.notebook_rest.model.domain.Category;
import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.repository.CategoryDao;
import es.udc.asi.notebook_rest.model.repository.NoteDao;
import es.udc.asi.notebook_rest.model.service.dto.CategoryDTO;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CategoryService {
  @Autowired
  private CategoryDao categoryDAO;

  @Autowired
  private NoteDao noteDAO;

  public Collection<CategoryDTO> findAll() {
    return categoryDAO.findAll().stream().sorted(Comparator.comparing(Category::getName)).map(CategoryDTO::new)
        .collect(Collectors.toList());
  }

  public CategoryDTO findById(Long id) throws NotFoundException {
    Category category = categoryDAO.findById(id);
    if (category == null) {
      throw new NotFoundException(id.toString(), Category.class);
    }
    return new CategoryDTO(category);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public void deleteById(Long id) throws NotFoundException {
    Category theCategory = categoryDAO.findById(id);
    if (theCategory == null) {
      throw new NotFoundException(id.toString(), Category.class);
    }

    Collection<Note> notes = noteDAO.findAllByCategory(id);
    notes.forEach(note -> {
      note.getCategories().remove(theCategory);
      noteDAO.update(note);
    });
    categoryDAO.delete(theCategory);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public CategoryDTO create(CategoryDTO category) {
    Category bdCategory = new Category(category.getName());
    categoryDAO.create(bdCategory);
    return new CategoryDTO(bdCategory);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public CategoryDTO update(CategoryDTO category) throws NotFoundException {
    Category bdCategory = categoryDAO.findById(category.getId());
    if (bdCategory == null) {
      throw new NotFoundException(category.getId().toString(), Category.class);
    }
    bdCategory.setName(category.getName());
    categoryDAO.update(bdCategory);
    return new CategoryDTO(bdCategory);
  }
}

