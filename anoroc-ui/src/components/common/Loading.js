import React from "react";
import Box from "@material-ui/core/Box";
import LinearProgress from "@material-ui/core/LinearProgress";
import { connect } from "react-redux";
import { loading } from "../../selectors/common/selector";

const Loading = ({ isLoading }) => {
  return (
    <Box>{isLoading === true && <LinearProgress color="secondary" />}</Box>
  );
};

const mapStateToProps = state => ({
  isLoading: loading(state)
});

const mapDispatchToProps = dispatch => ({});

export default connect(mapStateToProps, mapDispatchToProps)(Loading);
