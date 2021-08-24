import React, { useEffect, useState } from "react";
import { useCookies } from "react-cookie";
import Problem from "../Problem/Problem";
import axios from "axios";

function List({ data = [], setList, name }) {
  const tag = "List";
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
              <Problem problem={p} stateTypes={Type} onChange={onChange} />
            </li>
          ))}
    </ul>
  );
}

export default List;
