import React, {useEffect, useState} from "react";
import {select} from "d3";
import axios from "axios";
import {useCookies} from "react-cookie";

function MyChart() {
  const [cookies, setCookie] = useCookies(["key"]);
  const key = cookies.key;

  const [data, setData] = useState({});

  useEffect(() => {
    axios
      .get("/api/myPage/solved-dates/", {
        headers: {
          api_key: `Bearer ${key}`,
        },
        params: {
          "page-size": 15,
          "page-no": 1,
        },
      })
      .then((res) => {
        const result = res.data;
        console.log("result", result);
        if (result.success) {
          const data = result.response;
          setData((prev) => {
            data.forEach((record) => {
              const date = record.regdate.split(" ")[0];

              if (!prev[date]) {
                prev[date] = [record.quiz_title];
              } else {
                prev[date].push(record.quiz_title);
              }
            });
            return prev;
          });
        }
      });
  }, []);

  return (
    <div className="mylist" style={{display: "flex"}}>
      <div className="left">
        <div>지금까지 이만큼 풀었어요</div>
        <div>Calendar</div>
        <ul>
          {Object.entries(data).map((date, ps) => {
            return <li>{date}</li>;
          })}
        </ul>
      </div>
      <div className="right">
        <div>차트로 보기</div>
        <div>MyChart</div>
      </div>
    </div>
  );
}

export default MyChart;
