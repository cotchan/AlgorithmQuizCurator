package com.yhcy.aqc.controller.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequest {

    private String principal;   //id

    private String credentials; //pw

    private String credentialsConfirm;  //pw_confirm

    private String nickName;

    private Integer verifyQuestionSeq;

    private String verifyAnswer;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("principal", principal)
                .append("credentials", credentials)
                .append("credentialsConfirm", credentialsConfirm)
                .append("nickName", nickName)
                .append("verifyQuestionSeq", verifyQuestionSeq)
                .append("verifyAnswer", verifyAnswer)
                .toString();
    }

}