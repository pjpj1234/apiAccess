-- 创建库
create database if not exists api_access;

-- 切换库
use api_access;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    userAvatar   varchar(1024)                          null comment '用户头像',
    gender       tinyint                                null comment '性别',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user / admin',
    userPassword varchar(512)                           not null comment '密码',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
) comment '用户';

-- 帖子表
create table if not exists post
(
    id            bigint auto_increment comment 'id' primary key,
    age           int comment '年龄',
    gender        tinyint  default 0                 not null comment '性别（0-男, 1-女）',
    education     varchar(512)                       null comment '学历',
    place         varchar(512)                       null comment '地点',
    job           varchar(512)                       null comment '职业',
    contact       varchar(512)                       null comment '联系方式',
    loveExp       varchar(512)                       null comment '感情经历',
    content       text                               null comment '内容（个人介绍）',
    photo         varchar(1024)                      null comment '照片地址',
    reviewStatus  int      default 0                 not null comment '状态（0-待审核, 1-通过, 2-拒绝）',
    reviewMessage varchar(512)                       null comment '审核信息',
    viewNum       int                                not null default 0 comment '浏览数',
    thumbNum      int                                not null default 0 comment '点赞数',
    userId        bigint                             not null comment '创建用户 id',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
) comment '帖子';

create table interface_info
(
    id             bigint auto_increment comment '主键'
        primary key,
    name           varchar(256)                       not null comment '名称',
    description    varchar(256)                       null comment '描述',
    url            varchar(512)                       not null comment '接口地址',
    requestParams  text                               null,
    requestHeader  text                               null comment '请求头',
    responseHeader text                               null comment '响应头',
    status         int      default 0                 not null comment '接口状态（0-关闭，1-开启）',
    method         varchar(256)                       not null comment '请求类型',
    userId         bigint                             not null comment '创建人',
    createTime     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete       tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
)
    comment '接口信息';

create table user_interface_info
(
    id             bigint auto_increment comment '主键' primary key,
    userId         bigint                             not null comment '调用用户id',
    interfaceInfoId bigint  default 0                 not null comment '接口id',
    totalNum       int default 0                      not null comment '总调用次数',
    leftNum        int default 0                      not null comment '剩余调用次数',
    status         int      default 0                 not null comment '接口状态（0-正常，1-禁用）',
    createTime     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete       tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
)
    comment '用户调用接口信息';

INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (21, '张伟', '西南交通大学', 'http://localhost:8090/api/name/user/', null, 'Authorization: Bearer token', 'Content-Type: application/json', 1, 'POST', 101, '2024-07-31 20:32:08', '2024-08-14 23:21:49', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (22, '李娜', '清华大学', 'http://localhost:8090/api/name/', null, 'Authorization: Bearer token', 'Content-Type: application/json', 1, 'POST', 102, '2024-07-31 20:32:08', '2024-08-14 18:23:15', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (23, '王强', '北京大学', 'www.pku.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 0, 'PUT', 103, '2024-07-31 20:32:08', '2024-08-01 16:57:16', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (24, '刘洋', '复旦大学', 'www.fudan.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 0, 'DELETE', 104, '2024-07-31 20:32:08', '2024-08-01 16:57:16', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (25, '陈明', '上海交通大学', 'www.sjtu.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 0, 'GET', 105, '2024-07-31 20:32:08', '2024-08-01 16:57:16', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (26, '杨静', '浙江大学', 'www.zju.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 0, 'POST', 106, '2024-07-31 20:32:08', '2024-08-01 16:57:16', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (27, '赵云', '南京大学', 'www.nju.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 0, 'PUT', 107, '2024-07-31 20:32:08', '2024-08-01 16:57:16', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (28, '孙浩', '武汉大学1', 'www.whu.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 0, 'DELETE', 108, '2024-07-31 20:32:08', '2024-08-12 20:54:18', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (29, '周婷', '哈尔滨工业大学', 'www.hit.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 0, 'GET', 109, '2024-07-31 20:32:08', '2024-08-01 16:57:16', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (30, '吴磊', '同济大学', 'www.tongji.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 0, 'POST', 110, '2024-07-31 20:32:08', '2024-08-01 16:57:16', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (31, '郑爽', '东南大学', 'www.seu.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 1, 'PUT', 111, '2024-07-31 20:32:08', '2024-07-31 20:32:08', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (32, '何俊', '中山大学', 'www.sysu.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 1, 'DELETE', 112, '2024-07-31 20:32:08', '2024-07-31 20:32:08', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (33, '林静', '北京师范大学', 'www.bnu.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 1, 'GET', 113, '2024-07-31 20:32:08', '2024-07-31 20:32:08', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (34, '徐斌', '南开大学', 'www.nankai.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 1, 'POST', 114, '2024-07-31 20:32:08', '2024-07-31 20:32:08', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (35, '蒋婷婷', '西安交通大学', 'www.xjtu.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 1, 'PUT', 115, '2024-07-31 20:32:08', '2024-07-31 20:32:08', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (36, '郭俊', '四川大学', 'www.scu.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 1, 'DELETE', 116, '2024-07-31 20:32:08', '2024-07-31 20:32:08', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (37, '韩梅', '电子科技大学', 'www.uestc.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 1, 'GET', 117, '2024-07-31 20:32:08', '2024-07-31 20:32:08', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (38, '陈伟', '中国人民大学', 'www.ruc.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 1, 'POST', 118, '2024-07-31 20:32:08', '2024-07-31 20:32:08', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (39, '李静', '西北工业大学', 'www.nwpu.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 1, 'PUT', 119, '2024-07-31 20:32:08', '2024-07-31 20:32:08', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (40, '张婷', '天津大学', 'www.tju.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 1, 'DELETE', 120, '2024-07-31 20:32:08', '2024-07-31 20:32:08', 0);
INSERT INTO api_access.interface_info (id, name, description, url, requestParams, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) VALUES (41, '刘强', '中国农业大学', 'www.cau.edu.cn', null, 'Authorization: Bearer token', 'Content-Type: application/json', 1, 'GET', 121, '2024-07-31 20:32:08', '2024-07-31 20:32:08', 0);

INSERT INTO api_access.user (id, userName, userAccount, userAvatar, gender, userRole, userPassword, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1, '蒲俊', 'xiaopu', 'https://s2.loli.net/2024/07/27/VscE7DrW1NZgKe3.jpg', 0, 'admin', '8796e9ca141c92931bb8815b3eb9d166', 'pujun', 'abc', '2024-08-01 16:17:49', '2024-08-07 15:06:24', 0);
INSERT INTO api_access.user (id, userName, userAccount, userAvatar, gender, userRole, userPassword, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (2, '施皓天', 'xiaoshi', 'https://s2.loli.net/2024/07/27/VscE7DrW1NZgKe3.jpg', 0, 'admin', '8796e9ca141c92931bb8815b3eb9d166', null, null, '2024-08-01 23:24:43', '2024-08-01 23:24:45', 0);
INSERT INTO api_access.user (id, userName, userAccount, userAvatar, gender, userRole, userPassword, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (3, null, 'xiaotao', null, null, 'user', '8796e9ca141c92931bb8815b3eb9d166', 'd19b74ff6bf7dc67', 'c23031cbdb882344', '2024-08-07 15:06:36', '2024-08-07 15:06:36', 0);

INSERT INTO api_access.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (1, 1, 21, 14, 3, 0, '2024-08-08 17:16:02', '2024-08-15 10:56:26', 0);
