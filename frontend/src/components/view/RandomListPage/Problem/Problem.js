import React from "react";

function Problem(props) {
  const tag = "Problem";

  const {
    quiz_title,
    quiz_url,
    quiz_url_desc,
    quiz_state_type,
    quiz_level,
    quiz_number,
  } = props.problem;

  //임시
  const quiz_desc = quiz_url_desc.includes("Baekjoon Online Judge")
    ? "백준"
    : "";

  const onChange = (e) => {
    // console.log(tag, "onchange");
    props.onChange(e.target.value, quiz_number);
  };

  return (
    <div
      className="problem"
      style={{display: "flex", justifyContent: "space-between"}}
    >
      <a href={quiz_url}>{quiz_title}</a>
      <div style={{display: "flex", marginRight: "43px"}}>
        <select style={{width: "198px"}} onChange={onChange}>
          {Object.keys(props.stateTypes).map((type) => {
            if (quiz_state_type === type) {
              <option key={quiz_number} defaultValue={type}>
                {type}
              </option>;
            }
            return <option value={type}>{type}</option>;
          })}
        </select>
        <div className="psinfo quiz_level">{quiz_level}</div>
        <div className="psinfo quiz_url_desc">{quiz_desc}</div>
      </div>
    </div>
  );
}

export default Problem;
