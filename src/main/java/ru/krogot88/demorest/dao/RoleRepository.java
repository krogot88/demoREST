package ru.krogot88.demorest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krogot88.demorest.model.Role;

public interface RoleRepository  extends JpaRepository<Role,Long> {
}
