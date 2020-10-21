import React, { useEffect } from "react";
import EnvironmentList from "./EnvironmentList";
import EmptyEnvironment from "./EmptyEnvironment";
import { connect } from "react-redux";
import { getEnvironmentCount } from "../../selectors/environment/selector";
import { getEnvironmentCountRequest } from "../../thunks/environment/thunks";

const Environment = ({ loadCountFromServer, count }) => {
  useEffect(() => {
    loadCountFromServer();
  }, [loadCountFromServer]);

  const environmentComponent = () => {
    if (count !== 0) {
      return <EnvironmentList />;
    }
    return <EmptyEnvironment />;
  };

  return environmentComponent();
};

const mapStateToProps = state => ({
  count: getEnvironmentCount(state)
});

const mapDispatchToProps = dispatch => ({
  loadCountFromServer: () => {
    dispatch(getEnvironmentCountRequest());
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(Environment);
