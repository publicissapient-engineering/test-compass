export const ADD_ENVIRONMENT = "ADD_ENVIRONMENT";

export const addEnvironment = environment => ({
  type: ADD_ENVIRONMENT,
  payload: { environment }
});

export const LIST_ENVIRONMENT = "LIST_ENVIRONMENT";

export const listEnvironment = environments => ({
  type: LIST_ENVIRONMENT,
  payload: { environments }
});

export const ENVIRONMENT_COUNT = "ENVIRONMENT_COUNT";

export const getEnvironmentCount = count => ({
  type: ENVIRONMENT_COUNT,
  payload: { count }
});

export const SET_ENVIRONMENT_PAGE = "SET_ENVIRONMENT_PAGE";

export const setEnvironmentPage = page => ({
  type: SET_ENVIRONMENT_PAGE,
  payload: { page }
});

export const SET_ENVIRONMENT_SIZE = "SET_ENVIRONMENT_SIZE";

export const setEnvironmentSize = size => ({
  type: SET_ENVIRONMENT_SIZE,
  payload: { size }
});

export const UPDATE_ENVIRONMENT = "UPDATE_ENVIRONMENT";

export const updateEnvironment = environment => ({
  type: UPDATE_ENVIRONMENT,
  payload: { environment }
});

export const SET_SELECTED_ENVIRONMENT = "SET_SELECTED_ENVIRONMENT";

export const setSelectedEnvironment = selectedEnvironment => ({
  type: SET_SELECTED_ENVIRONMENT,
  payload: { selectedEnvironment }
});

export const ALL_ENVIRONMENTS = "ALL_ENVIRONMENTS";

export const allEnvironments = environments => ({
  type: ALL_ENVIRONMENTS,
  payload: { environments }
});

export const SET_SELECTED_ENVIRONMENT_ID = "SET_SELECTED_ENVIRONMENT_ID";

export const setSelectedEnvironmentId = selectedEnvironmentId => ({
  type: SET_SELECTED_ENVIRONMENT_ID,
  payload: { selectedEnvironmentId }
});
