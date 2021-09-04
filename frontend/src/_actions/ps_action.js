import {
  GET_RANDOWMQ,
  GET_UNSELECTQ,
  CHANGE_STATE,
  GET_PSLIST,
} from "./types.js";
import axios from "axios";

export function getRandomQ(dataToSubmit, apiToken) {
  const request = axios
    .post(`/api/problems/pick`, dataToSubmit, {
      headers: {
        api_key: `Bearer ${apiToken}`,
      },
    })
    .then((response) => response.data);

  return {
    type: GET_RANDOWMQ,
    payload: request,
  };
}

export function getUnselectedQ(apiToken) {
  const request = axios
    .get(`/api/problems`, {
      headers: {
        api_key: `Bearer ${apiToken}`,
      },
    })
    .then((response) => response.data);

  return {
    type: GET_UNSELECTQ,
    payload: request,
  };
}

export function changePsState(body, apiToken) {
  const request = axios
    .put("/api/problems/solved-check", body, {
      headers: {
        api_key: `Bearer ${apiToken}`,
      },
    })
    .then((response) => response.data);

  return {
    type: CHANGE_STATE,
    payload: request,
  };
}

export function getTotalList(apiToken, setList) {
  const request = axios
    .get("/api/myPage/solved-problems/", {
      headers: {
        api_key: `Bearer ${apiToken}`,
      },
    })
    .then((response) => response.data);

  return {
    type: GET_PSLIST,
    payload: request,
  };
}
