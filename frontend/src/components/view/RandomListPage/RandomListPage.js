import {React, useEffect, useState} from "react";
import Problem from "./Problem/Problem";
import axios from "axios";
import {useCookies} from "react-cookie";
import {ReactComponent as RandomBtn} from "../../../img/RandomBtn.svg";

function RandomListPage(props) {
  const tag = "RandomListPage";
  const [cookies, setCookie] = useCookies(["key"]);
  const key = cookies.key;
  const [Type, setType] = useState({});

  const onClick = () => {
    // console.log(tag, "onclick");

    for (let p of props.Plist) {
      if (p.quiz_state_type === "선택하지 않음") {
        return alert("선택하지 않은 문제가 있습니다.");
      }
    }
    props.setIsEmpty(!props.IsEmpty);
  };

  const onChange = (nextstate, quiz_number) => {
    console.log(tag, "onChange", nextstate, quiz_number);
    props.setPlist((prevPlist) => {
      return prevPlist.map((p) => {
        if (p.quiz_number === quiz_number) {
          return {...p, quiz_state_type: nextstate};
        }
        return {...p};
      });
    });

    let stateEnum = Type[nextstate];
    let body = {
      quiz_states: [
        {
          quiz_number: quiz_number,
          state_type: stateEnum,
        },
      ],
    };

    // console.log(body);

    axios.put("/api/problems/solved-check", body, {
      headers: {
        api_key: `Bearer ${key}`,
      },
    });
  };

  useEffect(() => {
    console.log(tag, props);

    /** 문제 상태 목록 받아오기 */
    axios.get("/api/problems/state-types").then((response) => {
      console.log(tag, response);

      if (response.data.success) {
        let types = {};
        response.data.response.forEach((type) => {
          types[type.desc_kor] = type.state_code;
        });
        setType(types);
      }
    });
  }, []);

  return (
    <div style={{width: "80%", margin: "auto"}}>
      <div className="problem-info">
        <div className="problem-length">{props.Plist.length}문제</div>
        <div className="rerandom active">
          <span>한번 더!</span>
          <RandomBtn className="randombtn" onClick={onClick} />
        </div>
      </div>
      <ul className="problemlist" style={{width: "100%"}}>
        {props.Plist.map((p) => (
          <li key={p.quiz_number}>
            <Problem problem={p} stateTypes={Type} onChange={onChange} />
          </li>
        ))}
      </ul>
      <div className="pslist-link">
        <a href="/mypslist">문제 목록 보러가기</a>
      </div>
    </div>
  );
}

export default RandomListPage;
