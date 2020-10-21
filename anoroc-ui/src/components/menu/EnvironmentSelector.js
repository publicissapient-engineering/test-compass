import React, { useEffect } from "react";
import {
  getAllEnvironments,
  getSelectedEnvironmentId
} from "../../selectors/environment/selector";
import {
  getAllEnvironmentsRequest,
  setSelectedEnvironmentIdRequest
} from "../../thunks/environment/thunks";
import { connect } from "react-redux";

import Box from "@material-ui/core/Box";
import { makeStyles } from "@material-ui/core/styles";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import FormControl from "@material-ui/core/FormControl";
import { map } from "lodash";

const EnvironmentSelector = ({
  loadEnvironmentsFromServer,
  environments,
  updateSelectedEnvironmentId,
  selectedEnvironmentId
}) => {
  useEffect(() => {
    loadEnvironmentsFromServer();
  }, [loadEnvironmentsFromServer]);

  const [environmentId, setEnvironmentIdValue] = React.useState(
    selectedEnvironmentId
  );

  const handleEnvironmentChange = event => {
    setEnvironmentIdValue(event.target.value);
    updateSelectedEnvironmentId(event.target.value);
  };

  const useStyles = makeStyles(theme => ({
    environmentBg: {
      color: "#ffffff"
    },
    environmentPosition: {}
  }));

  const classes = useStyles();

  return (
    <Box ml={2} className={classes.environmentPosition}>
      <FormControlLabel
        label=""
        className={classes.environmentBg}
        control={
          <FormControl>
            <Select
              labelId="source-script-type"
              id="source-script-type"
              value={environmentId}
              displayEmpty
              onChange={handleEnvironmentChange}
              className={classes.environmentBg}
            >
              <MenuItem value="" disabled>
                Select Environment
              </MenuItem>
              {map(environments, (environment, index) => (
                <MenuItem value={environment.id}>{environment.name}</MenuItem>
              ))}
            </Select>
          </FormControl>
        }
      />
    </Box>
  );
};

const mapStateToProps = state => ({
  environments: getAllEnvironments(state),
  selectedEnvironmentId: getSelectedEnvironmentId(state)
});
const mapDispatchToProps = dispatch => ({
  loadEnvironmentsFromServer: () => {
    dispatch(getAllEnvironmentsRequest());
  },
  updateSelectedEnvironmentId: environmentId => {
    dispatch(setSelectedEnvironmentIdRequest(environmentId));
  }
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EnvironmentSelector);
