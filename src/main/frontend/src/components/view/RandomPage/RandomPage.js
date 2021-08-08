import React from "react";
import {useEffect} from "react";
import axios from "axios";

function RandomPage() {
  useEffect(() => {
    console.log("response");

    axios.get("/api/hcheck").then((response) => {
      console.log(response);
    });
  }, []);

  return <div>RandomPage</div>;
}

export default RandomPage;
