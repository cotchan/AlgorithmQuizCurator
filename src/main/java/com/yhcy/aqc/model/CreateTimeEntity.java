package com.yhcy.aqc.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * regDate를 모든 엔티티에 기본적으로 추가하기 위한 상위 클래스
 * CreateTimeEntity를 상속받은 Entity(도메인 모델)은 자동으로 createDate를 컬럼으로 가진다.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CreateTimeEntity {

    @CreatedDate
    @Column(name = "regdate")
    private LocalDateTime createDate;
}
