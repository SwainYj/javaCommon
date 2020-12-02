package com.swain.common.utils.component.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class SimpleUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Logger LOGGER = LoggerFactory.getLogger(PKI.class);

    private String token;
    private String customerId;
    private String domainId;
    private String name;
    private Integer userType;
    private String xDomainId;
    private String expiresAt;
    /**
     * @since 2.4.5
     * 增加domainName用来与name比较判断是否子账号
     */
    private String domainName;

    /**
     * 2.12.2 新增subUserId.Saas新购通知需要租户子账号信息
     */
    private String subUserId;

    private String tokenRole;

    public String getSubUserId() {
        return subUserId;
    }

    public void setSubUserId(String subUserId) {
        this.subUserId = subUserId;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getxDomainId() {
        return xDomainId;
    }

    public void setxDomainId(String xDomainId) {
        this.xDomainId = xDomainId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getTokenRole() {
        return tokenRole;
    }

    public void setTokenRole(String tokenRole) {
        this.tokenRole = tokenRole;
    }
}
