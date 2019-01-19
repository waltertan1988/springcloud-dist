package org.walter.base.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.walter.base.entity.JpaSysMenu;

public interface SysMenuRepository extends JpaRepository<JpaSysMenu, Long>{

	public List<JpaSysMenu> findByMenuCodeIn(Collection<String> menuCodes);
}
