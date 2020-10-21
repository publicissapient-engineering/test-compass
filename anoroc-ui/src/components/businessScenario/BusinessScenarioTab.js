import React, { useEffect } from "react";
import { makeStyles } from "@material-ui/core/styles";
import { connect } from "react-redux";
import { updateBusinessScenarioRequest } from "../../thunks/businessScenario/thunks";
import { runBusinessScenarioRequest } from "../../thunks/run/thunks";
import { getSelectedBusinessScenario } from "../../selectors/businessScenario/selector";
import { getSelectedEnvironmentId } from "../../selectors/environment/selector";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";
import Button from "@material-ui/core/Button";
import ButtonGroup from "@material-ui/core/ButtonGroup";
import SaveIcon from "@material-ui/icons/Save";
import DirectionsRunIcon from "@material-ui/icons/DirectionsRun";
import Typography from "@material-ui/core/Typography";
import AddFeature from "./AddFeature";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import { map } from "lodash";
import AcUnitIcon from "@material-ui/icons/AcUnit";
import SportsKabaddiIcon from "@material-ui/icons/SportsKabaddi";
import Avatar from "@material-ui/core/Avatar";
import Chip from "@material-ui/core/Chip";
import { deepPurple, grey } from "@material-ui/core/colors";

const useStyles = makeStyles(theme => ({
  root: {
    display: "flex",
    flexWrap: "wrap"
  },
  textField: {
    marginLeft: theme.spacing(2),
    marginRight: theme.spacing(2),
    marginBottom: theme.spacing(2),
    marginTop: theme.spacing(2)
  },
  title: {
    marginBottom: theme.spacing(2)
  },
  buttonGroupPosition: {
    position: "absolute",
    top: theme.spacing(10),
    right: theme.spacing(2)
  },
  button: {
    margin: theme.spacing(2)
  },
  purple: {
    color: theme.palette.getContrastText(deepPurple[500]),
    backgroundColor: deepPurple[500],
    width: theme.spacing(4),
    height: theme.spacing(4)
  },
  brown: {
    color: theme.palette.getContrastText(grey[900]),
    backgroundColor: grey[900],
    width: theme.spacing(4),
    height: theme.spacing(4)
  }
}));

const BusinessScenariosTab = ({
  updateBusinessScenario,
  selectedBusinessScenario,
  runBusinessScenario,
  selectedEnvironmentId
}) => {
  const [name, setName] = React.useState("");
  const [description, setDescription] = React.useState("");

  useEffect(() => {
    if (selectedBusinessScenario) {
      setName(selectedBusinessScenario.name);
      setDescription(selectedBusinessScenario.description);
    }
  }, [selectedBusinessScenario]);

  const classes = useStyles();

  return (
    <Box>
      <Box className={classes.root}>
        <TextField
          id="businessScenariosName"
          label="Name"
          style={{ margin: 12 }}
          fullWidth
          value={name}
          onChange={e => setName(e.target.value)}
          margin="normal"
          InputLabelProps={{
            shrink: true
          }}
        />
        <TextField
          id="businessScenariosDescription"
          multiline
          rows={4}
          variant="outlined"
          label="Description"
          style={{ margin: 12 }}
          fullWidth
          value={description}
          onChange={e => setDescription(e.target.value)}
          margin="normal"
          InputLabelProps={{
            shrink: true
          }}
        />
      </Box>
      <ButtonGroup color="primary" className={classes.buttonGroupPosition}>
        <AddFeature />
        <Button
          color="primary"
          startIcon={<SaveIcon />}
          variant="contained"
          className={classes.button}
          onClick={() => {
            updateBusinessScenario(selectedBusinessScenario, name, description);
          }}
        >
          Update
        </Button>
        <Button
          color="primary"
          startIcon={<DirectionsRunIcon />}
          variant="contained"
          className={classes.button}
          onClick={() =>
            runBusinessScenario(selectedBusinessScenario, selectedEnvironmentId)
          }
        >
          Run
        </Button>
      </ButtonGroup>
      {selectedBusinessScenario &&
        selectedBusinessScenario.features.length === 0 && (
          <Grid
            container
            direction="column"
            justify="center"
            alignItems="center"
          >
            <Box mt={8} ml={2} mr={2}>
              <img
                src="/empty-features-in-bs.svg"
                alt="No Records"
                width="600"
              />
            </Box>
            <Box>
              <Typography variant="h6" color="primary">
                No features associated to this business scenario.
              </Typography>
            </Box>
          </Grid>
        )}

      <Box>
        <Grid>
          <Box mt={2} mr={2} ml={2}>
            <Typography color="primary">
              {selectedBusinessScenario &&
                selectedBusinessScenario.features.length}{" "}
              Associated Features
            </Typography>
            <Box mt={2} mb={2}>
              <TableContainer component={Paper}>
                <Table className={classes.table} aria-label="simple table">
                  <TableHead>
                    <TableRow>
                      <TableCell>Order</TableCell>
                      <TableCell>Type</TableCell>
                      <TableCell>Name</TableCell>
                      <TableCell>Application</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {selectedBusinessScenario &&
                      map(
                        selectedBusinessScenario.features,
                        (feature, index) => {
                          return (
                            <TableRow key={feature.id} hover>
                              <TableCell>{index + 1}</TableCell>
                              <TableCell>
                                {feature.featureType === "ANOROC" && (
                                  <Avatar className={classes.purple}>
                                    <AcUnitIcon />
                                  </Avatar>
                                )}
                                {feature.featureType === "KARATE" && (
                                  <Avatar className={classes.brown}>
                                    <SportsKabaddiIcon />
                                  </Avatar>
                                )}
                              </TableCell>
                              <TableCell component="th" scope="row">
                                {feature.name}
                              </TableCell>
                              <TableCell>
                                <Chip label={feature.application.name} />
                              </TableCell>
                            </TableRow>
                          );
                        }
                      )}
                  </TableBody>
                </Table>
              </TableContainer>
            </Box>
          </Box>
        </Grid>
      </Box>
    </Box>
  );
};

const mapStateToProps = state => ({
  selectedBusinessScenario: getSelectedBusinessScenario(state),
  selectedEnvironmentId: getSelectedEnvironmentId(state)
});

const mapDispatchToProps = dispatch => ({
  updateBusinessScenario: (businessScenarios, name, description) => {
    let featureIds = businessScenarios.features.map(feature => feature.id);
    dispatch(
      updateBusinessScenarioRequest(
        businessScenarios.id,
        name,
        description,
        featureIds
      )
    );
  },
  runBusinessScenario: (businessScenarios, selectedEnvironmentId) => {
    dispatch(
      runBusinessScenarioRequest(businessScenarios.id, selectedEnvironmentId)
    );
  }
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BusinessScenariosTab);
