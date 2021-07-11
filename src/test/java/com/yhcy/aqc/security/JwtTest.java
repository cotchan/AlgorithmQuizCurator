package com.yhcy.aqc.security;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 *  TestInstance 는 테스트 인스턴스의 라이프 사이클을 설정할 때 사용
 *  PER_CLASS : test 클래스 당 인스턴스가 생성된다.
 *  위 어노테이션을 통해 JwtTest 클래스의 라이프 사이클을 클래스 단위로 설정
 *
 *  라이프 사이클을 클래스 단위로 지정해 놓으면 @BeforeAll, @AfterAll 어노테이션을 static method가 아닌 곳에서도 사용할 수 있다.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Jwt jwt;

    @BeforeAll
    void setUp() {
        String issuer = "algorithm_quiz_curator";
        String clientSecret = "yhcycyyh";
        int expirySeconds = 10;

        jwt = new Jwt(issuer, clientSecret, expirySeconds);
    }


    /**
     * int userKey, String userId, String nickName, String[] roles
     */
    @Test
    void JWT_토큰을_생성하고_복호화_할수있다() {
        Jwt.Claims claims = Jwt.Claims.of(1,"tester", "cotchanKevein", new String[]{"ROLE_USER"});
        String encodedJWT = jwt.newToken(claims);
        log.info("encodedJWT: {}", encodedJWT);

        Jwt.Claims decodedJWT = jwt.verify(encodedJWT);
        log.info("decodedJWT: {}", decodedJWT);

        assertThat(claims.userKey, is(decodedJWT.userKey));
        assertArrayEquals(claims.roles, decodedJWT.roles);
    }

    @Test
    void JWT_토큰을_리프레시_할수있다() throws Exception {
        if (jwt.getExpirySeconds() > 0) {
            Jwt.Claims claims = Jwt.Claims.of(1,"tester", "cotchanKevein", new String[]{"ROLE_USER"});
            String encodedJWT = jwt.newToken(claims);
            log.info("encodedJWT: {}", encodedJWT);

            // 1초 대기 후 토큰 갱신
            sleep(1000L);

            String encodedRefreshedJWT = jwt.refreshToken(encodedJWT);
            log.info("encodedRefreshedJWT: {}", encodedRefreshedJWT);

            assertThat(encodedJWT, not(encodedRefreshedJWT));

            Jwt.Claims oldJwt = jwt.verify(encodedJWT);
            Jwt.Claims newJwt = jwt.verify(encodedRefreshedJWT);

            long oldExp = oldJwt.exp();
            long newExp = newJwt.exp();

            // 1초 후에 토큰을 갱신했으므로, 새로운 토큰의 만료시각이 1초 이후임
            assertThat(newExp >= oldExp + 1_000L, is(true));

            log.info("oldJwt: {}", oldJwt);
            log.info("newJwt: {}", newJwt);
        }
    }

}
