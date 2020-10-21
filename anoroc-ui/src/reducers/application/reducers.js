import {
  ADD_APPLICATION,
  APPLICATION_COUNT,
  LIST_APPLICATION,
  SET_APPLICATION_PAGE,
  SET_APPLICATION_SIZE,
  ALL_APPLICATIONS,
  SET_SELECTED_APPLICATION
} from "../../actions/application/actions";

export const applications = (state = [], action) => {
  const { type, payload } = action;
  switch (type) {
    case ADD_APPLICATION: {
      return state;
    }
    case LIST_APPLICATION: {
      const { applications } = payload;
      const applicationsList = {
        ...state,
        data: applications
      };
      return applicationsList;
    }
    case APPLICATION_COUNT: {
      const { count } = payload;
      const applicationCount = {
        ...state,
        count
      };
      return applicationCount;
    }
    case SET_APPLICATION_PAGE: {
      const { page } = payload;
      const pageData = {
        ...state,
        page
      };
      return pageData;
    }
    case SET_APPLICATION_SIZE: {
      const { size } = payload;
      const sizeData = {
        ...state,
        size
      };
      return sizeData;
    }
    case ALL_APPLICATIONS: {
      const { applications } = payload;
      const applicationsList = {
        ...state,
        allApplications: applications
      };
      return applicationsList;
    }
    case SET_SELECTED_APPLICATION: {
      const{ selectedApplication } = payload;
      const application = {
        ...state,
        selectedApplication
      };
      return application;
    }
    default:
      return state;
  }
};
