import React from "react";
import { connect } from "react-redux";
import { getSessionId } from "../../selectors/debug/selector";
import EmptyDebug from "./EmptyDebug";
import DebugEditor from "./DebugEditor";

const Debug = ({ sessionId }) => {
  const getDebugComponent = () => {
    if (sessionId) {
      return <DebugEditor />;
    } else {
      return <EmptyDebug />;
    }
  };

  return getDebugComponent();
};

const mapStateToProps = state => ({
  sessionId: getSessionId(state)
});
const mapDispatchToProps = dispatch => ({});

export default connect(mapStateToProps, mapDispatchToProps)(Debug);
