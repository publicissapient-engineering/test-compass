import React, { useEffect } from "react";
import ApplicationList from "./ApplicationList";
import EmptyApplication from "./EmptyApplication";
import { connect } from "react-redux";
import { getApplicationCountRequest } from "../../thunks/application/thunks";
import { getApplicationCount } from "../../selectors/application/selector";

export const Application = ({ loadCountFromServer, count }) => {
  useEffect(() => {
    loadCountFromServer();
  }, [loadCountFromServer]);

  const applicationComponent = () => {
    if (count !== 0) {
      return <ApplicationList />;
    } else {
      return <EmptyApplication />;
    }
  };
  return applicationComponent();
};

const mapStateToProps = state => ({
  count: getApplicationCount(state)
});

const mapDispatchToProps = dispatch => ({
  loadCountFromServer: () => {
    dispatch(getApplicationCountRequest());
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(Application);
