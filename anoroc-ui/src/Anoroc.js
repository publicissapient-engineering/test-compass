import React from "react";
import Header from "./components/menu/Header";
import { BrowserRouter, Route, Switch, Redirect, Link } from "react-router-dom";
import Application from "./components/application/Application";
import Feature from "./components/feature/Feature";
import EmptyHome from "./components/common/EmptyHome";
import Debug from "./components/debug/Debug";
import Run from "./components/runs/Run";
import BusinessScenario from "./components/businessScenario/BusinessScenario";
import Environment from "./components/environment/Environment";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";
import Loading from "./components/common/Loading";
import AlertMessage from "./components/common/AlertMessage";
import GlobalObject from "./components/globalObject/GlobalObject";
import Reports from "./components/reports/Reportportal";
import keycloak from "./keycloak";
import { useKeycloak } from "@react-keycloak/web";

const Anoroc = () => {
  const { keycloak, initialized } = useKeycloak();

  const redirectComponent = () => {
    if (initialized && !keycloak.authenticated) {
      return <Redirect to="/" />;
    }
    return "";
  };

  return (
    <Grid item xs={12}>
      <Box>
        <BrowserRouter>
          <Header />
          <Loading />
          <AlertMessage />
          {redirectComponent()}
          <Switch>
            <Route exact path="/" component={EmptyHome} />
            <Route exact path="/applications" component={Application} />
            <Route exact path="/features" component={Feature} />
            <Route exact path="/debug" component={Debug} />
            <Route exact path="/runs" component={Run} />
            <Route exact path="/globalObjects" component={GlobalObject} />
            <Route exact path="/environments" component={Environment} />
            <Route
              exact
              path="/business-scenario"
              component={BusinessScenario}
            />
            <Route exact path="/reports" component={Reports} />
          </Switch>
        </BrowserRouter>
      </Box>
    </Grid>
  );
};

export default Anoroc;
