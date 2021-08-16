import {GET_RANDOWMQ, GET_UNSELECTQ} from "./types.js";
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
