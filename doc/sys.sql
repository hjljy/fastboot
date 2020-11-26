CREATE TABLE `sys_menu` (
  `id` int NOT NULL COMMENT '菜单ID',
  `pid` int NOT NULL DEFAULT '0' COMMENT '父级菜单ID',
  `menu_type` varchar(64) NOT NULL COMMENT '菜单类型 M菜单 P页面 F功能按钮',
  `menu_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '菜单名称',
  `perms` varchar(64) NOT NULL DEFAULT '' COMMENT '菜单权限',
  `sort` int NOT NULL DEFAULT '0' COMMENT '菜单排序',
  `menu_channle` int NOT NULL DEFAULT '1' COMMENT '菜单渠道 1 web 2 app 3 api',
  `menu_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '菜单路径',
  `visiable` int NOT NULL DEFAULT '0' COMMENT '是否可见 0是(默认) 1否',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int NOT NULL DEFAULT '0' COMMENT '是否删除  0否(默认) 1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sys_role` (
  `id` int NOT NULL COMMENT '角色ID',
  `org_id` int NOT NULL DEFAULT '-1' COMMENT '角色所属机构ID -1管理员机构',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `sort` int NOT NULL DEFAULT '0' COMMENT '角色排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int NOT NULL DEFAULT '0' COMMENT '是否删除  0否(默认) 1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sys_role_menu` (
  `id` int NOT NULL,
  `role_id` int NOT NULL COMMENT '角色ID',
  `menu_id` int NOT NULL COMMENT '菜单ID',
  `status` int NOT NULL DEFAULT '0' COMMENT '是否删除  0否(默认) 1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `sys_user` (
  `id` bigint NOT NULL COMMENT '用户ID',
  `user_name` varchar(255) NOT NULL COMMENT '用户名称',
  `nick_name` varchar(255) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `password` varchar(255) NOT NULL COMMENT '用户密码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `avatar_url` varchar(255) NOT NULL DEFAULT '' COMMENT '用户头像',
  `sex` int NOT NULL DEFAULT '-1' COMMENT '用户性别 -1保密(默认) 0女 1男 ',
  `birthday` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '出生日期',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `enable` int NOT NULL DEFAULT '0' COMMENT '是否禁用  0否(默认) 1是',
  `status` int NOT NULL DEFAULT '0' COMMENT '是否删除  0否(默认) 1是',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`user_name`) COMMENT '用户账户索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `sys_user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` int NOT NULL COMMENT '角色ID',
  `status` int NOT NULL DEFAULT '0' COMMENT '是否删除 0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;