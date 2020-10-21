import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Button from "@material-ui/core/Button";
import Fab from "@material-ui/core/Fab";
import IconButton from "@material-ui/core/IconButton";
import AssignmentIcon from "@material-ui/icons/Assignment";
import DirectionsRunIcon from "@material-ui/icons/DirectionsRun";
import AppsIcon from "@material-ui/icons/Apps";
import BusinessCenterIcon from "@material-ui/icons/BusinessCenter";
import ImportantDevicesIcon from "@material-ui/icons/ImportantDevices";
import FileCopyIcon from "@material-ui/icons/FileCopy";
import AssessmentIcon from "@material-ui/icons/Assessment";
import Box from "@material-ui/core/Box";
import ToolTip from "@material-ui/core/Tooltip";
import EnvironmentSelector from "./EnvironmentSelector";
import EcoRoundedIcon from "@material-ui/icons/EcoRounded";
import CancelIcon from "@material-ui/icons/Cancel";
import { useKeycloak } from "@react-keycloak/web";
import { setToken, clear } from "../../thunks/common/auth";
import { Link, BrowserRouter } from "react-router-dom";
import Typography from "@material-ui/core/Typography";
import AcUnitIcon from "@material-ui/icons/AcUnit";

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1
  },
  title: {
    flexGrow: 1
  },
  margin: {
    margin: theme.spacing(1)
  },
  marginRight: {
    position: "absolute",
    right: theme.spacing(2)
  },
  marginEnvSelect: {},
  extendedIcon: {
    marginRight: theme.spacing(1)
  }
}));

const Header = () => {
  const classes = useStyles();
  const { keycloak } = useKeycloak();

  const logout = () => {
    clear();
    keycloak.logout();
  };

  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Toolbar>
        <IconButton edge="start" color="inherit" aria-label="menu">
          <AcUnitIcon />
        </IconButton>
        <Button color="inherit" href="/">
          <Typography variant="h6" className={classes.title}>
            TEST Compass
          </Typography>
        </Button>
          {keycloak && keycloak.authenticated && (
            <React.Fragment>
              {setToken(keycloak.token)}
              <Button
                color="inherit"
                startIcon={<AssignmentIcon />}
                className={classes.margin}
                component={Link}
                to={"/features"}
              >
                Features
              </Button>
              <Button
                color="inherit"
                startIcon={<BusinessCenterIcon />}
                className={classes.margin}
                component={Link}
                to={"/business-scenario"}
              >
                Business Scenario
              </Button>
              <Button
                color="inherit"
                startIcon={<DirectionsRunIcon />}
                className={classes.margin}
                component={Link}
                to={"/runs"}
              >
                Runs
              </Button>
              <Button
                color="inherit"
                startIcon={<AssessmentIcon />}
                className={classes.margin}
                component={Link}
                to={"/reports"}
              >
                Reports
              </Button>
              <Box className={classes.marginEnvSelect}>
                <EnvironmentSelector />
              </Box>
              <Box className={classes.marginRight}>
                <ToolTip title="Object Repository">
                  <Fab
                    color="primary"
                    aria-label="add"
                    size="small"
                    className={classes.margin}
                    component={Link}
                    to={"/globalObjects"}
                  >
                    <FileCopyIcon />
                  </Fab>
                </ToolTip>
                <ToolTip title="Environments">
                  <Fab
                    color="primary"
                    aria-label="add"
                    size="small"
                    className={classes.margin}
                    component={Link}
                    to={"/environments"}
                  >
                    <ImportantDevicesIcon />
                  </Fab>
                </ToolTip>
                <ToolTip title="Application">
                  <Fab
                    color="primary"
                    aria-label="add"
                    size="small"
                    className={classes.margin}
                    component={Link}
                    to={"/applications"}
                  >
                    <EcoRoundedIcon />
                  </Fab>
                </ToolTip>
                <Button
                  color="inherit"
                  startIcon={<CancelIcon color="secondary" />}
                  onClick={event => {
                    logout();
                  }}
                >
                  Logout
                </Button>
              </Box>
            </React.Fragment>
          )}
        </Toolbar>
      </AppBar>
    </div>
  );
};

export default Header;
