package iisi.example.gia.dao;

import iisi.example.gia.entity.DeptDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepository extends JpaRepository<DeptDO, Integer> {
}
