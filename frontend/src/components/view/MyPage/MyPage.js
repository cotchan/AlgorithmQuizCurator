import axios from "axios";
import {React, useEffect} from "react";
import {useCookies} from "react-cookie";

function MyPage() {
  const tag = "MyPage";
  const [cookies, setCookie, removeCookie] = useCookies(["key"]);
  const key = cookies.key;

  useEffect(() => {
    console.log("mypage");
  }, []);

  const onButtonClickHandler = (e) => {
    console.log(key);
    axios
      .post("/api/user/logout", "", {
        headers: {
          api_key: `Bearer ${key}`,
        },
      })
      .then((response) => {
        console.log(tag, response);
        removeCookie("key");
      });
  };

  return (
    <div>
      <button className="">본인 정보 확인</button>
      <button className="">본인 정보 수정</button>
      <button className="logout-btn" onClick={onButtonClickHandler}>
        로그아웃
      </button>
    </div>
  );
}

export default MyPage;
