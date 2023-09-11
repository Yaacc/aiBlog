# aiBlog

[GitHub入门：创建分支branch和使用Pull request](https://blog.csdn.net/qq_39375237/article/details/109832043)

[IDEA fork别人的GitHub项目、保持代码同步、开发并pull request](https://blog.csdn.net/qq_39618369/article/details/108758462)

[IDEA创建Maven项目报错- Error injecting constructor, java.lang.NoSuchMethodError: org.apache.maven.model](https://blog.csdn.net/qq_44605463/article/details/123803362)
# mysql
## tb_feedback
```mysql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `tb_video`
-- ----------------------------
DROP TABLE IF EXISTS `tb_video`;
CREATE TABLE `tb_video` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `user` varchar(10) DEFAULT NULL COMMENT '发布者',
                            `video_title` varchar(255) DEFAULT NULL COMMENT '视频标题',
                            `video_desc` text COMMENT '视频介绍',
                            `video_cover` varchar(255) DEFAULT NULL COMMENT '视频封面',
                            `video_path` varchar(255) DEFAULT NULL COMMENT '视频路径',
                            `video_status` int(11) DEFAULT '1',
                            `video_like` int(20) DEFAULT NULL,
                            `video_created` varchar(20) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;
```


# mysql
## tb_feedback
```mysql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_feedback`
-- ----------------------------
DROP TABLE IF EXISTS `tb_feedback`;
CREATE TABLE `tb_feedback` (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `content` varchar(225) DEFAULT NULL,
                               `user_id` int(11) DEFAULT NULL,
                               `createDate` varchar(25) DEFAULT NULL,
                               `admin_id` int(11) DEFAULT NULL,
                               `answer` varchar(255) DEFAULT NULL,
                               `answerDate` varchar(25) DEFAULT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;
```

# mysql
## tb_comment
```mysql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_comment`
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `userId` int(11) DEFAULT NULL,
                              `articleId` int(11) DEFAULT NULL,
                              `content` text,
                              `create_time` varchar(20) DEFAULT NULL,
                              `parent_id` int(11) DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;
```
# mysql
## tb_favorite
```mysql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_favorite`
-- ----------------------------
DROP TABLE IF EXISTS `tb_favorite`;
CREATE TABLE `tb_favorite` (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `userId` int(11) DEFAULT NULL COMMENT '用户',
                               `articleId` int(11) DEFAULT NULL COMMENT '文章',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;
```
# mysql
## tb_like
```mysql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_role`
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`role_name` varchar(20) DEFAULT NULL COMMENT '角色名称',
`role_code` varchar(20) DEFAULT NULL COMMENT '角色编号',
`role_note` varchar(64) DEFAULT NULL COMMENT '备注',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

```
# mysql
## tb_like
```mysql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_like`
-- ----------------------------
DROP TABLE IF EXISTS `tb_like`;
CREATE TABLE `tb_like` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`userId` int(11) DEFAULT NULL COMMENT '用户',
`articleId` int(11) DEFAULT NULL COMMENT '文章',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;
```

# mysql
## tb_article
```mysql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章编号',
  `user` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发布人',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发布时间',
  `likes` int(11) NULL DEFAULT NULL COMMENT '点赞',
  `collect` int(11) NULL DEFAULT NULL COMMENT '收藏',
  `is_delete` tinyint(1) NULL DEFAULT NULL COMMENT '1删除',
  `enable` tinyint(1) NULL DEFAULT NULL COMMENT '1启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
```
## tb_files
```mysql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_files
-- ----------------------------
DROP TABLE IF EXISTS `tb_files`;
CREATE TABLE `tb_files`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `size` bigint(10) NULL DEFAULT NULL COMMENT '文件大小',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下载链接',
  `md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件md5',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除（1删除）',
  `enable` tinyint(1) NULL DEFAULT 1 COMMENT '是否禁用链接（1可用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
```
## tb_admin
```mysql
/*
 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50640
 Source Host           : localhost:3306
 Source Schema         : db_home
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_admin
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE `tb_admin`  (
  `id` int(5) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '序号',
  `adminNumber` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员编号',
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `realName` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '真实姓名',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '性别',
  `age` int(3) NULL DEFAULT NULL COMMENT '年龄',
  `IDCard` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份证号',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_admin
-- ----------------------------
INSERT INTO `tb_admin` VALUES (00001, '1001', '管理员1', '123', '张三', '男', 23, '330381221301241245', '2023-07-31 16:28:34');
INSERT INTO `tb_admin` VALUES (00002, '1200', 'admin2', '123', '里斯', '男', 34, '110011001100110011', '2023-07-31 16:30:29');
INSERT INTO `tb_admin` VALUES (00003, 'a1232', 'admin', '123', '白一', '男', 32, '987654321123456789', '2023-07-31 16:39:34');
INSERT INTO `tb_admin` VALUES (00004, 'a34243', 'a1', '123', '贝贝', '女', 32, '97645568741231564X', '2023-07-31 16:43:30');

SET FOREIGN_KEY_CHECKS = 1;
```

## tb_user
```mysql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
    `id` int(5) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '序号',
    `userNumber` varchar(20) NOT NULL COMMENT '员工编号',
    `username` varchar(20) NOT NULL COMMENT '用户名',
    `password` varchar(20) NOT NULL COMMENT '密码',
    `realName` varchar(10) NOT NULL COMMENT '真实姓名',
    `sex` varchar(1) DEFAULT NULL COMMENT '性别',
    `age` int(3) DEFAULT NULL COMMENT '年龄',
    `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
    `IDCard` varchar(18) NOT NULL COMMENT '身份证号',
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `role_id` int(11) DEFAULT NULL COMMENT '角色Id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
```