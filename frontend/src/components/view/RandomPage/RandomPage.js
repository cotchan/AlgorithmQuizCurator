import React, {useState} from "react";
import {useDispatch} from "react-redux";
import {getRandomQ} from "../../../_actions/ps_action";
import {ReactComponent as RandomBtn} from "../../../img/RandomBtn.svg";

function RandomPage(props) {
  const tag = "RandomPage";
  const [Hover, setHover] = useState(false);
  const [Rnum, setRnum] = useState(0);
  const dispatch = useDispatch();

  const toggleHover = () => setHover(!Hover);
  const onChangeHandler = (e) => setRnum(e.target.value);

  const onClickHandler = () => {
    let input = parseInt(Rnum);

    if (Number.isInteger(input)) {
      /**
       *랜덤 문제 가져오기
       */
      let body = {problem_cnt: input};
      let key = props.cookiesInfo.key;
      dispatch(getRandomQ(body, key)).then((response) => {
        let result = response.payload;
        if (result.success) {
          // console.log(tag, result.response);
          props.setPlist(result.response);
        }
      });
    }
  };

  return (
    <div
      className="random"
      style={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        width: "835px",
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
        <span
          style={{
            fontWeight: "bold",
            fontSize: "42px",
            textShadow: "7px 1px 4px rgba(0, 0, 0, 0.25)",
          }}
        >
          매일 알고리즘 문제를 고르는 게 귀찮다면?
        </span>
      </div>
      <form
        style={{display: "flex", flexDirection: "column", alignItems: "center"}}
      >
        <div
          style={{
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            background: " #C4C4C4",
            border: "2px solid #000000",
            boxShadow: "10px 15px 4px rgba(0, 0, 0, 0.25)",
            borderRadius: "22px",
            width: "200px",
            padding: "0 80px",
            height: "115px",
            marginTop: "63px",
          }}
        >
          <input
            style={{
              border: "none",
              background: "#C4C4C4",
              width: "20%",
              height: "80%",
              fontSize: "42px",
            }}
            onChange={onChangeHandler}
          />
          <span style={{fontSize: "42px"}}>개</span>
        </div>

        <RandomBtn
          onMouseEnter={toggleHover}
          onMouseLeave={toggleHover}
          className={Hover ? "" : "btn-hover"}
          onClick={onClickHandler}
          style={{marginTop: "63px"}}
        />
      </form>
    </div>
  );
}

export default RandomPage;
