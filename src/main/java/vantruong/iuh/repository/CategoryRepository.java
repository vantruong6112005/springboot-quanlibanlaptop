package vantruong.iuh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vantruong.iuh.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
