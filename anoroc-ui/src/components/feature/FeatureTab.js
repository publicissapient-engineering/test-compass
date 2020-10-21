import React, { useEffect } from "react";
import AppBar from "@material-ui/core/AppBar";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import { makeStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import ButtonGroup from "@material-ui/core/ButtonGroup";
import SaveIcon from "@material-ui/icons/Save";
import DirectionsRunIcon from "@material-ui/icons/DirectionsRun";
import { connect } from "react-redux";
import { updateFeatureRequest,associatedFeaturesRequest } from "../../thunks/feature/thunks";
import { runFeatureRequest } from "../../thunks/run/thunks";
import { getSelectedFeature, getAssociatedFeatures } from "../../selectors/feature/selector";
import { getSelectedEnvironmentId } from "../../selectors/environment/selector";
import AceEditor from "react-ace";
import "ace-builds/src-noconflict/mode-json";
import "ace-builds/src-noconflict/mode-gherkin";
import "ace-builds/src-noconflict/theme-monokai";
import "ace-builds/src-noconflict/ext-language_tools";
import "ace-builds/src-noconflict/snippets/gherkin";
import TextField from "@material-ui/core/TextField";
import IncludeFeature from "./IncludeFeature"
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Grid from "@material-ui/core/Grid";
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
    backgroundColor: deepPurple[500]
  },
  brown: {
    color: theme.palette.getContrastText(grey[900]),
    backgroundColor: grey[900]
  }
}));

const TabPanel = props => {
  const { children, value, index } = props;

  return (
    <Typography hidden={value !== index} id={`feature-${index}`}>
      {value === index && <Box>{children}</Box>}
    </Typography>
  );
};

const currentTab = index => {
  return {
    id: `feature-tab-${index}`
  };
};

const FeatureTab = ({
  updateFeature,
  selectedFeature,
  runFeature,
  selectedEnvironmentId,
  associatedFeatures = [],
  getAssociatedFeatures
}) => {
  const [value, setValue] = React.useState(0);
  const [liveCode, setLiveCode] = React.useState("");
  const [liveXpath, setLiveXpath] = React.useState("");
  const [featureName, setFeatureName] = React.useState("");
  
  useEffect(() => {
    if (selectedFeature) {
      setLiveCode(selectedFeature.content);
      setLiveXpath(selectedFeature.xpath);
      setFeatureName(selectedFeature.name);
      getAssociatedFeatures(selectedFeature);
    }
  }, [selectedFeature]);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const classes = useStyles();

  return (
    <Box>
      <Box className={classes.root}>
        <TextField
          id="featureName"
          label="Feature Name"
          style={{ margin: 12 }}
          fullWidth
          value={featureName}
          onChange={e => setFeatureName(e.target.value)}
          margin="normal"
          InputLabelProps={{
            shrink: true
          }}
        />
      </Box>
      <AppBar position="static" color="default">
        <Tabs value={value} onChange={handleChange}>
          <Tab label="Feature" {...currentTab(0)} />
          <Tab label="XPath" {...currentTab(1)} />
          <Tab label="Associated Features" {...currentTab(2)} />
        </Tabs>
      </AppBar>
      <TabPanel value={value} index={0}>
        <Box>
          <AceEditor
            mode="gherkin"
            theme="monokai"
            value={liveCode}
            fontSize={16}
            onChange={newCode => {
              setLiveCode(newCode);
            }}
            name="codeEditor"
            width="100%"
            showPrintMargin={true}
            showGutter={true}
            highlightActiveLine={true}
            editorProps={{ $blockScrolling: true }}
            setOptions={{
              enableBasicAutocompletion: true,
              enableLiveAutocompletion: true,
              enableSnippets: true,
              showLineNumbers: true,
              tabSize: 2
            }}
          />
        </Box>
      </TabPanel>
      <TabPanel value={value} index={1}>
        <Box>
          <AceEditor
            mode="json"
            theme="monokai"
            fontSize={16}
            value={liveXpath}
            onChange={newXpath => {
              setLiveXpath(newXpath);
            }}
            name="xpathEditor"
            width="100%"
            showPrintMargin={true}
            showGutter={true}
            highlightActiveLine={true}
            editorProps={{ $blockScrolling: true }}
            setOptions={{
              enableBasicAutocompletion: true,
              enableLiveAutocompletion: true,
              enableSnippets: true,
              showLineNumbers: true,
              tabSize: 2
            }}
          />
        </Box>
      </TabPanel>
      <TabPanel value={value} index={2}>
        {associatedFeatures.length === 0 && (
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
                No Features associated.
              </Typography>
            </Box>
          </Grid>
        )}
        <Box>
          <Grid>
          {associatedFeatures.length !== 0 && (
              <Box>
                <TableContainer component={Paper}>
                  <Table className={classes.table} aria-label="simple table">
                    <TableHead>
                      <TableRow>
                        <TableCell>Type</TableCell>
                        <TableCell>Name</TableCell>
                        <TableCell>Application</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {selectedFeature &&
                        map(
                          associatedFeatures,
                          (feature, index) => {
                            return (
                              <TableRow key={feature.id} hover>
                            
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
          )}
          </Grid>
        </Box>
      </TabPanel>
      <ButtonGroup color="primary" className={classes.buttonGroupPosition}>
        <IncludeFeature/>
        <Button
          color="primary"
          startIcon={<SaveIcon />}
          variant="contained"
          className={classes.button}
          onClick={() => {
            updateFeature(selectedFeature, liveCode, liveXpath, featureName);

          }}
        >
          Update
        </Button>
        <Button
          color="primary"
          startIcon={<DirectionsRunIcon />}
          variant="contained"
          className={classes.button}
          onClick={() => runFeature(selectedFeature, selectedEnvironmentId)}
        >
          Run
        </Button>
      </ButtonGroup>
    </Box>
  );
};

const mapStateToProps = state => ({
  selectedFeature: getSelectedFeature(state),
  selectedEnvironmentId: getSelectedEnvironmentId(state),
  associatedFeatures: getAssociatedFeatures(state)
});

const mapDispatchToProps = dispatch => ({
  updateFeature: (feature, content, xpath, featureName) => {
    dispatch(
      updateFeatureRequest(
        feature.id,
        featureName,
        content,
        xpath,
        feature.featureType,
        feature.application.id,
        feature.include_features
      )
    );
  },
  runFeature: (feature, selectedEnvironmentId) => {
    dispatch(runFeatureRequest(feature.id, selectedEnvironmentId));
  },
  getAssociatedFeatures: (feature) => {
    dispatch(associatedFeaturesRequest(feature.id));
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(FeatureTab);
