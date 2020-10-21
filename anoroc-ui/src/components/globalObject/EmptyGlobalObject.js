import React from "react";
import { Box, Grid, Typography } from "@material-ui/core";
import AddGlobalObject from "./AddGlobalObject";

const EmptyGlobalObject = () => {
  return (
    <Grid container direction="column" justify="center" alignItems="center">
      <Box mt={4} ml={2} mr={2}>
        <img src="/empty_global_object.svg" alt="No Records" width="600" />
      </Box>
      <Box mt={2}>
        <Typography variant="h6" color="primary">
          No Global Objects found. Do you want to add the first Global Object?
        </Typography>
      </Box>
      <Box mt={2}>
        <AddGlobalObject />
      </Box>
    </Grid>
  );
};

export default EmptyGlobalObject;
