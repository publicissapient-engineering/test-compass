import React, { useEffect } from "react";
import Box from "@material-ui/core/Box";
import { makeStyles } from "@material-ui/core/styles";
import AddIcon from "@material-ui/icons/Add";
import Fab from "@material-ui/core/Fab";
import ToolTip from "@material-ui/core/Tooltip";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import { connect } from "react-redux";
import { addFeatureRequest } from "../../thunks/feature/thunks.js";
import { getAllApplicationsRequest } from "../../thunks/application/thunks.js";
import { getAllApplications } from "../../selectors/application/selector";
import Select from "@material-ui/core/Select";
import InputLabel from "@material-ui/core/InputLabel";
import MenuItem from "@material-ui/core/MenuItem";
import FormControl from "@material-ui/core/FormControl";
import { map } from "lodash";

const useStyles = makeStyles(theme => ({
  button: {
    margin: theme.spacing(2)
  },
  formControl: {
    minWidth: 240
  },
  selectEmpty: {
    marginTop: theme.spacing(2)
  }
}));

const AddFeature = ({
  onAddFeatureClick,
  applications,
  loadAllApplicationsFromServer
}) => {
  const [open, setOpen] = React.useState(false);
  const [name, setNameValue] = React.useState("");
  const [scriptType, setScriptTypeValue] = React.useState("");
  const [applicationId, setApplicationIdValue] = React.useState("");

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSelectChange = event => {
    setScriptTypeValue(event.target.value);
  };

  const handleApplicationChange = event => {
    setApplicationIdValue(event.target.value);
  };

  useEffect(() => {
    loadAllApplicationsFromServer();
  }, [loadAllApplicationsFromServer]);

  const classes = useStyles();

  return (
    <Box>
      <ToolTip title="Add Feature">
        <Fab
          color="primary"
          aria-label="add"
          className={classes.button}
          onClick={handleClickOpen}
        >
          <AddIcon />
        </Fab>
      </ToolTip>
      <Dialog
        fullWidth
        open={open}
        onClose={handleClose}
        aria-labelledby="form-dialog-title"
      >
        <DialogTitle id="form-dialog-title">Add Feature</DialogTitle>
        <DialogContent>
          <DialogContentText></DialogContentText>
          <Box mb={2}>
            <TextField
              autoFocus
              margin="dense"
              id="name"
              label="Feature Details"
              value={name}
              onChange={e => setNameValue(e.target.value)}
              fullWidth
            />
          </Box>
          <Box mb={2}>
            <FormControl className={classes.formControl}>
              <InputLabel id="script-select-label">
                Source Script Type
              </InputLabel>
              <Select
                labelId="source-script-type"
                id="source-script-type"
                value={scriptType}
                onChange={handleSelectChange}
              >
                <MenuItem value={"KARATE"}>Karate</MenuItem>
                <MenuItem value={"ANOROC"}>CATS</MenuItem>
              </Select>
            </FormControl>
          </Box>
          <Box mb={2}>
            <FormControl className={classes.formControl}>
              <InputLabel id="script-select-label">Application</InputLabel>
              <Select
                labelId="source-script-type"
                id="source-script-type"
                value={applicationId}
                onChange={handleApplicationChange}
              >
                {map(applications, (application, index) => (
                  <MenuItem value={application.id}>{application.name}</MenuItem>
                ))}
              </Select>
            </FormControl>
          </Box>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={() => {
              onAddFeatureClick(name, scriptType, applicationId);
              setNameValue("");
              handleClose();
            }}
            color="primary"
            variant="contained"
          >
            Add Feature
          </Button>
          <Button onClick={handleClose} color="secondary" variant="contained">
            Cancel
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

const mapStateToProps = state => ({
  applications: getAllApplications(state)
});

const mapDispatchToProps = dispatch => ({
  onAddFeatureClick: (name, scriptType, applicationId) => {
    dispatch(addFeatureRequest(name, scriptType, applicationId));
  },
  loadAllApplicationsFromServer: () => {
    dispatch(getAllApplicationsRequest());
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(AddFeature);
