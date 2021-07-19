package com.yhcy.aqc.controller.user;

import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
public class JoinResult_JJORO {

    private final String apiToken;
    private final UserDto_JJORO user;

    public JoinResult_JJORO(String apiToken, UserDto_JJORO user) {
        this.apiToken = apiToken;
        this.user = user;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("apiToken", apiToken)
                .append("user", user)
                .toString();
    }

}
