import React, {useEffect, useState} from "react";
import {useCookies} from "react-cookie";
import Problem from "../Problem/Problem";
import axios from "axios";
import {useDispatch} from "react-redux";
import {changePsState} from "../../../_actions/ps_action";

function List({data = [], setList}) {
  const tag = "List";
  const [Type, setType] = useState({});
  const [cookies, setCookie] = useCookies(["key"]);
  const dispatch = useDispatch();
  const key = cookies.key;

  useEffect(() => {
    /** 문제 상태 목록 받아오기 */
    axios.get("/api/problems/state-types").then((response) => {
      let types = {};

      if (response.data.success) {
        response.data.response.forEach((type) => {
          types[type.desc_kor] = type.state_code;
        });
        setType(types);
      }
    });
  }, []);

  const onChange = (nextstate, quiz_number) => {
    console.log("onChange", nextstate, quiz_number);
    setList((prevPlist) => {
      return prevPlist.map((p) => {
        if (p.quiz_number === quiz_number) {
          return {
            ...p,
            quiz_state_code: Type[nextstate],
            quiz_state_desc: nextstate,
          };
        }
        return p;
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

    dispatch(changePsState(body, key)).then((response) => {
      console.log(tag, "chang Ps Success");
    });
  };

  return (
    <ul className="problemlist" style={{width: "100%"}}>
      {data.length <= 0
        ? ""
        : data.map((p) => (
            <li key={`${p.quiz_number}`}>
              <Problem problem={p} stateTypes={Type} onChange={onChange} />
            </li>
          ))}
    </ul>
  );
}

export default List;
