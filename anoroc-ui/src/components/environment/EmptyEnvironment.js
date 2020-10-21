import React from "react";
import { Box, Grid, Typography } from "@material-ui/core";
import AddEnvironment from "./AddEnvironment";

const EmptyEnvironment = () => {
  return (
    <Grid container direction="column" justify="center" alignItems="center">
      <Box mt={4} ml={2} mr={2}>
        <img src="/empty_environment.svg" alt="No Records" width="600" />
      </Box>
      <Box mt={2}>
        <Typography variant="h6" color="primary">
          No environments found. Do you want to add the first environment?
        </Typography>
      </Box>
      <Box mt={2}>
        <AddEnvironment />
      </Box>
    </Grid>
  );
};

export default EmptyEnvironment;
