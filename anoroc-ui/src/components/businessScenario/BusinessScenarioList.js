import React, { useEffect } from "react";
import List from "@material-ui/core/List";
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import TablePagination from "@material-ui/core/TablePagination";
import { connect } from "react-redux";
import {
  listBusinessScenarioRequest,
  setSelectedBusinessScenarioRequest
} from "../../thunks/businessScenario/thunks";
import AddBusinessScenario from "./AddBusinessScenario";
import { map } from "lodash";
import {
  getBusinessScenarios,
  getBusinessScenarioCount,
  getPage,
  getSize
} from "../../selectors/businessScenario/selector";
import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import ListItemText from "@material-ui/core/ListItemText";
import DisplayDate from "../common/DisplayDate";
import { deepPurple } from "@material-ui/core/colors";
import BusinessCenterIcon from "@material-ui/icons/BusinessCenter";
import BusinessScenarioTab from "./BusinessScenarioTab";

const BusinessScenarioList = ({
  loadBusinessScenarioFromServer,
  setSelectedBusinessScenario,
  businessScenarios = [],
  count = 0,
  page = 0,
  size = 10
}) => {
  useEffect(() => {
    loadBusinessScenarioFromServer(page, size);
  }, [loadBusinessScenarioFromServer, page, size]);

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
    }
  }));

  const classes = useStyles();

  const onChangePage = (event, pageSelected) => {
    loadBusinessScenarioFromServer(pageSelected, size);
    setSelectedIndex(0);
  };

  return (
    <Box>
      <Grid container>
        <Grid item xs={4}>
          <Box mt={1} mr={2}>
            <List className={classes.root} component="nav">
              {map(businessScenarios, (businessScenario, index) => (
                <ListItem
                  divider
                  button
                  selected={selectedIndex === index}
                  onClick={event => {
                    handleListItemClick(index);
                    setSelectedBusinessScenario(businessScenarios[index]);
                  }}
                >
                  <ListItemAvatar>
                    <Avatar className={classes.purple}>
                      <BusinessCenterIcon />
                    </Avatar>
                  </ListItemAvatar>
                  <ListItemText
                    primary={businessScenario.name}
                    secondary={
                      <DisplayDate
                        dateString={businessScenario.created_date_time}
                        prefixString="Created on "
                      />
                    }
                  />
                </ListItem>
              ))}
            </List>
            <Box display="flex" flexDirection="row-reverse">
              <AddBusinessScenario />
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
        <Grid item xs={8}>
          <Box mt={2} mr={2}>
            <BusinessScenarioTab />
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
};

const mapStateToProps = state => ({
  businessScenarios: getBusinessScenarios(state),
  count: getBusinessScenarioCount(state),
  page: getPage(state),
  size: getSize(state)
});

const mapDispatchToProps = dispatch => ({
  loadBusinessScenarioFromServer: (page, size) => {
    dispatch(listBusinessScenarioRequest(page, size));
  },
  setSelectedBusinessScenario: businessScenario => {
    dispatch(setSelectedBusinessScenarioRequest(businessScenario));
  }
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BusinessScenarioList);
