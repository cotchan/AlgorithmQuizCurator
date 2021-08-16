import {React, useEffect, useRef} from "react";
import {withCookies, useCookies} from "react-cookie";

function NavBar(props) {
  const {key, name} = props.cookiesInfo;
  return (
    <>
      <header
        className="sticky"
        style={{
          display: "flex",
          backgroundColor: "#4D4ACF",
          height: "64px",
          padding: "24px 42px",
        }}
      >
        <div style={{flex: "none"}}>
          <a
            style={{
              color: "#F30707",
              fontWeight: "bold",
              fontSize: "32px",
              textDecoration: "none",
              height: "100%",
              width: "100%",
              verticalAlign: "middle",
              display: "flex",
              alignItems: "center",
            }}
            href="/"
          >
            ALGOL
          </a>
        </div>
        <div
          className="end"
          style={{flex: "none", marginLeft: "auto", display: "flex"}}
        >
          {key ? (
            ""
          ) : (
            <div style={{padding: "12px 40px"}}>
              <a
                href="/signin"
                style={{
                  color: "#ffffff",
                  fontSize: "24px",
                  textDecoration: "none",
                  justifyContent: "flex-end",
                  alignContent: "center",
                }}
              >
                <span style={{transform: "translateY(-25%)"}}>sign in</span>
              </a>
            </div>
          )}

          <div
            style={{
              background: "black",
              color: "white",
              textAlign: "center",
              borderRadius: "5px",
              display: "flex",
              alignItems: "center",
              boxSizing: "border-box",
            }}
          >
            <a
              href={`${key ? "/" : "/signup"}`}
              style={{
                color: "#ffffff",
                fontSize: "24px",
                textDecoration: "none",
                height: "100%",
                width: "100%",
                padding: "12px 40px",
                verticalAlign: "middle",
                display: "flex",
                alignItems: "center",
              }}
            >
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
