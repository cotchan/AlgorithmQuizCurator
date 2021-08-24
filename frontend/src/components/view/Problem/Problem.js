import React, { useEffect, useState } from "react";

function Problem(props) {
  const tag = "Problem";
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
      className="problem"
      style={{ display: "flex", justifyContent: "space-between" }}
    >
      <a href={quiz_url}>{quiz_title}</a>
      <div style={{ display: "flex", marginRight: "43px" }}>
        <select
          style={{ width: "198px" }}
          onChange={onChange}
          value={state}
          defaultValue={state}
        >
          {Object.keys(props.stateTypes).map((type) => {
            return (
              <option key={type} value={type}>
                {type}
              </option>
            );
          })}
        </select>

        <div className="psinfo quiz_level">{quiz_level}</div>
        <div className="psinfo quiz_url_desc">{quiz_desc}</div>
      </div>
    </div>
  );
}

export default Problem;
