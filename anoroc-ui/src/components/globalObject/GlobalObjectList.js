import React, { useEffect } from "react";
import List from "@material-ui/core/List";
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import GlobalObjectTab from "./GlobalObjectTab";
import TablePagination from "@material-ui/core/TablePagination";
import { connect } from "react-redux";
import {
  listGlobalObjectRequest,
  setSelectedGlobalObjectRequest
} from "../../thunks/globalObject/thunks";
import { map } from "lodash";
import {
  getGlobalObjects,
  getGlobalObjectCount,
  getPage,
  getSize
} from "../../selectors/globalObject/selector";
import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import ListItemText from "@material-ui/core/ListItemText";
import DisplayDate from "../common/DisplayDate";
import { deepPurple, grey } from "@material-ui/core/colors";
import ImportantDevicesIcon from "@material-ui/icons/ImportantDevices";
import AddGlobalObject from "./AddGlobalObject";
import Chip from "@material-ui/core/Chip";

const GlobalObjectList = ({
  loadGlobalObjectFromServer,
  setSelectedGlobalObject,
  globalObjects = [],
  count = 0,
  page = 0,
  size = 10
}) => {
  useEffect(() => {
    loadGlobalObjectFromServer(0, size);
  }, [loadGlobalObjectFromServer, size]);

  const [selectedIndex, setSelectedIndex] = React.useState(0);

  const handleListItemClick = index => {
    setSelectedIndex(index);
  };

  const useStyles = makeStyles(theme => ({
    root: {
      width: "100%",
      backgroundColor: theme.palette.background.paper
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

  const classes = useStyles();

  const onChangePage = (event, pageSelected) => {
    loadGlobalObjectFromServer(pageSelected, size);
    setSelectedIndex(0);
  };

  return (
    <Box>
      <Grid container>
        <Grid item xs={4}>
          <Box mt={1} mr={2}>
            <List className={classes.root} component="nav">
              {map(globalObjects, (globalObject, index) => (
                <Grid container>
                  <Grid item xs={12} sm={12}>
                    <ListItem
                      divider
                      button
                      selected={selectedIndex === index}
                      onClick={event => {
                        handleListItemClick(index);
                        setSelectedGlobalObject(globalObjects[index]);
                      }}
                    >
                      <ListItemAvatar>
                        <Avatar className={classes.purple}>
                          <ImportantDevicesIcon />
                        </Avatar>
                      </ListItemAvatar>
                      <ListItemText
                        primary={globalObject.name}
                        secondary={
                          <DisplayDate
                            dateString={globalObject.created_date_time}
                            prefixString="Created on "
                          />
                        }
                      />
                      <Grid item xs={12} sm={3}>
                          <Chip label={globalObject.application.name}/>
                        </Grid>
                    </ListItem>
                  </Grid>
                </Grid>
              ))}
            </List>
            {count > 0 && (
              <Box display="flex" flexDirection="row-reverse">
                <AddGlobalObject />
                <TablePagination
                  rowsPerPageOptions={[size]}
                  component="div"
                  count={count}
                  rowsPerPage={size}
                  page={page}
                  onChangePage={onChangePage}
                />
              </Box>
            )}
          </Box>
        </Grid>
        <Grid item xs={8}>
          <Box mt={2} mr={2}>
            {count > 0 && <GlobalObjectTab />}
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
};

const mapStateToProps = state => ({
  globalObjects: getGlobalObjects(state),
  count: getGlobalObjectCount(state),
  page: getPage(state),
  size: getSize(state)
});

const mapDispatchToProps = dispatch => ({
  loadGlobalObjectFromServer: (page, size) => {
    dispatch(listGlobalObjectRequest(page, size));
  },
  setSelectedGlobalObject: globalObject => {
    dispatch(setSelectedGlobalObjectRequest(globalObject));
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(GlobalObjectList);
