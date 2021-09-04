import React, {useEffect, useState} from "react";
import resetBtn from "../../../img/resetBtn.png";

function ProblemResetBtn(props) {
  const tag = "Problem_resetbtn";
  const [state, setState] = useState("");

  const {
    quiz_title,
    quiz_url,
    quiz_url_desc,
    quiz_state_type,
    quiz_state_code,
    quiz_level,
    quiz_number,
  } = props.problem;

  /**
   * 임시 코드
   */
  const quiz_desc = quiz_url_desc.includes("Baekjoon Online Judge")
    ? "백준"
    : "";
  const onClick = (e) => {
    e.preventDefault();
    props.onClick(quiz_number);
  };

  useEffect(() => {
    if (!quiz_state_type) {
      Object.entries(props.stateTypes).forEach(([key, value]) => {
        if (value === quiz_state_code) {
          setState(key);
        }
      });
      return;
    }
    setState(quiz_state_type);
  }, [props]);

  const onChange = (e) => {
    e.preventDefault();
    props.onChange(e.target.value, quiz_number);
  };

  return (
    <div
      className="problem reset"
      style={{display: "flex", justifyContent: "space-between"}}
    >
      <img
        src={resetBtn}
        alt="reset-btn"
        style={{width: "60px", marginLeft: "24px"}}
        onClick={onClick}
      ></img>

      <a href={quiz_url}>{quiz_title}</a>

      <div style={{display: "flex", marginRight: "43px"}}>
        <div className="psinfo quiz_level">{quiz_level}</div>
        <div className="psinfo quiz_url_desc">{quiz_desc}</div>
      </div>
    </div>
  );
}

export default ProblemResetBtn;
