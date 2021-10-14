/*
 * 用户表格
 */
drop table if exists f_user;

create table f_user(
  id bigint primary key auto_increment comment '主键，长整数',
  username varchar(32) unique not null comment '用户名',
  name varchar(16) comment '姓名',
  password varchar(128) not null comment '密码',
  authorities varchar(255) comment '权限，使用字符串描述权限，格式是： 权限1;权限2;权限3'
);

/*
 * 文件表格，用来保存每个用户上传的文件的基本信息。
 */
drop table if exists f_file;

create table f_file(
  id bigint primary key auto_increment comment '主键',
  file_name varchar(128) not null unique comment '文件名称，是系统自动生成的',
  original_file_name varchar(128) not null comment '文件的原始名称',
  file_size bigint not null comment '文件的大小，单位是字节',
  user_id bigint not null comment '上传文件的用户主键'
);
