import React from "react";

function NavBar() {
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
              fontSize: "24px",
              textDecoration: "none",
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
              href="/signup"
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
              <span style={{transform: "translateY(-25%)"}}>sign up</span>
            </a>
          </div>
        </div>
      </header>
    </>
  );
}

export default NavBar;
