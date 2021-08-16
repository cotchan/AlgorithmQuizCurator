import {GET_UNSELECTQ} from "../_actions/types";

export default function ps_reducer(state = {}, action) {
  switch (action.type) {
    case GET_UNSELECTQ:
      return {...state, success: action.payload};
    default:
      return state;
  }
}
