package com.yhcy.aqc.controller.authentication;

import com.yhcy.aqc.controller.user.UserDto_JJORO;
import com.yhcy.aqc.security.AuthenticationResult;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;


@Setter
@Getter
public class AuthenticationResultDto {

    private String apiToken;

    private UserDto_JJORO user;

    public AuthenticationResultDto(AuthenticationResult source) {
        BeanUtils.copyProperties(source, this);

        this.user = new UserDto_JJORO(source.getUser());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("apiToken", apiToken)
                .append("user", user)
                .toString();
    }
}
