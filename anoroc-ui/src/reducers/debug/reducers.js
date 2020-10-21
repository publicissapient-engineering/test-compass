import { START_DEBUG_SESSION } from "../../actions/debug/actions";

export const debug = (state = "", action) => {
  const { type, payload } = action;
  switch (type) {
    case START_DEBUG_SESSION: {
      const { sessionId } = payload;
      return {
        ...state,
        sessionId
      };
    }
    default:
      return state;
  }
};
