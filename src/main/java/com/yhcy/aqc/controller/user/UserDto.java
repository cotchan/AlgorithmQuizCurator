package com.yhcy.aqc.controller.user;


import com.yhcy.aqc.model.user.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {

    private Integer seq;

    private String userId;

    private String nickName;

    public UserDto(User source) {
        this.seq = source.getSeq();
        this.userId = source.getUserId();
        this.nickName = source.getNickName();
    }
}
