package com.swain.common.utils.component.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swain.common.utils.component.exception.ServiceExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TokenParser {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(PKI.class);

    // 从IAM服务器获取的证书，一般可以认为永久不变
    private String cert = "";

    private static final TokenParser INSTANCE = new TokenParser();

    private TokenParser() {
    }

    public static TokenParser getInstance() {
        return INSTANCE;
    }

    public String getTokenInfo(String tokenId) {
        return checkPKIToken(tokenId);
    }

    public SimpleUser getUserInfoFromToken(String tokenId) throws Exception {
        SimpleUser user = new SimpleUser();
        return user;
    }

    private void expireVerify(String token, SimpleUser user) throws Exception {
        //增加token失效校验
    }

    private String checkPKIToken(String pkiToken) {
        return PKI.decodeToken(pkiToken, cert);
    }

    private JsonNode mapper(String token)
            throws JsonProcessingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tree = mapper.readTree(token);
        return tree;
    }
}
