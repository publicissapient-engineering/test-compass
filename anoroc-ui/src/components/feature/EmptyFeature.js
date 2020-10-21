import React from "react";
import { Box, Grid, Typography } from "@material-ui/core";
import AddFeature from "./AddFeature";

const EmptyFeature = () => {
  return (
    <Grid container direction="column" justify="center" alignItems="center">
      <Box mt={4} ml={2} mr={2}>
        <img src="/empty.svg" alt="No Records" width="600" />
      </Box>
      <Box mt={2}>
        <Typography variant="h6" color="primary">
          No features found. Do you want to add the first feature?
        </Typography>
      </Box>
      <Box mt={2}>
        <AddFeature />
      </Box>
    </Grid>
  );
};

export default EmptyFeature;
