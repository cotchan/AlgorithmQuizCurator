import "./App.css";

import {BrowserRouter as Router, Switch, Route} from "react-router-dom";
import {React, useEffect} from "react";

import Navbar from "./components/view/NavBar/NavBar.js";
import SignIn from "./components/view/SignIn/SignIn.js";
import SignUp from "./components/view/SignUp/SignUp.js";
import RandomPage from "./components/view/RandomPage/RandomPage";
import RandomListPage from "./components/view/RandomListPage/RandomListPage";
import MyProblemList from "./components/view/MyProblemList/MyProblemList";
import MyChart from "./components/view/MyChart/MyChart";
import {withCookies, Cookies, useCookies} from "react-cookie";

function App() {
  const [keycookies, setkeyCookie, removekeyCookie] = useCookies(["key"]);
  const [namecookies, setnameCookie, removenameCookie] = useCookies(["name"]);

  // test;
  useEffect(() => {
    removekeyCookie("key");
    removenameCookie("name");
  }, []);

  return (
    <Router>
      <Navbar cookiesInfo={(keycookies, namecookies)} />
      <div>
        <Switch>
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
          <Route exact path="/random" component={RandomPage} />
          <Route exact path="/randomlist" component={RandomListPage} />
          <Route exact path="/mypslist" component={MyProblemList} />
          <Route exact path="/mychart" component={MyChart} />
        </Switch>
      </div>
    </Router>
  );
}

export default App;
