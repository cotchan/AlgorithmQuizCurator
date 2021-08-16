import React from "react";
import {withCookies} from "react-cookie";

function NavBar(props) {
  const {key, name} = props.cookiesInfo;

  return (
    <>
      <header className="sticky">
        <div>
          <a id="logo" href="/">
            ALGOL
          </a>
        </div>
        <div className="end" style={{display: "flex"}}>
          {key ? (
            <ul className="navigation">
              <li>
                <a href="/random">Random</a>
              </li>
              <li>
                <a href="/randomlist">Result</a>
              </li>
              <li>
                <a href="/mypslist">PsList</a>
              </li>
              <li>
                <a href="/mychart">My Chart</a>
              </li>
              <li>
                <a href="/rank">Ranking</a>
              </li>
            </ul>
          ) : (
            <div className="signin" style={{padding: "12px 40px"}}>
              <a href="/signin">
                <span style={{transform: "translateY(-25%)"}}>sign in</span>
              </a>
            </div>
          )}

          <div className={`${key ? "login" : "logout"}`}>
            <a href={`${key ? "/mypage" : "/signup"}`}>
              <span style={{transform: "translateY(-25%)"}}>
                {key ? `${name}` : "sign up"}
              </span>
            </a>
          </div>
        </div>
      </header>
    </>
  );
}

export default withCookies(NavBar);
