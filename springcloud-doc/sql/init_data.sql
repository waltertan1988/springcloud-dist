
/* 初始化密码为123456 */
insert  into `acl_user`(`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`gender`,`password`,`user_real_name`,`username`,`is_expired`,`is_locked`,`is_password_expired`,`is_enabled`,`mobile`) values (NULL,'*ADMIN','2018-08-29 22:50:08','*ADMIN','2018-08-29 22:50:14','M','$2a$10$8r2tGdMtSLfEHbYV/3ZmE.90ivYb7h1y7TyHy86l9mDADMA89SH2O','walter.tan','0009785','\0','\0','\0','\1','13123456789');
insert  into `acl_user`(`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`gender`,`password`,`user_real_name`,`username`,`is_expired`,`is_locked`,`is_password_expired`,`is_enabled`,`mobile`) values (NULL,'*ADMIN','2018-08-29 22:50:08','*ADMIN','2018-08-29 22:50:14','M','$2a$10$8r2tGdMtSLfEHbYV/3ZmE.90ivYb7h1y7TyHy86l9mDADMA89SH2O','waltertan','walter','\0','\0','\0','\1','13987654321');

insert into `acl_role` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `role_code`, `role_name`, `parent_role_code`) values(NULL,'*ADMIN','2019-04-30 23:06:48','*ADMIN','2019-04-30 23:06:54','ROLE_USER','普通用户',NULL);
insert into `acl_role` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `role_code`, `role_name`, `parent_role_code`) values(NULL,'*ADMIN','2018-08-29 22:51:06','*ADMIN','2018-08-29 22:51:11','ROLE_ADMIN','系统管理员','ROLE_USER');

insert into `acl_user_role` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `role_code`, `username`) values(null,'*ADMIN','2018-08-29 22:51:34','*ADMIN','2018-08-29 22:51:42','ROLE_ADMIN','0009785');
insert into `acl_user_role` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `role_code`, `username`) values(null,'*ADMIN','2018-08-29 22:51:34','*ADMIN','2018-08-29 22:51:42','ROLE_USER','walter');

insert into `acl_menu` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `is_group`, `menu_code`, `menu_name`, `parent_code`, `uri`) values(NULL,'*ADMIN','2019-04-29 23:15:36','*ADMIN','2019-04-29 23:15:41','','PUBLIC_INDEX_PAGE','首页','ROOT','/index');
insert into `acl_menu` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `is_group`, `menu_code`, `menu_name`, `parent_code`, `uri`) values(NULL,'*ADMIN','2019-05-01 01:40:37','*ADMIN','2019-05-01 01:40:42','','ADMIN_SYSADM_ROLE','角色管理','ROOT','/admin/sysAdm/role');

insert into `acl_role_resource` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `resource_code`, `resource_type`, `role_code`) values(NULL,'*ADMIN','2019-05-01 01:18:13','*ADMIN','2019-05-01 01:18:13','PUBLIC_INDEX_PAGE','MENU','ROLE_USER');
insert into `acl_role_resource` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `resource_code`, `resource_type`, `role_code`) values(NULL,'*ADMIN','2019-05-01 01:42:36','*ADMIN','2019-05-01 01:42:41','ADMIN_SYSADM_ROLE','MENU','ROLE_ADMIN');

