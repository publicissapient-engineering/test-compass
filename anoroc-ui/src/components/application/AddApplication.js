import React from "react";
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
import { addOrEditApplicationRequest } from "../../thunks/application/thunks.js";

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

const AddApplication = ({ onAddApplicationClick }) => {
  const [open, setOpen] = React.useState(false);
  const [name, setNameValue] = React.useState("");

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const classes = useStyles();
  return (
    <Box>
      <ToolTip title="Add Application">
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
        open={open}
        onClose={handleClose}
        aria-labelledby="form-dialog-title"
      >
        <DialogTitle id="form-dialog-title">Add Application</DialogTitle>
        <DialogContent>
          <DialogContentText>
            To add a new application, enter the application name and click on
            Add Application.
          </DialogContentText>
          <Box mb={2}>
            <TextField
              autoFocus
              margin="dense"
              id="name"
              label="Application Name"
              value={name}
              onChange={e => setNameValue(e.target.value)}
              fullWidth
            />
          </Box>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={() => {
              onAddApplicationClick(name);
              setNameValue("");
              handleClose();
            }}
            color="primary"
            variant="contained"
          >
            Add Application
          </Button>
          <Button onClick={handleClose} color="secondary" variant="contained">
            Cancel
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

const mapStateToProps = state => ({});

const mapDispatchToProps = dispatch => ({
  onAddApplicationClick: name => dispatch(addOrEditApplicationRequest(name))
});

export default connect(mapStateToProps, mapDispatchToProps)(AddApplication);
