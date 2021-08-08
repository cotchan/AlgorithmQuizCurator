import {LOGIN_USER, JOIN_USER, GET_QUESTION, DOUBLE_CHECK_ID} from "./types.js";
import axios from "axios";

export function loginUser(dataToSubmit) {
  const request = axios
    .post("/api/user/login", dataToSubmit)
    .then((response) => response.data)
    .catch((error) => error);
  console.log("userACTION");

  return {
    type: LOGIN_USER,
    payload: request,
  };
}

export function joinUser(dataToSubmit) {
  const request = axios
    .post("/api/user/join", dataToSubmit)
    .then((response) => response.data);
  return {
    type: JOIN_USER,
    payload: request,
  };
}

export function getQuestion() {
  const request = axios
    .post("/api/user/verify-questions", {})
    .then((response) => response);

  return {
    type: GET_QUESTION,
    payload: request,
  };
}

export function doubleCheckerID(userId) {
  const request = axios
    .post(`/api/user/dup-check-by-userId/${userId}`)
    .then((response) => response);

  return {
    type: DOUBLE_CHECK_ID,
    payload: request,
  };
}
