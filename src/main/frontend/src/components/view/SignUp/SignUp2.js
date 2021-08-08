import React from "react";

function SignUp2(props) {
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

    props.onSubmit(event);
  };

  const onChangeQuestionHandler = (event) => {
    props.changeQ(event.target.value);
  };

  const onChangeAnswerHandler = (event) => {
    props.changeA(event.target.value);
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
          height: "280px",
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
            Question
          </label>
          <select
            name="pets"
            id="pet-select"
            style={{
              marginBottom: "14px",
              width: "100%",
              height: "32px",
              padding: "7px",
              borderRadius: "16px",
            }}
            onChange={onChangeQuestionHandler}
          >
            <option
              style={{
                borderRadius: "16px",
                height: "32px",
              }}
              selected
              value=""
            >
              질문을 선택해주세요
            </option>
            {props.info.q_list.map((question) => {
              return (
                <option
                  style={{
                    borderRadius: "16px",
                    height: "32px",
                  }}
                  value={question}
                >
                  {question}
                </option>
              );
            })}
          </select>

          <label style={{color: "#FFFFFF", marginBottom: "7px"}}>Answer</label>
          <input
            type="text"
            style={{
              borderRadius: "16px",
              height: "32px",
              marginBottom: "14px",
              padding: "7px",
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
