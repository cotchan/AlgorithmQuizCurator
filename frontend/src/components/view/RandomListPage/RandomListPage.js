import React from "react";
import List from "../List/List";
import { ReactComponent as RandomBtn } from "../../../img/RandomBtn.svg";

function RandomListPage(props) {
  const tag = "RandomListPage";

  const onClick = () => {
    for (let p of props.Plist) {
      if (p.quiz_state_type === "선택하지 않음") {
        return alert("선택하지 않은 문제가 있습니다.");
      }
    }
    props.setIsEmpty(!props.IsEmpty);
  };

  return (
    <div style={{ width: "80%", margin: "auto" }}>
      <div className="problem-info">
        <div className="problem-length">{props.Plist.length}문제</div>
        <div className="rerandom active">
          <span>한번 더!</span>
          <RandomBtn className="randombtn" onClick={onClick} />
        </div>
      </div>
      <List data={props.Plist} setList={props.setPlist} name="Random" />
      <div className="pslist-link">
        <a href="/mypslist">문제 목록 보러가기</a>
      </div>
    </div>
  );
}

export default RandomListPage;
