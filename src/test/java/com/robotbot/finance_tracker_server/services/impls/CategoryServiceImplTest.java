package com.robotbot.finance_tracker_server.services.impls;

import com.robotbot.finance_tracker_server.domain.dto.category.CategoryCreateRequest;
import com.robotbot.finance_tracker_server.domain.dto.category.CategoryResponse;
import com.robotbot.finance_tracker_server.domain.dto.category.CategoryUpdateRequest;
import com.robotbot.finance_tracker_server.domain.entities.CategoryEntity;
import com.robotbot.finance_tracker_server.domain.entities.IconEntity;
import com.robotbot.finance_tracker_server.domain.entities.UserEntity;
import com.robotbot.finance_tracker_server.domain.exceptions.AuthenticationException;
import com.robotbot.finance_tracker_server.domain.exceptions.EntityWithIdDoesntExistsException;
import com.robotbot.finance_tracker_server.mappers.impls.CategoryMapper;
import com.robotbot.finance_tracker_server.repositories.CategoryRepository;
import com.robotbot.finance_tracker_server.repositories.IconRepository;
import com.robotbot.finance_tracker_server.security.UserPrincipal;
import com.robotbot.finance_tracker_server.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper mapper;
    @Mock
    private UserService userService;
    @Mock
    private IconRepository iconRepository;

    @InjectMocks
    private CategoryServiceImpl service;

    private UserPrincipal principal;
    private UserEntity user;
    private IconEntity icon;

    @BeforeEach
    void setUp() {
        principal = mock(UserPrincipal.class);
        user = new UserEntity();
        user.setId(42L);
        when(userService.getUserByPrincipal(principal)).thenReturn(user);

        icon = new IconEntity();
        icon.setId(7L);
    }

    @Test
    void addCategory_success() {
        CategoryCreateRequest req = CategoryCreateRequest.builder()
                .iconId(7L)
                .name("Food")
                .build();
        when(iconRepository.findById(7L)).thenReturn(Optional.of(icon));
        CategoryEntity mapped = new CategoryEntity();
        when(mapper.mapRequestToEntity(req, user, icon)).thenReturn(mapped);

        service.addCategory(req, principal);

        verify(categoryRepository).save(mapped);
    }

    @Test
    void addCategory_iconNotFound_throws() {
        CategoryCreateRequest req = CategoryCreateRequest.builder()
                .iconId(99L)
                .build();
        when(iconRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityWithIdDoesntExistsException.class,
                () -> service.addCategory(req, principal)
        );
    }

    @Test
    void getCategoryById_success() {
        CategoryEntity entity = new CategoryEntity();
        entity.setUser(user);
        when(categoryRepository.findById(5L)).thenReturn(Optional.of(entity));
        CategoryResponse dto = new CategoryResponse();
        when(mapper.mapEntityToResponse(entity)).thenReturn(dto);

        CategoryResponse result = service.getCategoryById(principal, 5L);

        assertThat(result).isSameAs(dto);
    }

    @Test
    void getCategoryById_notFound_throws() {
        when(categoryRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(EntityWithIdDoesntExistsException.class,
                () -> service.getCategoryById(principal, 5L)
        );
    }

    @Test
    void getCategoryById_authFails_throws() {
        UserEntity other = new UserEntity();
        other.setId(99L);
        CategoryEntity entity = new CategoryEntity();
        entity.setUser(other);
        when(categoryRepository.findById(5L)).thenReturn(Optional.of(entity));

        assertThrows(AuthenticationException.class,
                () -> service.getCategoryById(principal, 5L)
        );
    }

    @Test
    void updateCategory_nameAndIcon_success() {
        CategoryEntity entity = new CategoryEntity();
        entity.setUser(user);
        when(categoryRepository.findById(3L)).thenReturn(Optional.of(entity));
        CategoryUpdateRequest req = CategoryUpdateRequest.builder()
                .name("Travel")
                .iconId(7L)
                .build();
        when(iconRepository.findById(7L)).thenReturn(Optional.of(icon));

        service.updateCategory(3L, req, principal);

        assertThat(entity.getName()).isEqualTo("Travel");
        assertThat(entity.getIcon()).isSameAs(icon);
        verify(categoryRepository).save(entity);
    }

    @Test
    void updateCategory_iconNotFound_throws() {
        CategoryEntity entity = new CategoryEntity();
        entity.setUser(user);
        when(categoryRepository.findById(4L)).thenReturn(Optional.of(entity));
        CategoryUpdateRequest req = CategoryUpdateRequest.builder()
                .iconId(123L)
                .build();
        when(iconRepository.findById(123L)).thenReturn(Optional.empty());

        assertThrows(EntityWithIdDoesntExistsException.class,
                () -> service.updateCategory(4L, req, principal)
        );
    }

    @Test
    void deleteCategory_success() {
        CategoryEntity entity = new CategoryEntity();
        entity.setUser(user);
        when(categoryRepository.findById(8L)).thenReturn(Optional.of(entity));

        service.deleteCategory(8L, principal);

        verify(categoryRepository).delete(entity);
    }

    @Test
    void deleteCategory_authFails_throws() {
        CategoryEntity entity = new CategoryEntity();
        UserEntity other = new UserEntity(); other.setId(100L);
        entity.setUser(other);
        when(categoryRepository.findById(9L)).thenReturn(Optional.of(entity));

        assertThrows(AuthenticationException.class,
                () -> service.deleteCategory(9L, principal)
        );
    }
}
