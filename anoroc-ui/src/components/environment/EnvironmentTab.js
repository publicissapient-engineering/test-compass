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
import { connect } from "react-redux";
import { updateEnvironmentRequest } from "../../thunks/environment/thunks";
import { getSelectedEnvironment } from "../../selectors/environment/selector";
import AceEditor from "react-ace";
import "ace-builds/src-noconflict/mode-json";
import "ace-builds/src-noconflict/mode-gherkin";
import "ace-builds/src-noconflict/theme-monokai";
import "ace-builds/src-noconflict/ext-language_tools";
import "ace-builds/src-noconflict/snippets/gherkin";
import TextField from "@material-ui/core/TextField";

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
  }
}));

const TabPanel = props => {
  const { children, value, index } = props;

  return (
    <Typography hidden={value !== index} id={`environment-${index}`}>
      {value === index && <Box>{children}</Box>}
    </Typography>
  );
};

const currentTab = index => {
  return {
    id: `environment-tab-${index}`
  };
};

const EnvironmentTab = ({ updateEnvironment, selectedEnvironment }) => {
  const [value, setValue] = React.useState(0);
  const [liveAnorocContent, setAnorocContent] = React.useState("");
  const [liveKarateContent, setKarateContent] = React.useState("");
  const [environmentName, setEnvironmentName] = React.useState("");
  const [liveSettingsContent, setSettingsContent] = React.useState("");

  useEffect(() => {
    if (selectedEnvironment) {
      if (selectedEnvironment.anoroc_content !== null) {
        setAnorocContent(selectedEnvironment.anoroc_content);
      } else {
        setAnorocContent("");
      }
      if (selectedEnvironment.karate_content !== null) {
        setKarateContent(selectedEnvironment.karate_content);
      } else {
        setKarateContent("");
      }
      if (selectedEnvironment.settings_content !== null) {
        setSettingsContent(selectedEnvironment.settings_content);
      } else {
        setSettingsContent("");
      }
      setEnvironmentName(selectedEnvironment.name);
    }
  }, [selectedEnvironment]);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const classes = useStyles();

  return (
    <Box>
      <Box className={classes.root}>
        <TextField
          id="environmentName"
          label="Environment Name"
          style={{ margin: 12 }}
          fullWidth
          value={environmentName}
          onChange={e => setEnvironmentName(e.target.value)}
          margin="normal"
          InputLabelProps={{
            shrink: true
          }}
        />
      </Box>
      <AppBar position="static" color="default">
        <Tabs value={value} onChange={handleChange}>
          <Tab label="CATS" {...currentTab(0)} />
          <Tab label="Karate" {...currentTab(1)} />
          <Tab label="Settings" {...currentTab(1)} />
        </Tabs>
      </AppBar>
      <TabPanel value={value} index={0}>
        <Box>
          <AceEditor
            mode="json"
            theme="monokai"
            fontSize={16}
            value={liveAnorocContent}
            onChange={newContent => {
              setAnorocContent(newContent);
            }}
            name="anarocContentEditor"
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
            value={liveKarateContent}
            onChange={newContent => {
              setKarateContent(newContent);
            }}
            name="karateContentEditor"
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
        <Box>
          <AceEditor
            mode="json"
            theme="monokai"
            fontSize={16}
            value={liveSettingsContent}
            onChange={newContent => {
              setSettingsContent(newContent);
            }}
            name="settingsContentEditor"
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
      <ButtonGroup color="primary" className={classes.buttonGroupPosition}>
        <Button
          color="primary"
          startIcon={<SaveIcon />}
          variant="contained"
          className={classes.button}
          onClick={() => {
            updateEnvironment(
              selectedEnvironment,
              environmentName,
              liveAnorocContent,
              liveKarateContent,
              liveSettingsContent
            );
          }}
        >
          Update
        </Button>
      </ButtonGroup>
    </Box>
  );
};

const mapStateToProps = state => ({
  selectedEnvironment: getSelectedEnvironment(state)
});

const mapDispatchToProps = dispatch => ({
  updateEnvironment: (
    environment,
    environmentName,
    anarocContent,
    karateContent,
    settingsContent
  ) => {
    dispatch(
      updateEnvironmentRequest(
        environment.id,
        environmentName,
        anarocContent,
        karateContent,
        settingsContent
      )
    );
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(EnvironmentTab);
