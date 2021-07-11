package com.yhcy.aqc.controller.user;

import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
public class JoinResult {

    private final String apiToken;
    private final UserDto user;

    public JoinResult(String apiToken, UserDto user) {
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
