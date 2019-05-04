CREATE TABLE `acl_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(255) COLLATE utf8_bin NOT NULL,
  `role_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `parent_role_code` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `created_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jqdita2l45v2gglry7bp8kl1f` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `acl_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8_bin NOT NULL,
  `password` varchar(255) COLLATE utf8_bin NOT NULL,
  `user_real_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `mobile` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `gender` varchar(1) COLLATE utf8_bin NOT NULL,,
  `is_expired` bit(1) NOT NULL,
  `is_locked` bit(1) NOT NULL,
  `is_password_expired` bit(1) NOT NULL,
  `is_enabled` bit(1) NOT NULL,
  `created_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_51bvuyvihefoh4kp5syh2jpi4` (`username`),
  UNIQUE KEY `UK_51bvuyvihefoh4kp5syh2jpi5` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `acl_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(255) COLLATE utf8_bin NOT NULL,
  `username` varchar(255) COLLATE utf8_bin NOT NULL,
  `created_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKdpcww1t3hhjsuxisdjygqps40` (`username`,`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `acl_action` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action_code` varchar(255) COLLATE utf8_bin NOT NULL,
  `action_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `is_group` bit(1) NOT NULL,
  `parent_code` varchar(255) COLLATE utf8_bin NOT NULL,
  `uri` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `order` int NOT NULL DEFAULT 0,
  `created_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_38i63eoapprflv3esjdf9036d` (`action_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `acl_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_group` bit(1) NOT NULL,
  `menu_code` varchar(255) COLLATE utf8_bin NOT NULL,
  `menu_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `parent_code` varchar(255) COLLATE utf8_bin NOT NULL,
  `uri` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `created_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_b679yhxaq3tpo2ri78i4tndgl` (`menu_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `acl_role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource_code` varchar(255) COLLATE utf8_bin NOT NULL,
  `resource_type` varchar(255) COLLATE utf8_bin NOT NULL,
  `role_code` varchar(255) COLLATE utf8_bin NOT NULL,
  `created_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6e17ja7cin5wpidl4kmd820by` (`resource_code`,`resource_type`,`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- SpringSecurity的REMEMBER-ME功能表
CREATE TABLE `persistent_logins` (
  `username` VARCHAR(64) NOT NULL, 
  `series` VARCHAR(64) PRIMARY KEY, 
  `token` VARCHAR(64) NOT NULL, 
  `last_used` TIMESTAMP NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;