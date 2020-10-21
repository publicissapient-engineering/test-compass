import React, { useEffect } from "react";
import RunList from "./RunList";
import EmptyRun from "./EmptyRun";
import { connect } from "react-redux";
import { getRunCount } from "../../selectors/run/selector";
import { listRunRequest } from "../../thunks/run/thunks";

const Run = ({ count, listRun }) => {
  useEffect(() => listRun());
  const runComponent = () => {
    if (count !== 0) {
      return <RunList />;
    }
    return <EmptyRun />;
  };

  return runComponent();
};

const mapStateToProps = state => ({
  count: getRunCount(state)
});

const mapDispatchToProps = dispatch => ({
  listRun: () => {
    dispatch(listRunRequest());
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(Run);
