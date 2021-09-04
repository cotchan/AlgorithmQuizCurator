import React, {useEffect, useState} from "react";
import {useCookies} from "react-cookie";
import ProblemResetBtn from "../Problem/ProblemResetBtn";
import axios from "axios";
import {useDispatch} from "react-redux";
import {changePsState} from "../../../_actions/ps_action";

function SolvedList({data = [], setList, setPsList}) {
  const tag = "SolvedList";
  const [Type, setType] = useState({});
  const [cookies, setCookie] = useCookies(["key"]);
  const dispatch = useDispatch();
  const key = cookies.key;

  useEffect(() => {
    console.log(tag, data);

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

  const onClick = (quiz_number) => {
    console.log(tag, "click");

    //삭제
    setPsList((prevPlist) => {
      return prevPlist.map((p, index) => {
        if (p.quiz_number === quiz_number) {
          return prevPlist.splice(index, 1);
        }
        return p;
      });
    });

    let body = {
      quiz_states: [
        {
          quiz_number: quiz_number,
          state_type: 0,
        },
      ],
    };

    dispatch(changePsState(body, key)).then(() => {
      console.log(tag, "삭제 Success");
    });
  };

  return (
    <ul className="problemlist" style={{width: "100%"}}>
      {data.length <= 0
        ? ""
        : data.map((p) => (
            <li key={`${p.quiz_number}`}>
              <ProblemResetBtn
                problem={p}
                stateTypes={Type}
                onClick={onClick}
              />
            </li>
          ))}
    </ul>
  );
}

export default SolvedList;
