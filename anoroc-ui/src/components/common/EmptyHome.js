import React from "react";
import { Box, Grid, Typography, Button } from "@material-ui/core";
import { useKeycloak } from "@react-keycloak/web";

const EmptyHome = () => {
  const { keycloak } = useKeycloak();
  return (
    <Grid container direction="column" justify="center" alignItems="center">
      <Box mt={4} ml={2} mr={2}>
        <img src="/welcome.svg" alt="Welcome" width="600" />
      </Box>
      <Box mt={2}>
        <Typography variant="h6" color="primary">
          Welcome to CATS Lite
        </Typography>
      </Box>
      <Box mt={2}>
        {keycloak && !keycloak.authenticated && (
          <Button
            color="primary"
            variant="contained"
            onClick={() => keycloak.login()}
          >
            Login
          </Button>
        )}
      </Box>
    </Grid>
  );
};

export default EmptyHome;
