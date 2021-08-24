import React, { useEffect, useState } from "react";
import { useCookies } from "react-cookie";
import ProblemResetBtn from "../Problem/ProblemResetBtn";
import axios from "axios";

function SolvedList({ data = [], setList, name }) {
  const tag = "SolvedList";
  const [Type, setType] = useState({});
  const [cookies, setCookie] = useCookies(["key"]);
  const key = cookies.key;

  useEffect(() => {
    console.log(tag, data);

    /** 문제 상태 목록 받아오기 */
    axios.get("/api/problems/state-types").then((response) => {
      let types = {};

      if (response.data.success) {
        response.data.response.forEach((type) => {
          //수정 필요 key, value 바꾸기
          types[type.desc_kor] = type.state_code;
        });
        setType(types);
      }
      console.log(types);
    });
  }, []);

  const onClick = (removed) => {
    //삭제
    setList((prevlist) => {
      //인덱스 찾기
      //해당 인덱스 문제 삭제
    });
  };

  const onChange = (nextstate, quiz_number) => {
    console.log(tag, "onChange", nextstate, quiz_number);
    setList((prevPlist) => {
      return prevPlist.map((p) => {
        if (p.quiz_number === quiz_number) {
          return { ...p, quiz_state_type: nextstate };
        }
        return { ...p };
      });
    });

    let stateEnum = Type[nextstate];
    console.log(tag, stateEnum);
    let body = {
      quiz_states: [
        {
          quiz_number: quiz_number,
          state_type: stateEnum,
        },
      ],
    };

    // console.log(body);

    axios
      .put("/api/problems/solved-check", body, {
        headers: {
          api_key: `Bearer ${key}`,
        },
      })
      .then((response) => {
        console.log(tag, "success", response);
      });
  };

  return (
    <ul className="problemlist" style={{ width: "100%" }}>
      {data.length <= 0
        ? ""
        : data.map((p) => (
            <li key={`${p.quiz_number}${name}`}>
              <ProblemResetBtn
                problem={p}
                stateTypes={Type}
                onChange={onChange}
                onClick={onClick}
              />
            </li>
          ))}
    </ul>
  );
}

export default SolvedList;
