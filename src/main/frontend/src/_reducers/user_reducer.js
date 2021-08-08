import {
  LOGIN_USER,
  JOIN_USER,
  GET_QUESTION,
  DOUBLE_CHECK_ID,
  // AUTH_USER
} from "../_actions/types";

export default function user_reducer(state = {}, action) {
  switch (action.type) {
    case LOGIN_USER:
      console.log("user_reducer");
      console.log(action.payload);

      return {...state, loginSuccess: action.payload};
    case JOIN_USER:
      return {...state, joinSuccess: action.payload};
    case GET_QUESTION:
      return {...state, getQSuccess: action.payload};
    // case AUTH_USER:
    //     return { ...state, userData: action.payload } //모든 유저 데이터
    default:
      return state;
  }
}
