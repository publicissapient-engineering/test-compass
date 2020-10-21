import React, { useEffect } from "react";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import ListItemText from "@material-ui/core/ListItemText";
import Avatar from "@material-ui/core/Avatar";
import { makeStyles } from "@material-ui/core/styles";
import { green, red } from "@material-ui/core/colors";
import TablePagination from "@material-ui/core/TablePagination";
import { map } from "lodash";
import { connect } from "react-redux";
import {
  setSelectedRunRequest,
  listRunRequest,
  getRunReport
} from "../../thunks/run/thunks";
import {
  getRuns,
  getRunCount,
  getPage,
  getSize,
  getSelectedRun
} from "../../selectors/run/selector";
import DirectionsRunIcon from "@material-ui/icons/DirectionsRun";
import DisplayDate from "../common/DisplayDate";
import FormGroup from "@material-ui/core/FormGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Switch from "@material-ui/core/Switch";
import Typography from "@material-ui/core/Typography";
import CircularProgress from "@material-ui/core/CircularProgress";
import HighlightOffIcon from "@material-ui/icons/HighlightOff";
import LinearProgress from "@material-ui/core/LinearProgress";
import BusinessCenterIcon from "@material-ui/icons/BusinessCenter";
import AssignmentIcon from "@material-ui/icons/Assignment";
import renderHTML from "react-render-html";
import { REPORT_URL } from "../../EnvVariables";

const RunList = ({
  selectedRun,
  loadRunsFromServer,
  setSelectedRun,
  runs = [],
  count = 0,
  page = 0,
  size = 10
}) => {
  useEffect(() => {
    loadRunsFromServer(page, size);
  }, [loadRunsFromServer, page, size]);

  useEffect(() => {
    if (selectedRun) {
      setReportUrl(REPORT_URL + selectedRun.report_url);
    }
  }, [selectedRun]);

  const [selectedIndex, setSelectedIndex] = React.useState(0);
  const [reportUrl, setReportUrl] = React.useState("");
  const [autoRefresh, setAutoRefresh] = React.useState({
    status: false
  });

  const [intervalId, setIntervalId] = React.useState(0);

  const handleListItemClick = index => {
    setSelectedIndex(index);
  };

  const handleAutoRefreshChange = event => {
    setAutoRefresh({ status: event.target.checked });
    if (!autoRefresh.status) {
      setIntervalId(setInterval(autoRefreshOnInterval, 5000));
    } else {
      clearInterval(intervalId);
    }
  };

  const autoRefreshOnInterval = () => {
    loadRunsFromServer(page, size);
    setSelectedIndex(0);
  };

  const onChangePage = (event, pageSelected) => {
    loadRunsFromServer(pageSelected, size);
    setSelectedIndex(0);
  };

  const useStyles = makeStyles(theme => ({
    root: {
      width: "100%",
      backgroundColor: theme.palette.background.paper
    },
    red: {
      backgroundColor: red[500]
    },
    green: {
      backgroundColor: green[500]
    },
    autoRefreshBg: {
      color: "#ffffff"
    },
    autoRefreshPosition: {
      position: "absolute",
      top: theme.spacing(3),
      right: theme.spacing(58)
    }
  }));

  const classes = useStyles();

  return (
    <Box>
      <Grid container>
        <Grid item xs={4}>
          <Box mr={1} mt={1}>
            <Box>
              <List component="nav">
                {map(runs, (run, index) => (
                  <ListItem
                    key={run.id}
                    divider
                    button
                    selected={selectedIndex === index}
                    onClick={event => {
                      handleListItemClick(index);
                      setSelectedRun(runs[index]);
                    }}
                  >
                    <ListItemAvatar>
                      {runs[index].status === "FAILURE" && (
                        <Avatar className={classes.red}>
                          {runs[index].run_type === "BUSINESS_SCENARIO" && (
                            <BusinessCenterIcon />
                          )}
                          {runs[index].run_type === "FEATURE" && (
                            <AssignmentIcon />
                          )}
                        </Avatar>
                      )}
                      {runs[index].status === "SUCCESS" && (
                        <Avatar className={classes.green}>
                          {runs[index].run_type === "BUSINESS_SCENARIO" && (
                            <BusinessCenterIcon />
                          )}
                          {runs[index].run_type === "FEATURE" && (
                            <AssignmentIcon />
                          )}
                        </Avatar>
                      )}
                      {runs[index].status === "RUNNING" && (
                        <Avatar>
                          <DirectionsRunIcon />
                          {runs[index].run_type === "BUSINESS_SCENARIO" && (
                            <BusinessCenterIcon />
                          )}
                          {runs[index].run_type === "FEATURE" && (
                            <AssignmentIcon />
                          )}
                        </Avatar>
                      )}
                      {runs[index].status === "ERROR" && (
                        <Avatar>
                          <HighlightOffIcon />
                          {runs[index].run_type === "BUSINESS_SCENARIO" && (
                            <BusinessCenterIcon />
                          )}
                          {runs[index].run_type === "FEATURE" && (
                            <AssignmentIcon />
                          )}
                        </Avatar>
                      )}
                    </ListItemAvatar>
                    <ListItemText
                      primary={<Box>{run.details}</Box>}
                      secondary={
                        <DisplayDate
                          dateString={run.run_at}
                          prefixString="Run on "
                        />
                      }
                    />
                  </ListItem>
                ))}
              </List>
              {count && (
                <Box
                  display="flex"
                  flexDirection="row-reverse"
                  bgcolor="background.paper"
                >
                  <TablePagination
                    rowsPerPageOptions={[size]}
                    component="div"
                    count={count}
                    rowsPerPage={size}
                    page={page}
                    onChangePage={onChangePage}
                  />
                </Box>
              )}
            </Box>
          </Box>
        </Grid>
        <Grid item xs={8}>
          <FormGroup row className={classes.autoRefreshPosition}>
            {autoRefresh.status && (
              <CircularProgress color="secondary" size={32} />
            )}
            <Box ml={1}>
              <FormControlLabel
                control={
                  <Switch
                    checked={autoRefresh.status}
                    onChange={handleAutoRefreshChange}
                    name="autoRefresh"
                    color="inherit"
                  />
                }
                label="Auto Refresh"
                className={classes.autoRefreshBg}
              />
            </Box>
          </FormGroup>
          <Box ml={2} mt={2} mr={2}>
            {selectedRun &&
              selectedRun.status !== "RUNNING" &&
              selectedRun.status !== "ERROR" && (
                <iframe
                  src={reportUrl}
                  height="800"
                  width="100%"
                  frameBorder="0"
                  title="Reports"
                />
              )}
            {selectedRun && selectedRun.status === "ERROR" && (
              <Grid
                container
                direction="column"
                justify="center"
                alignItems="center"
              >
                <Box mt={4} ml={2} mr={2}>
                  <img src="/error.svg" alt="Error" width="700" />
                </Box>
                <Box mt={2}>
                  <Typography variant="h6" color="primary">
                    {selectedRun.errorDescription}
                  </Typography>
                </Box>
              </Grid>
            )}
            {selectedRun && selectedRun.status === "RUNNING" && (
              <Grid
                container
                direction="column"
                justify="center"
                alignItems="center"
              >
                <Box mt={4} ml={2} mr={2}>
                  <img src="/loading.svg" alt="Loading" width="700" />
                  <LinearProgress />
                </Box>
                <Box mt={2}>
                  <Typography variant="h6" color="primary">
                    Execution in progress...
                  </Typography>
                </Box>
              </Grid>
            )}
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
};

const mapStateToProps = state => ({
  runs: getRuns(state),
  count: getRunCount(state),
  page: getPage(state),
  size: getSize(state),
  selectedRun: getSelectedRun(state)
});

const mapDispatchToProps = dispatch => ({
  loadRunsFromServer: (page, size) => {
    dispatch(listRunRequest(page, size));
  },
  setSelectedRun: run => {
    dispatch(setSelectedRunRequest(run));
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(RunList);
