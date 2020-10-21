import React from "react";
import { Box, Grid, Typography, Fab } from "@material-ui/core";
import ToolTip from "@material-ui/core/Tooltip";
import BugIcon from "@material-ui/icons/BugReport";
import { connect } from "react-redux";
import { startDebugSessionRequest } from "../../thunks/debug/thunks";

const EmptyDebug = ({ startDebugSession }) => {
  return (
    <Grid container direction="column" justify="center" alignItems="center">
      <Box mt={4} ml={2} mr={2}>
        <img src="/debug.svg" alt="No Records" width="600" />
      </Box>
      <Box mt={2}>
        <Typography variant="h6" color="primary">
          No debug session found. Do you want to start a debug session?
        </Typography>
      </Box>
      <Box mt={2}>
        <ToolTip title="Start Debug Session">
          <Fab color="primary" onClick={startDebugSession}>
            <BugIcon />
          </Fab>
        </ToolTip>
      </Box>
    </Grid>
  );
};

const mapStateToProps = state => ({});

const mapDispatchToProps = dispatch => ({
  startDebugSession: () => dispatch(startDebugSessionRequest())
});

export default connect(mapStateToProps, mapDispatchToProps)(EmptyDebug);
