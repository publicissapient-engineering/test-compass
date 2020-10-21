import React, { useEffect } from "react";
import List from "@material-ui/core/List";
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import EnvironmentTab from "./EnvironmentTab";
import TablePagination from "@material-ui/core/TablePagination";
import { connect } from "react-redux";
import {
  listEnvironmentRequest,
  setSelectedEnvironmentRequest
} from "../../thunks/environment/thunks";
import AddEnvironment from "./AddEnvironment";
import { map } from "lodash";
import {
  getEnvironments,
  getEnvironmentCount,
  getPage,
  getSize
} from "../../selectors/environment/selector";
import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import ListItemText from "@material-ui/core/ListItemText";
import DisplayDate from "../common/DisplayDate";
import { deepPurple, grey } from "@material-ui/core/colors";
import ImportantDevicesIcon from "@material-ui/icons/ImportantDevices";
import Chip from "@material-ui/core/Chip";

const EnvironmentList = ({
  loadEnvironmentFromServer,
  setSelectedEnvironment,
  environments = [],
  count = 0,
  page = 0,
  size = 10
}) => {
  useEffect(() => {
    loadEnvironmentFromServer(0, size);
  }, [loadEnvironmentFromServer, size]);

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
    loadEnvironmentFromServer(pageSelected, size);
    setSelectedIndex(0);
  };

  return (
    <Box>
      <Grid container>
        <Grid item xs={4}>
          <Box mt={1} mr={2}>
            <List className={classes.root} component="nav">
              {console.log(environments)}
              {map(environments, (environment, index) => (               
                <ListItem
                  divider
                  button
                  selected={selectedIndex === index}
                  onClick={event => {
                    handleListItemClick(index);
                    setSelectedEnvironment(environments[index]);
                  }}
                >
                  <ListItemAvatar>
                    <Avatar className={classes.purple}>
                      <ImportantDevicesIcon />
                    </Avatar>
                  </ListItemAvatar>
                  <ListItemText
                    primary={environment.name}
                    secondary={
                      <DisplayDate
                        dateString={environment.created_date_time}
                        prefixString="Created on "
                      />
                    }
                  />
                  </ListItem>                  
              ))}
            </List>
            {count > 0 && (
              <Box display="flex" flexDirection="row-reverse">
                <AddEnvironment />
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
            {count > 0 && <EnvironmentTab />}
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
};

const mapStateToProps = state => ({
  environments: getEnvironments(state),
  count: getEnvironmentCount(state),
  page: getPage(state),
  size: getSize(state)
});

const mapDispatchToProps = dispatch => ({
  loadEnvironmentFromServer: (page, size) => {
    dispatch(listEnvironmentRequest(page, size));
  },
  setSelectedEnvironment: environment => {
    dispatch(setSelectedEnvironmentRequest(environment));
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(EnvironmentList);
