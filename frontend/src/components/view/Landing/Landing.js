import {React, useEffect, useState} from "react";
import {useDispatch} from "react-redux";
import {getUnselectedQ} from "../../../_actions/ps_action";
import {useCookies} from "react-cookie";
import RandomPage from "../RandomPage/RandomPage";
import RandomListPage from "../RandomListPage/RandomListPage";

function Landing(props) {
  const dispatch = useDispatch();

  const tag = "landing";
  const {Plist, setPlist} = props;
  const [IsEmpty, setIsEmpty] = useState(false);
  const [cookies, setCookie] = useCookies(["key"]);

  useEffect(() => {
    const key = cookies.key;
    console.log(tag, Plist, key);

    /** 사용자가 뽑았지만 선택하지 않은 문제 가져오기 */
    dispatch(getUnselectedQ(key))
      .then((response) => {
        let result = response.payload;
        if (result.success) {
          console.log(tag, result.response);
          //임시 코드
          setPlist(result.response.slice(0, 5));
          // setPlist([]);
        }
      })
      .catch((e) => {
        /** 로그인 안내  */
        console.log(key);
        console.log(e, "로그인 이전");
      });
  }, [IsEmpty]);

  return (
    <>
      {Plist.length === 0 ? (
        <RandomPage {...props} />
      ) : (
        <RandomListPage
          {...props}
          Plist={Plist}
          setPlist={setPlist}
          IsEmpty={IsEmpty}
          setIsEmpty={setIsEmpty}
        />
      )}
    </>
  );
}

export default Landing;
