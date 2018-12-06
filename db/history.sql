-- 插入菜单和菜单权限
DROP PROCEDURE IF EXISTS insert_default_category;
DELIMITER //
CREATE PROCEDURE insert_default_category()
  BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE tid CHAR(64);
    DECLARE uu CHAR(64);
    DECLARE cur1 CURSOR FOR SELECT ut.id FROM `user_tenant` ut WHERE STATUS='1';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    OPEN cur1;
    REPEAT
      FETCH cur1 INTO tid;
      IF NOT done
      THEN
        SELECT UPPER(UUID()) INTO uu;
        -- 给每个租户创建默认分类
        INSERT INTO `user_post_category` (`id`, `category`, `createTime`, `tenantId`, `status`) VALUES (uu, '默认分类', SYSDATE(), tid, '1');
        -- 将每个租户的历史数据放到默认分类下
        UPDATE `user_post` SET category=uu WHERE tenantId=tid;
      END IF;
    UNTIL done END REPEAT;

    CLOSE cur1;
  END//
DELIMITER ;
-- 插入数据
CALL insert_default_category();
-- 删除存储过程
DROP PROCEDURE IF EXISTS insert_default_category;


-- 将user_employee表的 的proTitle 字段 name改成职级
UPDATE data_base_column
SET `Name` = '职级'
WHERE
    Id IN (
          SELECT
                 ch.Id
          FROM
               (
               SELECT
                      dc.Id
               FROM
                    data_base_table dbt,
                    data_table dt,
                    data_base_column dbc,
                    data_column dc
               WHERE
                   dbt.id = dt.Id
                 AND dt.Id = dbc.TableId
                 AND dbc.Id = dc.Id
                 AND dt.ActualTableName = 'user_employee'
                 AND dc.ActualColumnName = 'proTitle'
                 AND dbc.`Name`='职称'
               ) as ch
          );

-- 向data_column和data_base_column 插入数据
-- 存储过程
DROP PROCEDURE IF EXISTS insert_data_column;
DELIMITER //
CREATE PROCEDURE insert_data_column(tableName VARCHAR(255),columnName VARCHAR(255), dataType VARCHAR(20), actualColumnName VARCHAR(255), actualColumnType VARCHAR(20))
  BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE a CHAR(64);
    DECLARE b CHAR(36);
    DECLARE c CHAR(36);
    DECLARE aa INT DEFAULT 0;
    DECLARE cur1 CURSOR FOR SELECT ut.id, UPPER(UUID()) FROM `user_tenant` ut WHERE STATUS='1';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    OPEN cur1;
    REPEAT
      FETCH cur1 INTO a, b;
      IF NOT done
      THEN
        SELECT `Id` INTO c FROM `data_base_table` WHERE tenantId = a AND `Name` = tableName AND `Discriminator` = 'DataTable';
        IF (c IS NOT NULL && c != '') THEN
          INSERT INTO `data_base_column`(`Id`, `TableId`, `Name`, `DataType`, `Discriminator`, `IsPrimaryKey`, `tenantId`)
          VALUES (b, c, columnName, dataType, 'DataColumn', 0, a);
          INSERT INTO `data_column`(`Id`, `ActualColumnName`, `ActualColumnType`, `tenantId`)
          VALUES (b, actualColumnName, actualColumnType, a);
        END IF;
      END IF;
    UNTIL done END REPEAT;

    CLOSE cur1;
  END//
DELIMITER ;
-- 插入数据

CALL insert_data_column('员工表','企业工龄', '文本', 'enterpriseEmployedTime', 'varchar');
CALL insert_data_column('员工表','社会工龄', '文本', 'socialEmployedTime', 'varchar');
CALL insert_data_column('员工表','商业保险变更日期', '日期', 'commercialInsuranceChangeDate', 'date');
CALL insert_data_column('员工表','是否参加商业保险', '文本', 'isCommercialInsurance', 'varchar');
CALL insert_data_column('员工表','商业保险方案', '文本', 'commercialInsuranceScheme', 'varchar');
CALL insert_data_column('员工表','上年度剩余年假天数', '文本', 'lastYearAnnualLeave', 'varchar');
CALL insert_data_column('员工表','可申请年假天数', '文本', 'kexiuAnnualLeave', 'varchar');
CALL insert_data_column('员工表','已修年假天数', '文本', 'yixiuAnnualLeave', 'varchar');
CALL insert_data_column('员工表','入职前社保情况', '文本', 'beforeSocialInsuranceStatus', 'varchar');
CALL insert_data_column('员工表','入职前公积金情况', '文本', 'beforeFundStatus', 'varchar');
CALL insert_data_column('员工表','养老保险状态', '文本', 'endowmentInsuranceStatus', 'varchar');
CALL insert_data_column('员工表','医疗保险状态', '文本', 'medicareStatus', 'varchar');
CALL insert_data_column('员工表','工伤保险状态', '文本', 'injuryInsuranceStatus', 'varchar');
CALL insert_data_column('员工表','生育保险状态', '文本', 'maternityInsuranceStatus', 'varchar');
CALL insert_data_column('员工表','失业保险状态', '文本', 'unemploymentInsurance', 'varchar');
CALL insert_data_column('员工表','岗位名称', '文本', 'postName', 'varchar');
CALL insert_data_column('员工表','部门名称', '文本', 'departmentName', 'varchar');
CALL insert_data_column('员工表','qq号', '文本', 'qqNumber', 'varchar');
CALL insert_data_column('员工表','邮政编码', '文本', 'postalCode', 'varchar');
CALL insert_data_column('员工表','移动电话', '文本', 'mobilePhone', 'varchar');
CALL insert_data_column('员工表','家庭电话', '文本', 'homePhone', 'varchar');
CALL insert_data_column('员工表','备注', '文本', 'remark', 'varchar');
CALL insert_data_column('员工表','邮箱', '文本', 'emailbox', 'varchar');
CALL insert_data_column('员工表','试用期绩效工资', '文本', 'probationaryPerformance', 'text');
CALL insert_data_column('员工表','照片', '文本', 'picture', 'varchar');

-- 岗位 添加字段
-- 插入数据
CALL insert_data_column('岗位表','上级岗位', '文本', 'superior', 'varchar');
CALL insert_data_column('岗位表','岗位分类', '文本', 'category', 'varchar');
CALL insert_data_column('岗位表','创建时间', '日期', 'createTime', 'date');

-- 用户表 添加字段
-- 插入数据
CALL insert_data_column('用户表','更新时间', '日期+时间', 'updateTime', 'date');

-- 删除存储过程
DROP PROCEDURE IF EXISTS insert_data_column;


-- 子管理菜单和权限
-- 插入菜单和菜单权限
DROP PROCEDURE IF EXISTS insert_menu;
DELIMITER //
CREATE PROCEDURE insert_menu()
  BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE a CHAR(64);
    DECLARE userId CHAR(64);
    DECLARE u1 CHAR(36);
    DECLARE u2 CHAR(36);
    DECLARE u3 CHAR(36);
    DECLARE u4 CHAR(36);
    DECLARE u5 CHAR(36);
    DECLARE u6 CHAR(36);
    DECLARE u7 CHAR(36);
    DECLARE u8 CHAR(36);
    DECLARE u9 CHAR(36);
    DECLARE u10 CHAR(36);
    DECLARE u11 CHAR(36);
    DECLARE u12 CHAR(36);

    DECLARE cur1 CURSOR FOR SELECT ut.id FROM `user_tenant` ut WHERE STATUS='1';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    OPEN cur1;
    REPEAT
      FETCH cur1 INTO a;
      IF NOT done
      THEN
        SELECT `Id` INTO userId FROM `user_account` WHERE tenantId = a AND isTenantCreator=1;
        SELECT UPPER(UUID()) INTO u1;
        SELECT UPPER(UUID()) INTO u2;
        SELECT UPPER(UUID()) INTO u3;
        SELECT UPPER(UUID()) INTO u4;
        SELECT UPPER(UUID()) INTO u5;
        SELECT UPPER(UUID()) INTO u6;
        SELECT UPPER(UUID()) INTO u7;
        SELECT UPPER(UUID()) INTO u8;
        SELECT UPPER(UUID()) INTO u9;
        SELECT UPPER(UUID()) INTO u10;
        SELECT UPPER(UUID()) INTO u11;
        SELECT UPPER(UUID()) INTO u12;
        IF (userId IS NOT NULL && userId != '') THEN
          -- 菜单
          INSERT INTO `manage_menu` (`id`, `parentId`, `name`, `link`, `icon`, `sort`, `level`, `tenantId`) VALUES (u1, '0', '用户管理', NULL, NULL, '1', '1', a);
          INSERT INTO `manage_menu` (`id`, `parentId`, `name`, `link`, `icon`, `sort`, `level`, `tenantId`) VALUES (u2, '0', '系统管理', NULL, NULL, '2', '1', a);
          INSERT INTO `manage_menu` (`id`, `parentId`, `name`, `link`, `icon`, `sort`, `level`, `tenantId`) VALUES (u3, u1, '岗位分类', 'sys/user-post-classify', NULL, '5', '2', a);
          INSERT INTO `manage_menu` (`id`, `parentId`, `name`, `link`, `icon`, `sort`, `level`, `tenantId`) VALUES (u4, u2, '子管理员', 'sys/manage-child-manage', NULL, '5', '2', a);
          INSERT INTO `manage_menu` (`id`, `parentId`, `name`, `link`, `icon`, `sort`, `level`, `tenantId`) VALUES (u5, u1, '角色权限', 'iframe/rolePermissions', NULL, '1', '2', a);
          INSERT INTO `manage_menu` (`id`, `parentId`, `name`, `link`, `icon`, `sort`, `level`, `tenantId`) VALUES (u6, u1, '组织机构', 'iframe/organization', NULL, '2', '2', a);
          INSERT INTO `manage_menu` (`id`, `parentId`, `name`, `link`, `icon`, `sort`, `level`, `tenantId`) VALUES (u7, u1, '员工列表', 'iframe/employeeList', NULL, '3', '2', a);
          INSERT INTO `manage_menu` (`id`, `parentId`, `name`, `link`, `icon`, `sort`, `level`, `tenantId`) VALUES (u8, u1, '菜单管理', 'iframe/menuList', NULL, '4', '2', a);
          INSERT INTO `manage_menu` (`id`, `parentId`, `name`, `link`, `icon`, `sort`, `level`, `tenantId`) VALUES (u9, u2, '基础设置', 'tenant/edit', NULL, '1', '2', a);
          INSERT INTO `manage_menu` (`id`, `parentId`, `name`, `link`, `icon`, `sort`, `level`, `tenantId`) VALUES (u10, u2, '管理员变更', 'sys/manage-Account', NULL, '2', '2', a);
          INSERT INTO `manage_menu` (`id`, `parentId`, `name`, `link`, `icon`, `sort`, `level`, `tenantId`) VALUES (u11, u2, '操作日志', 'sys/log', NULL, '3', '2', a);
          INSERT INTO `manage_menu` (`id`, `parentId`, `name`, `link`, `icon`, `sort`, `level`, `tenantId`) VALUES (u12, u2, '登录日志', 'sys/login-log', NULL, '4', '2', a);
          -- 默认权限数据
          INSERT INTO `manage_user_menu` (`id`, `userId`, `menuId`, `tenantId`, `createTime`) VALUES (UPPER(UUID()), userId, u3, a, SYSDATE());
          INSERT INTO `manage_user_menu` (`id`, `userId`, `menuId`, `tenantId`, `createTime`) VALUES (UPPER(UUID()), userId, u1, a, SYSDATE());
          INSERT INTO `manage_user_menu` (`id`, `userId`, `menuId`, `tenantId`, `createTime`) VALUES (UPPER(UUID()), userId, u4, a, SYSDATE());
          INSERT INTO `manage_user_menu` (`id`, `userId`, `menuId`, `tenantId`, `createTime`) VALUES (UPPER(UUID()), userId, u5, a, SYSDATE());
          INSERT INTO `manage_user_menu` (`id`, `userId`, `menuId`, `tenantId`, `createTime`) VALUES (UPPER(UUID()), userId, u6, a, SYSDATE());
          INSERT INTO `manage_user_menu` (`id`, `userId`, `menuId`, `tenantId`, `createTime`) VALUES (UPPER(UUID()), userId, u7, a, SYSDATE());
          INSERT INTO `manage_user_menu` (`id`, `userId`, `menuId`, `tenantId`, `createTime`) VALUES (UPPER(UUID()), userId, u8, a, SYSDATE());
          INSERT INTO `manage_user_menu` (`id`, `userId`, `menuId`, `tenantId`, `createTime`) VALUES (UPPER(UUID()), userId, u2, a, SYSDATE());
          INSERT INTO `manage_user_menu` (`id`, `userId`, `menuId`, `tenantId`, `createTime`) VALUES (UPPER(UUID()), userId, u9, a, SYSDATE());
          INSERT INTO `manage_user_menu` (`id`, `userId`, `menuId`, `tenantId`, `createTime`) VALUES (UPPER(UUID()), userId, u10, a, SYSDATE());
          INSERT INTO `manage_user_menu` (`id`, `userId`, `menuId`, `tenantId`, `createTime`) VALUES (UPPER(UUID()), userId, u11, a, SYSDATE());
          INSERT INTO `manage_user_menu` (`id`, `userId`, `menuId`, `tenantId`, `createTime`) VALUES (UPPER(UUID()), userId, u12, a, SYSDATE());
        END IF;
      END IF;
    UNTIL done END REPEAT;

    CLOSE cur1;
  END//
DELIMITER ;
-- 插入数据
CALL insert_menu();
DROP PROCEDURE IF EXISTS insert_menu;