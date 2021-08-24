import React, { useEffect, useState } from "react";
import { useCookies } from "react-cookie";
import axios from "axios";
import { formatRelativeDate } from "../../../utils/format";
import charImg from "../../../img/chart.png";
import List from "../List/List";
import SolvedList from "../List/SolvedList";
import UnSolvedSection from "./UnSolvedSection/UnSolvedSection";

function MyProblemList() {
  const tag = "MyProblemList";
  const [cookies, setCookie] = useCookies(["key"]);
  const key = cookies.key;

  const [PsList, setPsList] = useState([]);
  const [SelectedTab, setSelectedTab] = useState("전체 목록");

  /* state 별 분류*/
  const [SolvedArr, setSolvedArr] = useState([]);
  const [NotSolvedArr, setNotSolvedArr] = useState([]);
  const [NtcList, setNtcList] = useState([]);
  const [TimeOverList, setTimeOverList] = useState([]);

  const navList = ["전체 목록", "풀이 미완료", "풀이완료"];

  /** 문제 목록 받아오기 */
  useEffect(() => {
    console.log(tag);

    axios
      .get("/api/myPage/solved-problems/", {
        headers: {
          api_key: `Bearer ${key}`,
        },
      })
      .then((response) => {
        console.log(tag, response);

        if (response.data.success) {
          let fullList = response.data.response;
          console.log("success", response.data.response);
          /**
           * todo : reg data 기준 정렬 추가
           */

          // 전체 목록
          const notSolvedArr = [];
          const solvedArr = [];
          const ntcList = [];
          const timeOverList = [];

          setPsList(fullList);
          fullList.forEach((ps) => {
            console.log("카테고리", ps.quiz_state_code);

            switch (ps.quiz_state_code) {
              case 2:
                ntcList.push(ps);
                break;
              case 3:
                notSolvedArr.push(ps);
                break;
              case 4:
                timeOverList.push(ps);
                break;
              case 5:
                solvedArr.push(ps);
                break;
              default:
            }
          });

          setSolvedArr(timeOverList); //임시
          setNotSolvedArr(notSolvedArr);
          setNtcList(ntcList);
          setTimeOverList(timeOverList);
        }
      });
  }, []);

  const onClick = (tabType) => {
    setSelectedTab(tabType);
  };

  return (
    <div className="mylist">
      <div>
        <div className="mylist__header">
          <div className="mylist_info">
            <h1>나의 문제 목록</h1>
            <div className="mylist_chartlink">
              <img alt="chart-icon" src={charImg}></img>
              <a href="/">차트로 파악하기</a>
            </div>
          </div>
          <ul className="mylist__nav">
            {navList.map((tabType) => (
              <li
                key={tabType}
                className={SelectedTab === tabType ? "active" : ""}
                onClick={() => onClick(tabType)}
              >
                {tabType}
              </li>
            ))}
          </ul>
        </div>
        {SelectedTab === "전체 목록" && (
          <section className={SelectedTab}>
            <List data={PsList} setList={setPsList} name="FullList" />
          </section>
        )}
        {SelectedTab === "풀이 미완료" && (
          <UnSolvedSection
            NtcList={NtcList}
            setNtcList={setNtcList}
            TimeOverList={TimeOverList}
            setTimeOverList={setTimeOverList}
            NotSolvedArr={NotSolvedArr}
            setNotSolvedArr={setNotSolvedArr}
          />
        )}
        {SelectedTab === "풀이완료" && (
          <section className={SelectedTab}>
            <SolvedList
              data={SolvedArr}
              setList={setSolvedArr}
              name="SolvedArr"
            />
          </section>
        )}
      </div>
    </div>
  );
}

export default MyProblemList;
