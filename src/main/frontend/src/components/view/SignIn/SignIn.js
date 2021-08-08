import {React, useState} from "react";
import {useDispatch} from "react-redux";
import {loginUser} from "../../../_actions/user_action.js";

function SignIn(props) {
  const dispatch = useDispatch();
  const [Id, setId] = useState("test004");
  const [Password, setPassword] = useState("!Q@W3e4r");

  const onIdHandler = (event) => {
    setId(event.currentTarget.value);
  };

  const onPasswordHandler = (event) => {
    setPassword(event.currentTarget.value);
  };

  const onSubmitHandler = (event) => {
    event.preventDefault();

    let body = {id: Id, pw: Password};

    dispatch(loginUser(body)).then((response) => {
      console.log(response);
      if (response.payload.success) {
        alert("로그인 성공");
        props.history.push("/");
      } else {
        alert("id 또는 비밀번호가 일치하지 않습니다.");
      }
    });
  };

  return (
    <>
      <div
        className="form"
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
            SIGN IN TO ALGOL
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
            <label style={{color: "#FFFFFF", marginBottom: "7px"}}>ID</label>
            <input
              type="text"
              style={{
                borderRadius: "16px",
                height: "32px",
                marginBottom: "14px",
                padding: "7px",
              }}
              value={Id}
              onChange={onIdHandler}
            />
            <label style={{color: "#FFFFFF", marginBottom: "7px"}}>
              Password
            </label>
            <input
              type="password"
              style={{
                borderRadius: "16px",
                height: "32px",
                marginBottom: "14px",
                padding: "7px",
              }}
              value={Password}
              onChange={onPasswordHandler}
            />
            <br />
            <button
              style={{
                backgroundColor: "#2C1A52",
                borderRadius: "16px",
                color: "white",
                height: "50px",
              }}
            >
              <span style={{fontWeight: "bold"}}>LOG IN</span>
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
              New to Algol?
            </p>{" "}
            <a href="/signup" style={{color: "#65CC35"}}>
              CREATE AN ACCOUNT
            </a>
          </div>
        </div>
      </div>
    </>
  );
}

export default SignIn;
