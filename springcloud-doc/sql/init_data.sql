
/* 初始化密码为123456 */
insert  into `acl_user`(`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`gender`,`password`,`user_real_name`,`username`,`is_expired`,`is_locked`,`is_password_expired`,`is_enabled`,`mobile`) values (NULL,'*ADMIN','2018-08-29 22:50:08','*ADMIN','2018-08-29 22:50:14','M','$2a$10$8r2tGdMtSLfEHbYV/3ZmE.90ivYb7h1y7TyHy86l9mDADMA89SH2O','walter.tan','0009785','\0','\0','\0','\1','13123456789');
insert  into `acl_user`(`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`gender`,`password`,`user_real_name`,`username`,`is_expired`,`is_locked`,`is_password_expired`,`is_enabled`,`mobile`) values (NULL,'*ADMIN','2018-08-29 22:50:08','*ADMIN','2018-08-29 22:50:14','M','$2a$10$8r2tGdMtSLfEHbYV/3ZmE.90ivYb7h1y7TyHy86l9mDADMA89SH2O','waltertan','walter','\0','\0','\0','\1','13987654321');

insert into `acl_role` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `role_code`, `role_name`, `parent_role_code`) values(NULL,'*ADMIN','2019-04-30 23:06:48','*ADMIN','2019-04-30 23:06:54','ROLE_USER','普通用户',NULL);
insert into `acl_role` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `role_code`, `role_name`, `parent_role_code`) values(NULL,'*ADMIN','2018-08-29 22:51:06','*ADMIN','2018-08-29 22:51:11','ROLE_ADMIN','系统管理员','ROLE_USER');

insert into `acl_user_role` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `role_code`, `username`) values(null,'*ADMIN','2018-08-29 22:51:34','*ADMIN','2018-08-29 22:51:42','ROLE_ADMIN','0009785');
insert into `acl_user_role` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `role_code`, `username`) values(null,'*ADMIN','2018-08-29 22:51:34','*ADMIN','2018-08-29 22:51:42','ROLE_USER','walter');

insert into `acl_action` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `action_code`, `action_name`, `is_group`, `parent_code`, `uri`, `order`) values(null,'*ADMIN','2019-05-11 15:21:58','*ADMIN','2019-05-11 15:22:04','ADMIN_ROLE','角色管理','','ROOT','/admin/role/**','0');
insert into `acl_action` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `action_code`, `action_name`, `is_group`, `parent_code`, `uri`, `order`) values(null,'*ADMIN','2019-05-11 15:28:07','*ADMIN','2019-05-11 15:28:14','ADMIN_PRODUCT','商品管理','','ROOT','/admin/product/**','0');

insert into `acl_role_resource` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `resource_code`, `resource_type`, `role_code`) values(null,'*ADMIN','2019-05-01 09:41:10','*ADMIN','2019-05-01 09:41:10','ADMIN_PRODUCT','ACTION','ROLE_USER');
insert into `acl_role_resource` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `resource_code`, `resource_type`, `role_code`) values(null,'*ADMIN','2019-05-11 09:34:35','*ADMIN','2019-05-11 09:34:35','ADMIN_ROLE','ACTION','ROLE_ADMIN');

