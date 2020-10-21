export const RUN_FEATURE = "RUN_FEATURE";

export const runFeature = run => ({
  type: RUN_FEATURE,
  payload: { run }
});

export const LIST_RUN = "LIST_RUN";

export const listRuns = runs => ({
  type: LIST_RUN,
  payload: { runs }
});

export const SET_PAGE = "SET_PAGE";

export const setPage = page => ({
  type: SET_PAGE,
  payload: { page }
});

export const SET_SIZE = "SET_SIZE";

export const setSize = size => ({
  type: SET_SIZE,
  payload: { size }
});

export const SET_SELECTED_RUN = "SET_SELECTED_RUN";

export const setSelectedRun = selectedRun => ({
  type: SET_SELECTED_RUN,
  payload: { selectedRun }
});

export const SET_RUN_COUNT = "SET_RUN_COUNT";

export const setRunCount = count => ({
  type: SET_RUN_COUNT,
  payload: { count }
});
