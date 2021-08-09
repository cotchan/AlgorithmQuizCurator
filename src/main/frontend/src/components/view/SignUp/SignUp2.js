import {React, useState} from "react";
import {useDispatch} from "react-redux";

import {doubleCheckerNick} from "../../../_actions/user_action.js";

function SignUp2(props) {
  const tag = "signup2";

  const dispatch = useDispatch();

  const [IsValidNick, setIsValidNick] = useState(false);
  const [IsValidA, setIsValidA] = useState(false);
  const [Checked, setChecked] = useState(false);

  const onSubmitHandler = (event) => {
    /**
     * 폼 확인
     */
    event.preventDefault();

    if (!props.info.verify_question) {
      return alert("비밀번호 확인 질문을 선택해주세요.");
    }
    if (!props.info.verify_answer) {
      return alert("비밀번호 확인 대답을 작성해주세요.");
    }
    if (!IsValidNick) {
      return;
    }
    if (!Checked) {
      return;
    }
    if (!IsValidA) {
      return;
    }

    props.onSubmit(event);
  };

  const onChangeNickHandler = (event) => {
    let nick = event.target.value;
    props.changeNick(nick);
    setChecked(false);

    if (nick.length >= 2) {
      setIsValidNick(true);
    } else {
      setIsValidNick(false);
    }
  };

  const onChangeQuestionHandler = (event) => {
    props.changeQ(event.target.value);
  };

  const onChangeAnswerHandler = (event) => {
    let answer = event.target.value;
    props.changeA(answer);

    if (answer.length >= 1) {
      setIsValidA(true);
    } else {
      setIsValidA(false);
    }
  };

  const onDoubleCheckHanlder = (event) => {
    event.preventDefault();

    let nick = props.info.nickname;

    dispatch(doubleCheckerNick(nick)).then((response) => {
      console.log(tag, response.payload);
      if (response.payload.success) {
        setChecked(true);
        console.log("nick true");
      } else {
        setChecked(false);
      }
    });

    setChecked(true);
  };

  return (
    <div
      className="signup-2"
      style={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        width: "360px",
        height: "100%",
        margin: "0 auto",
      }}
    >
      <div
        className="logo"
        style={{
          padding: "36px 0px 12px",
        }}
      ></div>
      <div
        style={{
          display: "flex",
          width: "100%",
          color: "#FFFFFF",
          justifyContent: "center",
        }}
      >
        <span style={{fontWeight: "bold", fontSize: "32px"}}>
          SIGN UP TO ALGOL
        </span>
      </div>
      <div
        className="form-body"
        style={{
          display: "flex",
          flexDirection: "column",
          boxSizing: "border-box",
          margin: "16px 0",
          width: "100%",
          height: "360px",
          alignItems: "center",
          backgroundColor: "#26249E",
          padding: "24px",
          borderRadius: "30px",
        }}
      >
        <form
          style={{display: "flex", flexDirection: "column", width: "100%"}}
          onSubmit={onSubmitHandler}
        >
          <label style={{color: "#FFFFFF", marginBottom: "7px"}}>
            Nickname{" "}
            <span className="valid_id" style={{color: "red", fontSize: "10px"}}>
              {IsValidNick ? "" : "두 글자 이상"}
            </span>
          </label>

          <div
            style={{
              display: "flex",
              flex: "auto",
              marginBottom: "14px",
              width: "100%",
            }}
          >
            <input
              type="text"
              value={props.info.nick}
              onChange={onChangeNickHandler}
              style={{
                borderRadius: "16px",
                width: "100%",
                padding: "7px",
              }}
            />
            <button
              type="submit"
              style={{
                padding: "7px",
                borderRadius: "16px",
                height: "100%",
              }}
              onClick={onDoubleCheckHanlder}
            >
              double check
            </button>
          </div>
          <label style={{color: "#FFFFFF", marginBottom: "7px"}}>
            Question
          </label>
          <select
            style={{
              flex: "auto",
              marginBottom: "14px",
              width: "100%",
              padding: "7px",
              borderRadius: "16px",
            }}
            onChange={onChangeQuestionHandler}
          >
            <option
              style={{
                borderRadius: "16px",
              }}
              defaultValue="질문을 선택해주세요"
            >
              질문을 선택해주세요
            </option>
            {props.info.qlist.map((question) => {
              return (
                <option
                  style={{
                    borderRadius: "16px",
                  }}
                  value={question.code}
                  key={question.code}
                >
                  {question.desc}
                </option>
              );
            })}
          </select>

          <label style={{color: "#FFFFFF", marginBottom: "7px"}}>
            Answer{" "}
            <span
              className="valid_answer"
              style={{color: "red", fontSize: "10px"}}
            >
              {IsValidA ? "" : "한 글자 이상"}
            </span>
          </label>
          <input
            type="text"
            style={{
              borderRadius: "16px",
              marginBottom: "14px",
              padding: "7px",
              flex: "auto",
            }}
            value={props.info.verify_answer}
            onChange={onChangeAnswerHandler}
          />
          <br />
          <button
            style={{
              backgroundColor: "#2C1A52",
              borderRadius: "16px",
              color: "white",
              height: "50px",
            }}
            onClick={onSubmitHandler}
          >
            <span style={{fontWeight: "bold"}}>SIGN UP</span>
          </button>
        </form>
      </div>
      <div
        className="if"
        style={{
          margin: "16px 0 10px",
          padding: "25px 0",
          border: "1px solid #2C1A52",
          borderRadius: "16px",
          width: "100%",
          fontSize: "12px",
        }}
      >
        <div
          style={{
            width: "100%",
            display: "flex",
            justifyContent: "center",
          }}
        >
          <p style={{display: "inline", color: "white", marginRight: "5px"}}>
            already have an account ?
          </p>{" "}
          <a href="/signup" style={{color: "#65CC35"}}>
            SIGN IN
          </a>
        </div>
      </div>
    </div>
  );
}

export default SignUp2;
