import { startDebugSession } from "../../actions/debug/actions";
import {
  startLoading,
  stopLoading,
  displayMessage
} from "../../actions/common/actions";

export const startDebugSessionRequest = () => async dispatch => {
  try {
    dispatch(startLoading());
    dispatch(startDebugSession("sessionId"));
    displayMessage("Debug session started");
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not start the debug session. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};
