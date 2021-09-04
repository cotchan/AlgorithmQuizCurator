import {func} from "prop-types";
import {GET_UNSELECTQ, CHANGE_STATE, GET_PSLIST} from "../_actions/types";

export default function ps_reducer(state = {}, action) {
  switch (action.type) {
    case GET_UNSELECTQ:
      return {...state, success: action.payload};
    case CHANGE_STATE:
      return {...state, success: action.payload};
    case GET_PSLIST:
      return {...state, success: action.payload};
    default:
      return state;
  }
}
