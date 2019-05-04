package com.walter.base.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walter.base.entity.JpaAclMenu;

public interface AclMenuRepository extends JpaRepository<JpaAclMenu, Long>{

	public List<JpaAclMenu> findByMenuCodeIn(Collection<String> menuCodes);
	
	public List<JpaAclMenu> findByIsGroup(boolean isGroup);
}
