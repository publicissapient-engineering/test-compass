import React, { useEffect } from "react";
import List from "@material-ui/core/List";
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import FeatureTab from "./FeatureTab";
import TablePagination from "@material-ui/core/TablePagination";
import { connect } from "react-redux";
import {
  listFeatureRequest,
  setSelectedFeatureRequest,
  associatedFeaturesRequest
} from "../../thunks/feature/thunks";
import AddFeature from "./AddFeature";
import { map } from "lodash";
import {
  getFeatures,
  getFeatureCount,
  getPage,
  getSize,
  getAssociatedFeatures
} from "../../selectors/feature/selector";
import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import ListItemText from "@material-ui/core/ListItemText";
import DisplayDate from "../common/DisplayDate";
import { deepPurple, grey } from "@material-ui/core/colors";
import AcUnitIcon from "@material-ui/icons/AcUnit";
import SportsKabaddiIcon from "@material-ui/icons/SportsKabaddi";
import Chip from "@material-ui/core/Chip";

const FeatureList = ({
  loadFeatureFromServer,
  setSelectedFeature,
  features = [],
  count = 0,
  page = 0,
  size = 10
}) => {
  useEffect(() => {
    loadFeatureFromServer(0, size);
  }, [loadFeatureFromServer, size]);

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
    loadFeatureFromServer(pageSelected, size);
    setSelectedIndex(0);
  };

  return (
    <Box>
      <Grid container>
        <Grid item xs={4}>
          <Box mt={1} mr={2}>
            <List className={classes.root} component="nav">
              {map(features, (feature, index) => (
                <Grid container>
                  <Grid item xs={12} sm={12}>
                    <ListItem
                      divider
                      button
                      selected={selectedIndex === index}
                      onClick={event => {
                        handleListItemClick(index);
                        setSelectedFeature(features[index]);
                      }}
                    >
                      <ListItemAvatar>
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
                      </ListItemAvatar>
                      <ListItemText
                        primary={feature.name}
                        secondary={
                          <DisplayDate
                            dateString={feature.created_date_time}
                            prefixString="Created on "
                          />
                        }
                      />
                      <Grid item xs={12} sm={3}>
                        <Chip label={feature.application.name} />
                      </Grid>
                    </ListItem>
                  </Grid>
                </Grid>
              ))}
            </List>
            {count > 0 && (
              <Box display="flex" flexDirection="row-reverse">
                <AddFeature />
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
            {count > 0 && <FeatureTab />}
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
};

const mapStateToProps = state => ({
  features: getFeatures(state),
  count: getFeatureCount(state),
  page: getPage(state),
  size: getSize(state),
  associatedFeatures: getAssociatedFeatures(state)
});

const mapDispatchToProps = dispatch => ({
  loadFeatureFromServer: (page, size) => {
    dispatch(listFeatureRequest(page, size));
  },
  setSelectedFeature: feature => {
    dispatch(setSelectedFeatureRequest(feature));
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(FeatureList);
