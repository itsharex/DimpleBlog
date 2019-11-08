package com.dimple.project.log.mapper;

import com.dimple.project.log.domain.LoginLog;

import java.util.List;

/**
 * @className: LoginLogMapper
 * @description: 系统访问日志情况信息 数据层
 * @author: Dimple
 * @date: 10/22/19
 */
public interface LoginLogMapper {
    /**
     * 新增系统登录日志
     *
     * @param loginLog 访问日志对象
     */
    void insertLoginLog(LoginLog loginLog);

    /**
     * 查询系统登录日志集合
     *
     * @param loginLog 访问日志对象
     * @return 登录记录集合
     */
    List<LoginLog> selectLoginLogList(LoginLog loginLog);

    /**
     * 批量删除系统登录日志
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    int deleteLoginLogByIds(String[] ids);

    /**
     * f
     * 清空系统登录日志
     *
     * @return 结果
     */
    int cleanLoginLog();
}