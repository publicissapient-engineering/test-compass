import React from "react";
import { Grid, Box, Typography } from "@material-ui/core";
import AddApplication from "./AddApplication";

const EmptyApplication = () => {
  return (
    <Grid container direction="column" justify="center" alignItems="center">
      <Box mt={12} ml={2} mr={2}>
        <img src="/empty_apps.svg" alt="No Records" width="800" />
      </Box>
      <Box mt={2}>
        <Typography variant="h6" color="primary">
          No applications found. Do you want to add the first application?
        </Typography>
      </Box>
      <Box mt={2}>
        <AddApplication />
      </Box>
    </Grid>
  );
};

export default EmptyApplication;
