import React from "react";
import { Box, Grid, Typography } from "@material-ui/core";
import AddBusinessScenario from "./AddBusinessScenario";

const EmptyBusinessScenario = () => {
  return (
    <Grid container direction="column" justify="center" alignItems="center">
      <Box mt={8} ml={2} mr={2}>
        <img src="/empty-business-scenarios.svg" alt="No Records" width="600" />
      </Box>
      <Box mt={2}>
        <Typography variant="h6" color="primary">
          No business scenarios found. Do you want to add the first business
          scenarios?
        </Typography>
      </Box>
      <Box mt={2}>
        <AddBusinessScenario />
      </Box>
    </Grid>
  );
};

export default EmptyBusinessScenario;
