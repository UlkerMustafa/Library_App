package org.libraryapp.dao.repository;

import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.libraryapp.dao.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
}

