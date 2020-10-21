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
import { updateGlobalObjectRequest } from "../../thunks/globalObject/thunks";
import { getSelectedGlobalObject } from "../../selectors/globalObject/selector";
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
    <Typography hidden={value !== index} id={`globalObject-${index}`}>
      {value === index && <Box>{children}</Box>}
    </Typography>
  );
};

const currentTab = index => {
  return {
    id: `globalObject-tab-${index}`
  };
};

const GlobalObjectTab = ({ updateGlobalObject, selectedGlobalObject }) => {
  const [value, setValue] = React.useState(0);
  const [content, setContent] = React.useState("");
  const [globalObjectName, setGlobalObjectName] = React.useState("");

  useEffect(() => {
    if (selectedGlobalObject) {
      if (selectedGlobalObject.content !== null) {
        setContent(selectedGlobalObject.content);
      } else {
        setContent("");
      }
      setGlobalObjectName(selectedGlobalObject.name);
    }
  }, [selectedGlobalObject]);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const classes = useStyles();

  return (
    <Box>
      <Box className={classes.root}>
        <TextField
          id="globalObjectName"
          label="Global Object Name"
          style={{ margin: 12 }}
          fullWidth
          value={globalObjectName}
          onChange={e => setGlobalObjectName(e.target.value)}
          margin="normal"
          InputLabelProps={{
            shrink: true
          }}
        />
      </Box>
      <AppBar position="static" color="default">
        <Tabs value={value} onChange={handleChange}>
          <Tab label="Content" {...currentTab(0)} />
        </Tabs>
      </AppBar>
      <TabPanel value={value} index={0}>
        <Box>
          <AceEditor
            mode="json"
            theme="monokai"
            fontSize={16}
            value={content}
            onChange={newContent => {
              setContent(newContent);
            }}
            name="contentEditor"
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
            updateGlobalObject(
              selectedGlobalObject,
              globalObjectName,
              content              
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
  selectedGlobalObject: getSelectedGlobalObject(state)
});

const mapDispatchToProps = dispatch => ({
  updateGlobalObject: (
    globalObject,
    globalObjectName,
    content
  ) => {
    dispatch(
      updateGlobalObjectRequest(
        globalObject.id,
        globalObjectName,
        content,
        globalObject.application.id
      )
    );
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(GlobalObjectTab);
