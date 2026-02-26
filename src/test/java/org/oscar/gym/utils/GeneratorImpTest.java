package org.oscar.gym.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GeneratorImp")
class GeneratorImpTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Long> typedQuery;

    private GeneratorImp generator;

    @BeforeEach
    void setUp() {
        generator = new GeneratorImp(entityManager);
    }

    @Nested
    @DisplayName("generatePass")
    class GeneratePass {

        @Test
        @DisplayName("returns 10 character password")
        void generatePass_returns10Chars() {
            String result = generator.generatePass();

            assertThat(result).hasSize(10);
        }

        @Test
        @DisplayName("returns only uppercase letters")
        void generatePass_returnsUppercaseLetters() {
            String result = generator.generatePass();

            assertThat(result).matches("^[A-Z]{10}$");
        }
    }

    @Nested
    @DisplayName("createUser")
    class CreateUser {

        @Test
        @DisplayName("returns firstName.lastName when username not occupied")
        void createUser_returnsSimpleWhenNotOccupied() {
            when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(typedQuery);
            when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
            when(typedQuery.getSingleResult()).thenReturn(0L);

            String result = generator.createUser("John", "Doe");

            assertThat(result).isEqualTo("John.Doe");
        }

        @Test
        @DisplayName("returns firstName.lastName with suffix when username occupied")
        void createUser_returnsWithSuffixWhenOccupied() {
            when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(typedQuery);
            when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
            when(typedQuery.getSingleResult()).thenReturn(2L);

            String result = generator.createUser("John", "Doe");

            assertThat(result).isEqualTo("John.Doe2");
        }
    }

    @Nested
    @DisplayName("isOccupied")
    class IsOccupied {

        @Test
        @DisplayName("returns count from database")
        void isOccupied_returnsCount() {
            when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(typedQuery);
            when(typedQuery.setParameter(anyString(), anyString())).thenReturn(typedQuery);
            when(typedQuery.getSingleResult()).thenReturn(3L);

            long result = generator.isOccupied("John", "Doe");

            assertThat(result).isEqualTo(3L);
        }
    }
}
