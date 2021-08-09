import React from "react";
import {useState, useEffect} from "react";
import {useDispatch} from "react-redux";
import {joinUser, getQuestion} from "../../../_actions/user_action.js";
import SignUp1 from "./SignUp1.js";
import SignUp2 from "./SignUp2.js";

function SignUp(props) {
  const tag = "SIGN UP TO ALGOL";

  const dispatch = useDispatch();

  const [id, setid] = useState("abcd1234");
  const [pw, setpw] = useState("abcd1234!");
  const [verify_answer, setverify_answer] = useState("answer");

  const [nickname, setnickname] = useState("nickname");
  const [verify_question, setverify_question] = useState("question");
  const [qlist, setqlist] = useState([]);

  const [Next, setNext] = useState("true");

  useEffect(() => {
    console.log("props", props);
    /**
     * veryfy question 받아오기
     */
    dispatch(getQuestion()).then((response) => {
      let result = response.payload;
      console.log(tag, result);

      if (response.payload.success) {
        setqlist(result.response);
      } else {
        alert("Error");
      }
    });
  }, []);

  const onIdHandler = (changedId) => {
    setid(changedId);
  };
  const onpwHandler = (changedPw) => {
    setpw(changedPw);
  };
  const onChangeAnswerHandler = (changedAnswer) => {
    setverify_answer(changedAnswer);
  };
  const onChangeQuestionHandler = (changedQuestion) => {
    setverify_question(changedQuestion);
  };
  const onChangeNickHandler = (nick) => {
    setnickname(nick);
  };

  const onNextClickHandler = (event) => {
    event.preventDefault();

    if (!id) {
      return alert("id를 작성해주시기 바랍니다.");
    }
    if (!pw) {
      return alert("비밀번호를 작성해주시기 바랍니다.");
    }

    setNext(false);
  };

  const onSubmitHandler = (event) => {
    event.preventDefault();

    let body = {
      id,
      pw,
      pw_confirm: pw,
      nickname,
      verify_question,
      verify_answer,
    };

    console.log(tag, "body", body);

    dispatch(joinUser(body)).then((response) => {
      if (response.payload.success) {
        alert("회원가입 성공");
        props.history.push("/");
      } else {
        // alert("회원가입 실패");
      }
    });

    props.history.push("/");
  };

  return Next ? (
    <SignUp1
      onNext={onNextClickHandler}
      info={{id, pw, nickname}}
      changeId={onIdHandler}
      changePw={onpwHandler}
    />
  ) : (
    <SignUp2
      info={{qlist, verify_answer, verify_question, nickname}}
      onSubmit={onSubmitHandler}
      changeQ={onChangeQuestionHandler}
      changeA={onChangeAnswerHandler}
      changeNick={onChangeNickHandler}
    />
  );
}

export default SignUp;
