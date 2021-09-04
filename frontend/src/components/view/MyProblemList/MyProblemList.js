import React, {useEffect, useState} from "react";
import {useCookies} from "react-cookie";
import axios from "axios";
import {getTotalList} from "../../../_actions/ps_action";
import {TabType, TabLabel} from "../../../constants/tabs.js";
import {formatRelativeDate} from "../../../utils/format";
import charImg from "../../../img/chart.png";
import List from "../List/List";
import SolvedList from "../List/SolvedList";
import UnSolvedSection from "./UnSolvedSection/UnSolvedSection";
import {useDispatch} from "react-redux";

function MyProblemList() {
  const tag = "MyProblemList";
  const [cookies, setCookie] = useCookies(["key"]);
  const key = cookies.key;
  const dispatch = useDispatch();

  const [PsList, setPsList] = useState([]);
  const [SelectedTab, setSelectedTab] = useState("전체 목록");

  /* 문제 state 별 분류*/
  const [SolvedArr, setSolvedArr] = useState([]);
  const [NotSolvedList, setNotSolvedList] = useState([]);
  const [NtcList, setNtcList] = useState([]);
  const [TimeOverList, setTimeOverList] = useState([]);

  /** 문제 목록 받아오기 */
  useEffect(() => {
    console.log(tag);
    dispatch(getTotalList(key)).then((response) => {
      const data = response.payload;
      console.log();
      if (data.success) {
        setPsList(() => data.response);
      }
    });
  }, [dispatch, key]);

  useEffect(() => {
    console.log(tag, "list changed");

    // todo (reducer로 처리)
    const solvedArr = [];
    const ntcList = [];
    const timeOverList = [];
    const notSolvedList = [];

    if (PsList.length === 0) {
      return;
    }
    PsList.forEach((ps) => {
      switch (ps.quiz_state_code) {
        case 2:
          ntcList.push(ps);
          break;
        case 3:
          notSolvedList.push(ps);
          break;
        case 4:
          timeOverList.push(ps);
          break;
        case 5:
          console.log("solved", ps);
          solvedArr.push(ps);
          break;
        default:
      }
    });
    console.log("solvedArr", solvedArr);
    setSolvedArr(solvedArr); //임시
    setNotSolvedList(notSolvedList);
    setNtcList(ntcList);
    setTimeOverList(timeOverList);
  }, [PsList]);

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
            {Object.values(TabLabel).map((tabType) => (
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
        {SelectedTab === TabType.TOTAL && (
          <section className={SelectedTab}>
            <List data={PsList} setList={setPsList} name="FullList" />
          </section>
        )}
        {SelectedTab === TabType.UNSOLVED && (
          <UnSolvedSection
            NtcList={NtcList}
            TimeOverList={TimeOverList}
            NotSolvedList={NotSolvedList}
            setPsList={setPsList}
          />
        )}
        {SelectedTab === TabType.SOLVED && (
          <section className={SelectedTab}>
            <SolvedList
              data={SolvedArr}
              setList={setSolvedArr}
              setPsList={setPsList}
            />
          </section>
        )}
      </div>
    </div>
  );
}

export default MyProblemList;
