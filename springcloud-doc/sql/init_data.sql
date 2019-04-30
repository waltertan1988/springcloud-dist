
/* 初始化密码为123456 */
insert  into `sys_user`(`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`gender`,`password`,`user_real_name`,`username`,`is_expired`,`is_locked`,`is_password_expired`,`is_enabled`,`mobile`) values (null,'*ADMIN','2018-08-29 22:50:08','*ADMIN','2018-08-29 22:50:14','M','$2a$10$8r2tGdMtSLfEHbYV/3ZmE.90ivYb7h1y7TyHy86l9mDADMA89SH2O','walter.tan','0009785','\0','\0','\0','\1','13123456789');
insert  into `sys_user`(`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`gender`,`password`,`user_real_name`,`username`,`is_expired`,`is_locked`,`is_password_expired`,`is_enabled`,`mobile`) values (null,'*ADMIN','2018-08-29 22:50:08','*ADMIN','2018-08-29 22:50:14','M','$2a$10$8r2tGdMtSLfEHbYV/3ZmE.90ivYb7h1y7TyHy86l9mDADMA89SH2O','waltertan','walter','\0','\0','\0','\1','13987654321');

insert into `sys_role` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `role_code`, `role_name`, `parent_role_code`) values(NULL,'*ADMIN','2019-04-30 23:06:48','*ADMIN','2019-04-30 23:06:54','ROLE_USER','普通用户',NULL);
insert into `sys_role` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `role_code`, `role_name`, `parent_role_code`) values(NULL,'*ADMIN','2018-08-29 22:51:06','*ADMIN','2018-08-29 22:51:11','ROLE_ADMIN','系统管理员','ROLE_USER');

insert into `sys_user_role` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `role_code`, `username`) values(null,'*ADMIN','2018-08-29 22:51:34','*ADMIN','2018-08-29 22:51:42','ROLE_ADMIN','0009785');
insert into `sys_user_role` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `role_code`, `username`) values(null,'*ADMIN','2018-08-29 22:51:34','*ADMIN','2018-08-29 22:51:42','ROLE_USER','walter');
