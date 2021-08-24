import React, { useEffect, useState } from 'react';
import {useCookies} from "react-cookie";
import axios from 'axios';
import {formatRelativeDate} from '../../../utils/format';
import charImg from '../../../img/chart.png';

import Problem from '../Problem/Problem';

function MyProblemList() {

  const tag= "MyProblemList";
  const [cookies, setCookie] = useCookies(["key"]);
  const key = cookies.key;
  const [Type, setType] = useState({});
  const [PsList, setPsList] = useState([]);
  const [SelectedTab, setSelectedTab] = useState("전체 목록");
  const [SolvedList, setSolvedList] = useState([]);
  const [NtcList, setNtcList] = useState([]);
  const [TimeOverList, setTimeOverList] = useState([]);


  const navList = ["전체 목록", "풀이 미완료", "풀이 완료"]

  /**공통 부분 */
  useEffect(() => {
    console.log(tag);

    /** 문제 상태 목록 받아오기 */
    axios.get("/api/problems/state-types").then((response) => {
      console.log(tag, response);

      if (response.data.success) {
        let types = {};
        response.data.response.forEach((type) => {
          //수정 필요 key, value 바꾸기
          types[type.desc_kor] = type.state_code;
        });
        setType(types);
      }
    });
  }, []);

  /** 문제 목록 받아오기 */
  useEffect(() => {
    console.log(tag);

    axios.get("/api/myPage/solved-problems/",
    {
      headers: {
        api_key: `Bearer ${key}`,
      },
    }
    ).then((response) => {
      console.log(tag, response);

      if (response.data.success) {
       console.log("success", response.data.response);
       /**
        * reg data 기준 정렬 추가
        */
        setPsList(response.data.response)
      }
    });
  }, []);

  
  const onChange = (nextstate, quiz_number) => {
    console.log(tag, "onChange", nextstate, quiz_number);
    setPsList((prevPlist) => {
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

  const onClick = (tabType) => {
    setSelectedTab(tabType)
  }


  return (
    <div className='mylist'>
      <div>
        <div className='mylist__header'>
          <div className='mylist_info'>
            <h1>나의 문제 목록</h1>
            <div className="mylist_chartlink" style={{display: "flex"}}>
              <img src={charImg}></img>
                <span style={{alignSelf: "flex-end", paddingBottom: "4px", fontWeight: "bolder"}}>차트로 파악하기</span>
            </div>
          </div>
          <ul className='mylist__nav'>
            {navList.map((tabType) => (
              <li key={tabType} className={SelectedTab == tabType ? "active" : ""}
              onClick={() => onClick(tabType)}>{tabType}</li>
            ))}
          </ul>
        </div>

        <section>
          <ul className="problemlist" style={{width: "100%"}}>
            {PsList.length <= 0 ? "": PsList.map((p) => (
              <li key={p.quiz_number}>
                <Problem problem={p} stateTypes={Type} onChange={onChange} />
              </li>
            ))}
          </ul>
      </section>
      </div>
    </div>
  );
}

export default MyProblemList;