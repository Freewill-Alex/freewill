package com.freewill.admin.common.security.session;

import lombok.Data;
import org.apache.shiro.session.mgt.SimpleSession;

/**
 * @author sanma
 */
@Data
public class OnlineSession extends SimpleSession {
    /**
     * 用户浏览器类型
     */
    private String userAgent;
    /**
     * 在线状态
     */
    private OnlineStatus status = OnlineStatus.on_line;
    /**
     * 用户登录时系统IP
     */
    private String systemHost;

    public enum OnlineStatus {
        /**
         * 状态
         */
        on_line("在线"), hidden("隐身"), force_logout("强制退出");
        private final String info;

        OnlineStatus(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }
    //省略其他
}