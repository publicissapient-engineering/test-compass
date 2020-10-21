import React from "react";
import { Box, Grid, Typography, Fab } from "@material-ui/core";
import ToolTip from "@material-ui/core/Tooltip";
import DirectionsRunIcon from "@material-ui/icons/DirectionsRun";

const EmptyRun = () => {
  return (
    <Grid container direction="column" justify="center" alignItems="center">
      <Box mt={4} ml={2} mr={2}>
        <img src="/runs.svg" alt="No Records" width="700" />
      </Box>
      <Box mt={2}>
        <Typography variant="h6" color="primary">
          No runs yet. Do you want to start your first run?
        </Typography>
      </Box>
      <Box mt={2}>
        <ToolTip title="Run Feature">
          <Fab color="primary" href="/">
            <DirectionsRunIcon />
          </Fab>
        </ToolTip>
      </Box>
    </Grid>
  );
};

export default EmptyRun;
