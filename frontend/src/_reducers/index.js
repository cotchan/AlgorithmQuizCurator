import {combineReducers} from "redux";
import user from "./user_reducer";
import problem from "./ps_reducer";

const rootReducer = combineReducers({
  user,
  problem,
});

export default rootReducer;
