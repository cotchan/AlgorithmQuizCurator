import "./App.css";

import {BrowserRouter as Router, Switch, Route} from "react-router-dom";
import {React, useState} from "react";

import Landing from "./components/view/Landing/Landing";
import Navbar from "./components/view/NavBar/NavBar.js";
import SignIn from "./components/view/SignIn/SignIn.js";
import SignUp from "./components/view/SignUp/SignUp.js";
import MyProblemList from "./components/view/MyProblemList/MyProblemList";
import MyChart from "./components/view/MyChart/MyChart";
import Ranking from "./components/view/Ranking/Ranking";
import MyPage from "./components/view/MyPage/MyPage";
import {useCookies} from "react-cookie";

function App() {
  const [keycookies, setkeyCookie, removekeyCookie] = useCookies(["key"]);
  const [namecookies, setnameCookie, removenameCookie] = useCookies(["name"]);
  const [Plist, setPlist] = useState([]);

  return (
    <Router>
      <Navbar cookiesInfo={(keycookies, namecookies)} />
      <div>
        <Switch>
          <Route
            exact
            path="/"
            render={(routerProps) => (
              <Landing
                {...routerProps}
                Plist={Plist}
                setPlist={setPlist}
                cookiesInfo={(keycookies, namecookies)}
              />
            )}
          />
          <Route
            exact
            path="/signin"
            render={(routerProps) => (
              <SignIn
                {...routerProps}
                cookiesInfo={(keycookies, namecookies)}
                setkeyCookie={setkeyCookie}
                setnameCookie={setnameCookie}
              />
            )}
          />
          <Route exact path="/signup" component={SignUp} />
          <Route exact path="/mypage" component={MyPage} />
          <Route exact path="/mypslist" component={MyProblemList} />
          <Route exact path="/mychart" component={MyChart} />
          <Route exact path="/rank" component={Ranking} />
        </Switch>
      </div>
    </Router>
  );
}

export default App;
