import React, { useEffect } from "react";
import Box from "@material-ui/core/Box";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import Button from "@material-ui/core/Button";
import BallotIcon from "@material-ui/icons/Ballot";
import { makeStyles, fade } from "@material-ui/core/styles";
import { connect } from "react-redux";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import { updateFeatureRequest } from "../../thunks/feature/thunks";
import { getSelectedFeature } from "../../selectors/feature/selector";
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
import { listIncludeFeatureRequest, listFeatureRequest } from "../../thunks/feature/thunks";
import { map, filter } from "lodash";
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
  root: {
    flexGrow: 1,
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

const IncludeFeature = ({
  handleSave,
  loadIncludeFeaturesFromServer,
  selectedFeature,
  features = [],
  count = 0,
  page = 0,
  size = 10
}) => {


  const [open, setOpen] = React.useState(false);
  const [search, setSearch] = React.useState("");

  const handleClickOpen = () => {
    setOpen(true);
    loadIncludeFeaturesFromServer(0, size, search, selectedFeature.id);
    setSelected(selectedFeature.include_features);

  };
  
  const handleClose = () => {
    setSearch("");
    setOpen(false);
    setSelected([]);
    clearFeatures();
  };

  const clearFeatures = () => {
    features= []
  }

  const filterByText = e => {
    if(e.target.value.length >=3) {
      setSearch(e.target.value);
      loadIncludeFeaturesFromServer(page, size, e.target.value, selectedFeature.id);
    } else if(e.target.value.length == 0){
      loadIncludeFeaturesFromServer(page, size, "",selectedFeature.id);
    }
  };

  const classes = useStyles();

  const onChangePage = (event, pageSelected) => {
    loadIncludeFeaturesFromServer(pageSelected, size, search, selectedFeature.id);
  };

  const isNotMatchWithSelectedFeature = (feature) => {
      if(feature.id !== selectedFeature.id) {
        return true;
      }else {
        //count = count -1;
        return false;
      }
  } 

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
        Include Features
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
              Include Features
            </Typography>
            <SearchBox searchHandler={filterByText} />
            <Box mr={2}>
              <Button
                onClick={() => {
                   
                  handleSave(
                    selectedFeature.id,
                    selectedFeature.name,
                    selectedFeature.content,
                    selectedFeature.xpath,
                    selectedFeature.featureType,
                    selectedFeature.application.id,
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
            Select the features that are called inside the parent feature, Also Parent feature will not be available in the feature list.
          </DialogContentText>

          <Box>
            <Grid className={classes.root} spacing={2}>
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
  selectedFeature: getSelectedFeature(state),
  features: getIncludeFeatures(state),
  count: getIncludeFeatureCount(state),
  page: getIncludeFeaturePage(state),
  size: getIncludeFeatureSize(state)
});

const mapDispatchToProps = dispatch => ({
  loadIncludeFeaturesFromServer: (page, size, searchByName, parentFeatureId) => {
    dispatch(listIncludeFeatureRequest(page, size, searchByName, parentFeatureId));
  },

  handleSave: (id, name, content, xpath, featureType, applicationId, includeFeatures) => {
    dispatch(
      updateFeatureRequest(id, name, content, xpath, featureType, applicationId, includeFeatures)
    );
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(IncludeFeature);
