import React, { useEffect } from "react";
import BusinessScenarioList from "./BusinessScenarioList";
import EmptyBusinessScenario from "./EmptyBusinessScenario";
import { connect } from "react-redux";
import { getBusinessScenarioCount } from "../../selectors/businessScenario/selector";
import { getBusinessScenarioCountRequest } from "../../thunks/businessScenario/thunks";

const BusinessScenario = ({ loadCountFromServer, count }) => {
  useEffect(() => {
    loadCountFromServer();
  }, [loadCountFromServer]);

  const businessScenarioComponent = () => {
    if (count !== 0) {
      return <BusinessScenarioList />;
    }
    return <EmptyBusinessScenario />;
  };

  return businessScenarioComponent();
};

const mapStateToProps = state => ({
  count: getBusinessScenarioCount(state)
});

const mapDispatchToProps = dispatch => ({
  loadCountFromServer: () => {
    dispatch(getBusinessScenarioCountRequest());
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(BusinessScenario);
