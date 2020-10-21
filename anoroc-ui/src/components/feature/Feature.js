import React, { useEffect } from "react";
import FeatureList from "./FeatureList";
import EmptyFeature from "./EmptyFeature";
import { connect } from "react-redux";
import { getFeatureCount } from "../../selectors/feature/selector";
import { getFeatureCountRequest } from "../../thunks/feature/thunks";

const Feature = ({ loadCountFromServer, count }) => {
  useEffect(() => {
    loadCountFromServer();
  }, [loadCountFromServer]);

  const featureComponent = () => {
    if (count !== 0) {
      return <FeatureList />;
    }
    return <EmptyFeature />;
  };

  return featureComponent();
};

const mapStateToProps = state => ({
  count: getFeatureCount(state)
});

const mapDispatchToProps = dispatch => ({
  loadCountFromServer: () => {
    dispatch(getFeatureCountRequest());
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(Feature);
