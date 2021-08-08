import React, {useState} from "react";
import {useDispatch} from "react-redux";
import {doubleCheckerID} from "../../../_actions/user_action.js";

import {idinfo, pwInfo, confirm_info} from "../../../constants/announcement.js";

function SignUp1(props) {
  const idRe = /^[a-z][a-z|_|\\-|0-9]{4,19}$/;
  const pwRe =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;

  const [IsValidId, setIsValidId] = useState(false);
  const [IsValidPw, setIsValidPw] = useState(false);
  const [IsSamePw, setIsSamePw] = useState(true);
  const [Checked, setChecked] = useState(false);

  const dispatch = useDispatch();

  const onIdHandler = (event) => {
    let curId = event.currentTarget.value;
    props.changeId(event.currentTarget.value);
    setChecked(false);

    if (idRe.exec(curId)) {
      setIsValidId(true);
    } else {
      setIsValidId(false);
    }
  };
  const onpwHandler = (event) => {
    let curPw = event.currentTarget.value;
    props.changePw(event.currentTarget.value);

    //비밀번호 재입력 시, 비밀번호 confirm 재입력 요망
    setIsSamePw(false);

    if (pwRe.exec(curPw)) {
      setIsValidPw(true);
    } else {
      setIsValidPw(false);
    }
  };

  const OnconfirmPwHandler = (event) => {
    let curConfirmpw = event.currentTarget.value;

    if (curConfirmpw !== props.info.pw) {
      return setIsSamePw(false);
    }
    setIsSamePw(true);
  };

  const onNextClickHandler = (event) => {
    event.preventDefault();
    console.log("onnext");

    if (!Checked) {
      return alert("아이디 중복 체크를 해주세요.");
    }
    if (!IsValidPw) {
      return;
    }
    if (!IsValidPw) {
      return;
    }
    if (!IsSamePw) {
      return;
    }

    props.onNext(event);
  };

  //todo
  const onDoubleCheckHanlder = (event) => {
    event.preventDefault();
    let curId = props.info.id;

    dispatch(doubleCheckerID(curId)).then((response) => {});

    setChecked(true);
  };

  return (
    <div
      className="signup-1"
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
        <form style={{display: "flex", flexDirection: "column", width: "100%"}}>
          <label style={{color: "#FFFFFF", marginBottom: "7px"}}>
            ID{" "}
            <span className="valid_id" style={{color: "red", fontSize: "10px"}}>
              {IsValidId ? "" : idinfo}
            </span>
          </label>

          <div
            style={{
              display: "flex",
              flex: "4 1",
              marginBottom: "14px",
              height: "100%",
              width: "100%",
            }}
          >
            <input
              type="text"
              value={props.info.id}
              onChange={onIdHandler}
              style={{
                borderRadius: "16px",
                width: "100%",
                height: "12px",
                padding: "7px",
              }}
            />
            <button
              type="submit"
              style={{
                padding: "7px",
                borderRadius: "16px",
                height: "20px",
              }}
              onClick={onDoubleCheckHanlder}
            >
              double check
            </button>
          </div>

          <label style={{color: "#FFFFFF", marginBottom: "7px"}}>
            pw{" "}
            <span className="valid_pw" style={{color: "red", fontSize: "10px"}}>
              {IsValidPw ? "" : pwInfo}
            </span>
          </label>
          <input
            type="password"
            style={{
              borderRadius: "16px",
              height: "12px",
              marginBottom: "14px",
              padding: "7px",
            }}
            value={props.info.pw}
            onChange={onpwHandler}
          />
          <label style={{color: "#FFFFFF", marginBottom: "7px"}}>
            rewrite pw
            <span className="valid_pw" style={{color: "red", fontSize: "10px"}}>
              {IsSamePw ? "" : confirm_info}
            </span>
          </label>
          <input
            type="password"
            style={{
              borderRadius: "16px",
              height: "12px",
              marginBottom: "14px",
              padding: "7px",
            }}
            onChange={OnconfirmPwHandler}
          />
          <br />
          <button
            style={{
              backgroundColor: "#2C1A52",
              borderRadius: "16px",
              color: "white",
              height: "50px",
            }}
            onClick={onNextClickHandler}
          >
            <span style={{fontWeight: "bold"}}>NEXT</span>
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

export default SignUp1;
