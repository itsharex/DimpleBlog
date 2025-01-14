package com.dimple.auth.service;

import com.dimple.common.core.constant.Constants;
import com.dimple.common.core.constant.SecurityConstants;
import com.dimple.common.core.utils.ServletUtils;
import com.dimple.common.core.utils.StringUtils;
import com.dimple.common.core.utils.ip.IpUtils;
import com.dimple.system.api.RemoteLogService;
import com.dimple.system.api.model.SysLogininforBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 记录日志方法
 *
 * @author Dimple
 */
@Component
@RequiredArgsConstructor
public class SysRecordLogService {
    private final RemoteLogService remoteLogService;

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     * @return
     */
    public void recordLogininfor(String username, String status, String message) {
        SysLogininforBO logininfor = new SysLogininforBO();
        logininfor.setUserName(username);
        logininfor.setIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        logininfor.setMsg(message);
        // 日志状态
        if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            logininfor.setStatus(Constants.LOGIN_SUCCESS_STATUS);
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            logininfor.setStatus(Constants.LOGIN_FAIL_STATUS);
        }
        remoteLogService.saveLogininfor(logininfor, SecurityConstants.INNER);
    }
}
