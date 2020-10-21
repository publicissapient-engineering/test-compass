import {
  ADD_ENVIRONMENT,
  LIST_ENVIRONMENT,
  ENVIRONMENT_COUNT,
  SET_ENVIRONMENT_PAGE,
  SET_ENVIRONMENT_SIZE,
  UPDATE_ENVIRONMENT,
  SET_SELECTED_ENVIRONMENT,
  ALL_ENVIRONMENTS,
  SET_SELECTED_ENVIRONMENT_ID
} from "../../actions/environment/actions";

export const environments = (state = [], action) => {
  const { type, payload } = action;
  switch (type) {
    case ADD_ENVIRONMENT: {
      return state;
    }
    case LIST_ENVIRONMENT: {
      const { environments } = payload;
      const environmentsList = {
        ...state,
        data: environments
      };
      return environmentsList;
    }
    case ENVIRONMENT_COUNT: {
      const { count } = payload;
      const environmentCount = {
        ...state,
        count
      };
      return environmentCount;
    }
    case SET_ENVIRONMENT_PAGE: {
      const { page } = payload;
      const pageData = {
        ...state,
        page
      };
      return pageData;
    }
    case SET_ENVIRONMENT_SIZE: {
      const { size } = payload;
      const sizeData = {
        ...state,
        size
      };
      return sizeData;
    }
    case UPDATE_ENVIRONMENT: {
      const { environment: updatedEnvironment } = payload;
      return {
        ...state,
        data: state.data.map(environment => {
          if (environment.id === updatedEnvironment.id) {
            return updatedEnvironment;
          }
          return environment;
        })
      };
    }
    case SET_SELECTED_ENVIRONMENT: {
      const { selectedEnvironment } = payload;
      const selectedEnvironmentData = {
        ...state,
        selectedEnvironment
      };
      return selectedEnvironmentData;
    }
    case ALL_ENVIRONMENTS: {
      const { environments } = payload;
      const environmentsList = {
        ...state,
        allEnvironments: environments
      };
      return environmentsList;
    }
    case SET_SELECTED_ENVIRONMENT_ID: {
      const { selectedEnvironmentId } = payload;
      const selectedEnvironmentData = {
        ...state,
        selectedEnvironmentId
      };
      return selectedEnvironmentData;
    }
    default:
      return state;
  }
};
