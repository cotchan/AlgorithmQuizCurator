package com.yhcy.aqc.controller.authentication;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthenticationRequest {

    //UserID에 해당
    private String id;

    //password에 해당
    private String pw;

    public AuthenticationRequest(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("pw", pw)
                .toString();
    }
}
