import React, { useEffect } from "react";
import Box from "@material-ui/core/Box";
import { makeStyles } from "@material-ui/core/styles";
import EditSharpIcon from '@material-ui/icons/EditSharp';
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
import { getSelectedApplication } from "../../selectors/application/selector.js";


const useStyles = makeStyles(theme => ({
  button: {
    margin: theme.spacing(0)
  },
  formControl: {
    minWidth: 240
  },
  selectEmpty: {
    marginTop: theme.spacing(2)
  }
}));

const EditApplication = ({ 
  selectedApplication, 
  onUpdateClick 
}) => {
  useEffect(() => {
    if (selectedApplication) {
      setNameValue(selectedApplication.name);
    }
  }, [selectedApplication]);

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
      <ToolTip title="Edit Application">
        <Fab 
            size="small" 
            color="primary"
            aria-label="edit"
            className={classes.button}
            onClick={handleClickOpen}
           >
          <EditSharpIcon/>
        </Fab>
      </ToolTip>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="form-dialog-title"
      >
        <DialogTitle id="form-dialog-title">Edit Application</DialogTitle>
        <DialogContent>
          <DialogContentText>
            To edit application name, enter the new application name and click on
            Update.
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
              onUpdateClick(selectedApplication.id, name);
              setNameValue("");
              handleClose();
            }}
            color="primary"
            variant="contained"
          >
            Update
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
  selectedApplication: getSelectedApplication(state)
});

const mapDispatchToProps = dispatch => ({
  onUpdateClick: (id, name ) => dispatch(addOrEditApplicationRequest(name,id))
});

export default connect(mapStateToProps, mapDispatchToProps)(EditApplication);
