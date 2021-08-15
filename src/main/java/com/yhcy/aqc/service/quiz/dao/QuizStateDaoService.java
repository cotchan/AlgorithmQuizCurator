package com.yhcy.aqc.service.quiz.dao;

import com.yhcy.aqc.error.NotFoundException;
import com.yhcy.aqc.model.quiz.Quiz;
import com.yhcy.aqc.model.quiz.QuizState;
import com.yhcy.aqc.model.quiz.QuizStateType;
import com.yhcy.aqc.model.quiz.QuizStateTypeEnum;
import com.yhcy.aqc.model.ranking.RankingListElement;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.repository.quiz.QuizStateRepository;
import com.yhcy.aqc.service.user.dao.UserDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@RequiredArgsConstructor
@Service
public class QuizStateDaoService {

    private final QuizStateRepository quizStateRepository;

    private final UserDaoService userDaoService;

    @PersistenceContext
    private final EntityManager em;

    //FIXME
    public List<QuizState> getFindAllSolvingProblems(String userId) {
        checkArgument(userId != null, "userId must be not null");
        User user = userDaoService.findByUserId(userId);
        return quizStateRepository.findAllSolvingProblems(user);
    }

    public List<Quiz> getQuizByStatesAndUserId(List<String> stateTypes, String userId, boolean reverse) throws Exception {
        //동적 쿼리 생성
        CriteriaBuilder cb = em.getCriteriaBuilder();
        //state와 userId를 조건에 사용하는 서브쿼리 생성, 결과에 quiz_seq만 사용하므로 결과 제네릭을 Integer로 지정
        CriteriaQuery<Integer> q = cb.createQuery(Integer.class);
        Subquery<Quiz> sq = q.subquery(Quiz.class);
        Root<QuizState> quizState = sq.from(QuizState.class);
        //유저 아이디 조건 추가
        Predicate predicateUserId = cb.equal(quizState.get("user").get("userId"), userId);
        //stateTypes의 갯수에 맞게 or 절을 추가
        Predicate predicateStateTypes = null;
        for (String stateType : stateTypes) {
            Predicate predicateStateTypeTemp = cb.equal(quizState.get("quizStateType").get("desc"), stateType);
            predicateStateTypes = predicateStateTypes == null ? predicateStateTypeTemp : cb.or(predicateStateTypes, predicateStateTypeTemp);
        }
        //유저 아이디 조건과 N개의 stateType이 or 절로 연결된 조건을 and 절로 결합
        Predicate predicate = cb.and(predicateUserId, predicateStateTypes);
        //완성된 쿼리문 (실제로 where 절 안의 컬럼을 직접적으로 사용하여 구성되진 않으나 보기 간결하게 표현)
        //select * from quiz_state where user.id = ? and (quiz_state_type.desc = ? or quiz_state_type.desc = ? or ...) order by seq asc
        sq.select(quizState.get("quiz").get("seq")).where(predicate);

        //서브쿼리를 사용하여 Quiz 테이블과 in 구문으로 비교
        CriteriaQuery<Quiz> q2 = cb.createQuery(Quiz.class);
        Root<Quiz> quiz = q2.from(Quiz.class);
        //reverse -> false : 입력한 state 조건에 맞는 결과
        //reverse -> true : 입력한 state 조건을 제외한 나머지 결과
        Predicate predicateQuiz = reverse ? cb.in(quiz.get("seq")).value(sq).not() : cb.in(quiz.get("seq")).value(sq);
        q2.select(quiz).where(predicateQuiz).orderBy(cb.asc(quiz.get("seq")));

        return em.createQuery(q2).getResultList();
    }

    public List<QuizState> getQuizStatesByStatesAndUserId(List<String> stateTypes, String userId) {
        //동적 쿼리 생성
        CriteriaBuilder cb = em.getCriteriaBuilder();
        //state와 userId를 조건에 사용하는 서브쿼리 생성, 결과에 quiz_seq만 사용하므로 결과 제네릭을 Integer로 지정
        CriteriaQuery<QuizState> query = cb.createQuery(QuizState.class);
        Root<QuizState> quizState = query.from(QuizState.class);
        //유저 아이디 조건 추가
        Predicate predicateUserId = cb.equal(quizState.get("user").get("userId"), userId);
        //stateTypes의 갯수에 맞게 or 절을 추가
        Predicate predicateStateTypes = null;
        for (String stateType : stateTypes) {
            Predicate predicateStateTypeTemp = cb.equal(quizState.get("quizStateType").get("desc"), stateType);
            predicateStateTypes = predicateStateTypes == null ? predicateStateTypeTemp : cb.or(predicateStateTypes, predicateStateTypeTemp);
        }
        //유저 아이디 조건과 N개의 stateType이 or 절로 연결된 조건을 and 절로 결합
        Predicate predicate = cb.and(predicateUserId, predicateStateTypes);
        //완성된 쿼리문 (실제로 where 절 안의 컬럼을 직접적으로 사용하여 구성되진 않으나 보기 간결하게 표현)
        //select seq from quiz_state where user.id = ? and (quiz_state_type.desc = ? or quiz_state_type.desc = ? or ...)
        query.select(quizState).where(predicate).orderBy(cb.desc(quizState.get("createDate")));

        return em.createQuery(query).getResultList();
    }

    @Transactional
    public QuizState save(final User user, final QuizStateType quizStateType, final Quiz quiz) {
        checkArgument(user != null, "user must be not null");
        checkArgument(quizStateType != null, "quizStateType must be not null");
        checkArgument(quiz != null, "quiz must be not null");

        final QuizState quizState = QuizState.builder()
                .user(user)
                .quiz(quiz)
                .quizStateType(quizStateType)
                .build();
        return quizStateRepository.save(quizState);
    }

    /**
     * Get problems of QuizStateTypeState
     * QST == QuizStateType를 의미한다.
     * NPNS, PNS, PS 중 해당하는 상태의 정보를 가져온다.
     */
    public List<QuizState> getProblemsOfQSTState(final User user, final QuizStateTypeEnum quizStateType) {
        checkArgument(user != null, "user must be not null");
        checkArgument(quizStateType != null, "quizStateType must be not null");

        return quizStateRepository.findAllOfTypeStateProblems(user, quizStateType.state());
    }

    public QuizState findByUserAndQuiz(final User user, final Quiz quiz) {
        checkArgument(user != null, "user must be not null");
        checkArgument(quiz != null, "quiz must be not null");

        return quizStateRepository.findByUserAndQuiz(user, quiz).orElseThrow(() -> new NotFoundException(QuizState.class, user, quiz));
    }

    public List<QuizState> getNotSelectedProblems(final User user, final int offset, final int limit) {
        checkArgument(user != null, "user must be not null");
        return quizStateRepository.findAllNotSelectedProblems(user, PageRequest.of(offset,limit));
    }

    /**
     * quizStateRepository.findAllOfTypeDescProblems 래핑 메소드
     * QuizStateTypeEnum의 code 값으로 조회할 때 사용하기 위해 만들어놓음
     */
    public List<QuizState> findAllByQuizStateTypeCode(final User user, final int code) {
        checkArgument(user != null, "user must be not null");
        QuizStateTypeEnum targetCode = QuizStateTypeEnum.ofCode(code);
        return findAllByQuizStateTypeDesc(user, targetCode);
    }

    /**
     * quizStateRepository.findAllOfTypeDescProblems 래핑 메소드
     * QuizStateTypeEnum의 desc 값으로 조회할 때 사용하기 위해 만들어놓음
     */
    public List<QuizState> findAllByQuizStateTypeDesc(final User user, final QuizStateTypeEnum quizStateTypeEnum) {
        checkArgument(user != null, "user must be not null");
        return quizStateRepository.findAllOfTypeDescProblems(user, quizStateTypeEnum.desc());
    }

    public List<RankingListElement> findBySolvedQuantity(final int pageSize, final int pageNo) {
        checkArgument(pageSize > 0, "page-size must be positive number");
        checkArgument(pageNo > 0, "page-no must be positive number");

        List<Object[]> rankingList =  quizStateRepository.findBySolvedQuantity();
        //paging
        List<RankingListElement> result = new LinkedList<>();
        for (int i = 0; i < pageSize; i++) {
            try {
                result.add(
                        RankingListElement.builder()
                        .user((User) rankingList.get(pageSize * (pageNo - 1) + i)[0])
                        .solvedCnt((Long) rankingList.get(pageSize * (pageNo - 1) + i)[1])
                        .build()
                );
            } catch (Exception e) {
                break;
            }
        }
        return result;
    }

    public List<RankingListElement> findByAccuracy(final int pageSize, final int pageNo) {
        checkArgument(pageSize > 0, "page-size must be positive number");
        checkArgument(pageNo > 0, "page-no must be positive number");

        List<Object[]> rankingList =  quizStateRepository.findBySolvedQuantity();
        //paging
        List<RankingListElement> result = new LinkedList<>();
        for (int i = 0; i < pageSize; i++) {
            try {
                if (rankingList.get(pageSize * (pageNo - 1) + i)[1] == null)
                    continue;
                result.add(
                        RankingListElement.builder()
                        .user((User) rankingList.get(pageSize * (pageNo - 1) + i)[0])
                        .accuracyRatio((Double) rankingList.get(pageSize * (pageNo - 1) + i)[1])
                        .build()
                );
            } catch (Exception e) {
                break;
            }
        }
        return result;
    }
}
