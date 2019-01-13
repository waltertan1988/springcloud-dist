
/* 初始化密码为123456 */
insert into `sys_user` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `gender`, `password`, `user_real_name`, `username`) values(null,'*ADMIN','2018-08-29 22:50:08','*ADMIN','2018-08-29 22:50:14','M','$2a$10$8r2tGdMtSLfEHbYV/3ZmE.90ivYb7h1y7TyHy86l9mDADMA89SH2O','walter.tan','0009785');

insert into `sys_role` (null, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `role_code`, `role_name`) values('1','*ADMIN','2018-08-29 22:51:06','*ADMIN','2018-08-29 22:51:11','ROLE_ADMIN','系统管理员');

insert into `sys_user_role` (null, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `role_code`, `username`) values('1','*ADMIN','2018-08-29 22:51:34','*ADMIN','2018-08-29 22:51:42','ROLE_ADMIN','0009785');
