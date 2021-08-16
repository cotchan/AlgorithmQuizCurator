import React, {useState} from "react";
import {useEffect} from "react";
import axios from "axios";
import {ReactComponent as RandomBtn} from "../../../img/RandomBtn.svg";

function RandomPage() {
  const [Hover, setHover] = useState(false);

  const toggleHover = () => setHover(!Hover);

  useEffect(() => {
    console.log("response");

    axios.get("/api/hcheck").then((response) => {
      console.log(response);
    });
  }, []);

  const onClickHandler = (event) => {
    console.log("onclick");
    /**
     * 랜덤 버튼
     */
  };

  return (
    <div
      className="random"
      style={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        width: "835px",
        height: "100%",
        margin: "0 auto",
      }}
    >
      <div
        className="logo"
        style={{
          padding: "36px 0px 12px",
        }}
      ></div>
      <div
        style={{
          display: "flex",
          width: "100%",
          color: "#FFFFFF",
          justifyContent: "center",
        }}
      >
        <span
          style={{
            fontWeight: "bold",
            fontSize: "42px",
            textShadow: "7px 1px 4px rgba(0, 0, 0, 0.25)",
          }}
        >
          매일 알고리즘 문제를 고르는 게 귀찮다면?
        </span>
      </div>
      <form
        style={{display: "flex", flexDirection: "column", alignItems: "center"}}
      >
        <div
          style={{
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            background: " #C4C4C4",
            border: "2px solid #000000",
            boxShadow: "10px 15px 4px rgba(0, 0, 0, 0.25)",
            borderRadius: "22px",
            width: "200px",
            padding: "0 80px",
            height: "115px",
            marginTop: "63px",
          }}
        >
          <input
            style={{
              border: "none",
              background: "#C4C4C4",
              width: "20%",
              height: "80%",
              fontSize: "42px",
            }}
          />
          <span style={{fontSize: "42px"}}>개</span>
        </div>

        <RandomBtn
          onMouseEnter={toggleHover}
          onMouseLeave={toggleHover}
          className={Hover ? "" : "btn-hover"}
          onClick={onClickHandler}
          style={{marginTop: "63px"}}
        />
      </form>
    </div>
  );
}

export default RandomPage;
