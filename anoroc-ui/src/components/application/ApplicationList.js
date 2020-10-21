import React, { useEffect } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import Divider from "@material-ui/core/Divider";
import { AppBar, Toolbar } from "@material-ui/core";
import { listApplicationRequest,setSelectedApplicationRequest } from "../../thunks/application/thunks";
import {
  getApplications,
  getApplicationCount,
  getPage,
  getSize,
  getSelectedApplication
} from "../../selectors/application/selector";
import { deepPurple, grey } from "@material-ui/core/colors";
import AcUnitIcon from "@material-ui/icons/AcUnit";
import SportsKabaddiIcon from "@material-ui/icons/SportsKabaddi";
import Avatar from "@material-ui/core/Avatar";
import { connect } from "react-redux";
import AddApplication from "./AddApplication";
import EditApplication from "./EditApplication";
import { map } from "lodash";
import TablePagination from "@material-ui/core/TablePagination";
import DisplayDate from "../common/DisplayDate";

const useStyles = makeStyles(theme => ({
  root: {
    maxWidth: 345
  },
  appbar_root: {
    flexGrow: 1
  },

  title: {
    flexGrow: 1,
  },
  media: {
    height: 140
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

const ApplicationList = ({
  loadApplicationFromServer,
  applications = [],
  setSelectedApplication,
  count = 0,
  page = 0,
  size = 10
}) => {
  useEffect(() => {
    loadApplicationFromServer(page, size);
  }, [loadApplicationFromServer, page, size]);



  const onChangePage = (event, pageSelected) => {
    loadApplicationFromServer(pageSelected, size);
  };

  
  const classes = useStyles();
  return (
    <Grid>
      <Grid
        container
        direction="row"
        alignItems="center"
        spacing={3}
        alignContent="center"
      >
        {map(applications, (application, index) => (
          <Grid item spacing={3} xs={3}>
            <Box mt={4} ml={2}>
              <Card className={classes.root}>
              <div className={classes.appbar_root}>
                <AppBar position="static">
                  <Toolbar>
                    <Typography variant="h6" className={classes.title} >
                    {application.name}
                    </Typography>
                    <div onClick= {event => {
                        setSelectedApplication(applications[index]);
                      }} >
                    <EditApplication color="inherit" 
                      ></EditApplication>
                      </div>
                  </Toolbar>
                </AppBar>
              </div>
                <CardActionArea>
                  <CardContent>
                    <Typography
                      variant="body2"
                      color="textSecondary"
                      component="p"
                    >
                      Created on
                      <DisplayDate dateString={application.created_date_time} />
                    </Typography>
                  </CardContent>
                </CardActionArea>
                <Divider />
                <CardActions>
                  <Button size="small" color="primary">
                    Features
                  </Button>
                  <Avatar className={classes.purple}>
                    <AcUnitIcon />
                  </Avatar>
                  <Avatar className={classes.brown}>
                    <SportsKabaddiIcon />
                  </Avatar>
                </CardActions>
              </Card>
            </Box>
          </Grid>
        ))}
        <Grid
          item
          direction="row"
          alignItems="center"
          spacing={3}
          alignContent="center"
        >
          <Box mt={12}>
            <AddApplication />
          </Box>
        </Grid>
      </Grid>
      <Box mt={4}>
        <TablePagination
          rowsPerPageOptions={[size]}
          component="div"
          count={count}
          rowsPerPage={size}
          page={page}
          onChangePage={onChangePage}
        />
      </Box>
    </Grid>
  );
};
const mapStateToProps = state => ({
  applications: getApplications(state),
  count: getApplicationCount(state),
  page: getPage(state),
  size: getSize(state),
  selectedApplication: getSelectedApplication(state)
});

const mapDispatchToProps = dispatch => ({
  loadApplicationFromServer: (page, size) => {
    dispatch(listApplicationRequest(page, size));
  },
  setSelectedApplication: application => {
    dispatch(setSelectedApplicationRequest(application));
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(ApplicationList);
