import React, { useEffect } from "react";
import GlobalObjectList from "./GlobalObjectList";
import EmptyGlobalObject from "./EmptyGlobalObject";
import { connect } from "react-redux";
import { getGlobalObjectCount } from "../../selectors/globalObject/selector";
import { getGlobalObjectCountRequest } from "../../thunks/globalObject/thunks";

const GlobalObject = ({ loadCountFromServer, count }) => {
  useEffect(() => {
    loadCountFromServer();
  }, [loadCountFromServer]);

  const globalObjectComponent = () => {
    if (count !== 0) {
      return <GlobalObjectList />;
    }
    return <EmptyGlobalObject />;
  };

  return globalObjectComponent();
};

const mapStateToProps = state => ({
  count: getGlobalObjectCount(state)
});

const mapDispatchToProps = dispatch => ({
  loadCountFromServer: () => {
    dispatch(getGlobalObjectCountRequest());
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(GlobalObject);
  