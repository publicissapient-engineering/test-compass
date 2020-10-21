import {
  ADD_BUSINESS_SCENARIO,
  LIST_BUSINESS_SCENARIO,
  BUSINESS_SCENARIO_COUNT,
  SET_BUSINESS_SCENARIO_PAGE,
  SET_BUSINESS_SCENARIO_SIZE,
  UPDATE_BUSINESS_SCENARIO,
  SET_SELECTED_BUSINESS_SCENARIO
} from "../../actions/businessScenario/actions";

export const businessScenarios = (state = [], action) => {
  const { type, payload } = action;
  switch (type) {
    case ADD_BUSINESS_SCENARIO: {
      return state;
    }
    case LIST_BUSINESS_SCENARIO: {
      const { businessScenarios } = payload;
      const businessScenariosList = {
        ...state,
        data: businessScenarios
      };
      return businessScenariosList;
    }
    case BUSINESS_SCENARIO_COUNT: {
      const { count } = payload;
      const businessScenarioCount = {
        ...state,
        count
      };
      return businessScenarioCount;
    }
    case SET_BUSINESS_SCENARIO_PAGE: {
      const { page } = payload;
      const pageData = {
        ...state,
        page
      };
      return pageData;
    }
    case SET_BUSINESS_SCENARIO_SIZE: {
      const { size } = payload;
      const sizeData = {
        ...state,
        size
      };
      return sizeData;
    }
    case UPDATE_BUSINESS_SCENARIO: {
      const { businessScenario: updatedBusinessScenario } = payload;
      return {
        ...state,
        data: state.data.map(businessScenario => {
          if (businessScenario.id === updatedBusinessScenario.id) {
            return updatedBusinessScenario;
          }
          return businessScenario;
        })
      };
    }
    case SET_SELECTED_BUSINESS_SCENARIO: {
      const { selectedBusinessScenario } = payload;
      const selectedBusinessScenarioData = {
        ...state,
        selectedBusinessScenario
      };
      return selectedBusinessScenarioData;
    }
    default:
      return state;
  }
};
