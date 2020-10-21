import React, { useEffect } from "react";
import Box from "@material-ui/core/Box";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import Button from "@material-ui/core/Button";
import BallotIcon from "@material-ui/icons/Ballot";
import ToolTip from "@material-ui/core/Tooltip";
import { makeStyles, fade } from "@material-ui/core/styles";
import { connect } from "react-redux";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import { updateBusinessScenarioRequest } from "../../thunks/businessScenario/thunks";
import { getSelectedBusinessScenario } from "../../selectors/businessScenario/selector";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import Grid from "@material-ui/core/Grid";
import {
  getIncludeFeatures,
  getIncludeFeatureCount,
  getIncludeFeaturePage,
  getIncludeFeatureSize
} from "../../selectors/feature/selector";
import { listIncludeFeatureRequest } from "../../thunks/feature/thunks";
import { map } from "lodash";
import DisplayDate from "../common/DisplayDate";
import TablePagination from "@material-ui/core/TablePagination";
import { deepPurple, grey } from "@material-ui/core/colors";
import AcUnitIcon from "@material-ui/icons/AcUnit";
import SportsKabaddiIcon from "@material-ui/icons/SportsKabaddi";
import Avatar from "@material-ui/core/Avatar";
import Chip from "@material-ui/core/Chip";
import Checkbox from "@material-ui/core/Checkbox";
import SearchBox from '../common/Search'

const useStyles = makeStyles(theme => ({
  button: {
    margin: theme.spacing(2)
  },
  menuButton: {
    marginRight: theme.spacing(2)
  },
  title: {
    flexGrow: 1
  },
  table: {
    minWidth: 300
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
  },
  search: {
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: fade(theme.palette.common.white, 0.15),
    '&:hover': {
      backgroundColor: fade(theme.palette.common.white, 0.25),
    },
    marginRight: theme.spacing(2),
    marginLeft: 0,
    width: '100%',
    [theme.breakpoints.up('sm')]: {
      marginLeft: theme.spacing(3),
      width: 'auto',
    },
  },
  searchIcon: {
    padding: theme.spacing(0, 2),
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  inputRoot: {
    color: 'inherit',
  },
  inputInput: {
    padding: theme.spacing(1, 1, 1, 0),
    // vertical padding + font size from searchIcon
    paddingLeft: `calc(1em + ${theme.spacing(4)}px)`,
    transition: theme.transitions.create('width'),
    width: '100%',
    [theme.breakpoints.up('md')]: {
      width: '20ch',
    },
  }
}));

const AddFeature = ({
  handleSave,
  selectedBusinessScenario,
  loadFeatureFromServer,
  features = [],
  count = 0,
  page = 0,
  size = 10
}) => {
  const [open, setOpen] = React.useState(false);
  const [search, setSearch] = React.useState("");

  const handleClickOpen = () => {
    setOpen(true);
    loadFeatureFromServer(0, size);
    setSelected(selectedBusinessScenario.features.map(feature => feature.id));
  };

  const handleClose = () => {
    setOpen(false);
    setSelected([]);
    setSearch("");
    clearFeatures();
  };

  const filterByText = e => {
    setSearch(e.target.value);
    if(e.target.value.length >=3) {
      loadFeatureFromServer(page, size, e.target.value);
    }else if(e.target.value.length == 0){
      loadFeatureFromServer(page, size, "");
    }
  };

  const clearFeatures = () => {
    features= []
  }

  const classes = useStyles();

  const onChangePage = (event, pageSelected) => {
    loadFeatureFromServer(pageSelected, size, search);
  };

  const [checked, setChecked] = React.useState(true);

  const handleChange = event => {
    setChecked(event.target.checked);
  };

  const [selected, setSelected] = React.useState([]);
  const [selectedFeatures, setSelectedFeatures] = React.useState([]);

  const handleClick = (event, name) => {
    const selectedIndex = selected.indexOf(name);
    let newSelected = [];

    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, name);
    } else if (selectedIndex === 0) {
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) {
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelected = newSelected.concat(
        selected.slice(0, selectedIndex),
        selected.slice(selectedIndex + 1)
      );
    }

    setSelected(newSelected);
    
  };

  const isSelected = name => selected.indexOf(name) !== -1;
  return (
    <Box>
      <Button
        color="primary"
        startIcon={<BallotIcon />}
        variant="contained"
        className={classes.button}
        onClick={handleClickOpen}
      >
        Manage Features
      </Button>
      <Dialog
        maxWidth
        open={open}
        onClose={handleClose}
        aria-labelledby="form-dialog-title"
      >
        <AppBar position="static" color="primary">
          <Toolbar>
            <Typography variant="h6" className={classes.title}>
              Manage Features
            </Typography>
            <SearchBox searchHandler={filterByText}/>
            <Box mr={2}>
              <Button
                onClick={() => {
                  handleSave(
                    selectedBusinessScenario.id,
                    selectedBusinessScenario.name,
                    selectedBusinessScenario.description,
                    selected
                  );
                  handleClose();
                }}
                color="light"
                variant="contained"
              >
                Save
              </Button>
            </Box>
            <Button color="secondary" variant="contained" onClick={handleClose}>
              Close
            </Button>
          </Toolbar>
        </AppBar>
        <DialogContent>
          <DialogContentText>
            Select the features that are a part of this Business Scenario.
            Features in the business scenario is executed in the selected order.
          </DialogContentText>

          <Box>
            <Grid>
              <Box mt={2} mr={2} ml={2}>
                <Typography color="primary">Available Features</Typography>
                <Box mt={2} mb={2}>
                  <TableContainer component={Paper}>
                    <Table className={classes.table} aria-label="simple table">
                      <TableHead>
                        <TableRow>
                          <TableCell></TableCell>
                          <TableCell>Type</TableCell>
                          <TableCell>Name</TableCell>
                          <TableCell>Application</TableCell>
                        </TableRow>
                      </TableHead>
                      <TableBody>
                        {map(features, (feature, index) => {
                          const isItemSelected = isSelected(feature.id);
                          const labelId = `table-checkbox-${feature.id}`;
                          if(search !== "" && feature.name.toLowerCase().indexOf(search.toLowerCase()) === -1) {
                            return null;
                          }
                          return (
                            <TableRow
                              key={feature.id}
                              selected={isItemSelected}
                              onClick={event => handleClick(event, feature.id)}
                            >
                              <TableCell>
                                <Checkbox
                                  checked={isItemSelected}
                                  inputProps={{ "aria-labelledby": feature.id }}
                                />
                              </TableCell>
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
                        })}
                      </TableBody>
                    </Table>
                  </TableContainer>
                  <TablePagination
                    rowsPerPageOptions={[size]}
                    component="div"
                    count={count}
                    rowsPerPage={size}
                    page={page}
                    onChangePage={onChangePage}
                  />
                </Box>
              </Box>
            </Grid>
          </Box>
        </DialogContent>
      </Dialog>
    </Box>
  );
};

const mapStateToProps = state => ({
  selectedBusinessScenario: getSelectedBusinessScenario(state),
  features: getIncludeFeatures(state),
  count: getIncludeFeatureCount(state),
  page: getIncludeFeaturePage(state),
  size: getIncludeFeatureSize(state)
});

const mapDispatchToProps = dispatch => ({
  loadFeatureFromServer: (page, size, searchByName) => {
    dispatch(listIncludeFeatureRequest(page, size, searchByName));
  },
  handleSave: (id, name, description, selectedFeatureIds) => {

    dispatch(
      updateBusinessScenarioRequest(id, name, description, selectedFeatureIds)
    );
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(AddFeature);
