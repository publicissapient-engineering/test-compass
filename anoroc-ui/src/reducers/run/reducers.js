import {
  RUN_FEATURE,
  LIST_RUN,
  SET_PAGE,
  SET_SIZE,
  SET_SELECTED_RUN,
  SET_RUN_COUNT
} from "../../actions/run/actions";

export const runs = (state = "", action) => {
  const { type, payload } = action;
  switch (type) {
    case RUN_FEATURE: {
      const { run } = payload;
      return {
        ...state,
        run
      };
    }
    case LIST_RUN: {
      const { runs } = payload;
      return {
        ...state,
        data: runs
      };
    }
    case SET_PAGE: {
      const { page } = payload;
      return {
        ...state,
        page
      };
    }
    case SET_SIZE: {
      const { size } = payload;
      return {
        ...state,
        size
      };
    }
    case SET_SELECTED_RUN: {
      const { selectedRun } = payload;
      return {
        ...state,
        selectedRun
      };
    }
    case SET_RUN_COUNT: {
      const { count } = payload;
      return {
        ...state,
        count
      };
    }
    default:
      return state;
  }
};
