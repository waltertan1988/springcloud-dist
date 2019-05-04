package com.walter.base.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walter.base.entity.JpaAclAction;

public interface AclActionRepository extends JpaRepository<JpaAclAction, Long>{

	public List<JpaAclAction> findByActionCodeIn(Collection<String> actionCodes);
	
	public JpaAclAction findByActionCode(String actionCode);
	
	public List<JpaAclAction> findByIsGroup(boolean isGroup);
}
