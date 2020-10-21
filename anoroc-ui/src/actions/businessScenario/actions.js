export const ADD_BUSINESS_SCENARIO = "ADD_BUSINESS_SCENARIO";

export const addBusinessScenario = businessScenario => ({
  type: ADD_BUSINESS_SCENARIO,
  payload: { businessScenario }
});

export const LIST_BUSINESS_SCENARIO = "LIST_BUSINESS_SCENARIO";

export const listBusinessScenario = businessScenarios => ({
  type: LIST_BUSINESS_SCENARIO,
  payload: { businessScenarios }
});

export const BUSINESS_SCENARIO_COUNT = "BUSINESS_SCENARIO_COUNT";

export const getBusinessScenarioCount = count => ({
  type: BUSINESS_SCENARIO_COUNT,
  payload: { count }
});

export const SET_BUSINESS_SCENARIO_PAGE = "SET_BUSINESS_SCENARIO_PAGE";

export const setBusinessScenarioPage = page => ({
  type: SET_BUSINESS_SCENARIO_PAGE,
  payload: { page }
});

export const SET_BUSINESS_SCENARIO_SIZE = "SET_BUSINESS_SCENARIO_SIZE";

export const setBusinessScenarioSize = size => ({
  type: SET_BUSINESS_SCENARIO_SIZE,
  payload: { size }
});

export const UPDATE_BUSINESS_SCENARIO = "UPDATE_BUSINESS_SCENARIO";

export const updateBusinessScenario = businessScenario => ({
  type: UPDATE_BUSINESS_SCENARIO,
  payload: { businessScenario }
});

export const SET_SELECTED_BUSINESS_SCENARIO = "SET_SELECTED_BUSINESS_SCENARIO";

export const setSelectedBusinessScenario = selectedBusinessScenario => ({
  type: SET_SELECTED_BUSINESS_SCENARIO,
  payload: { selectedBusinessScenario }
});
