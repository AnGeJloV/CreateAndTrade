package com.createandtrade.repositories;

import com.createandtrade.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/** Репозиторий для работы с сущностью User.
 * Предоставляет стандартные CRUD-операции и кастомные методы поиска. */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /** Находит пользователя по его email.
     * Spring Data JPA автоматически сгенерирует SQL-запрос по названию метода. */
    Optional<User> findMyEmail(String email);

    /** Проверяет, существует ли пользователь с таким email.
     * Работает эффективнее, чем findByEmail, так как не загружает всю сущность. */
    boolean existsByEmail(String email);
}
