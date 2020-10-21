export const ADD_APPLICATION = "ADD_APPLICATION";

export const addApplication = application => ({
  type: ADD_APPLICATION,
  payload: { application }
});

export const APPLICATION_COUNT = "APPLICATION_COUNT";

export const getApplicationCount = count => ({
  type: APPLICATION_COUNT,
  payload: { count }
});

export const LIST_APPLICATION = "LIST_APPLICATION";

export const listApplication = applications => ({
  type: LIST_APPLICATION,
  payload: { applications }
});

export const SET_APPLICATION_PAGE = "SET_APPLICATION_PAGE";

export const setApplicationPage = page => ({
  type: SET_APPLICATION_PAGE,
  payload: { page }
});

export const SET_APPLICATION_SIZE = "SET_APPLICATION_SIZE";

export const setApplicationSize = size => ({
  type: SET_APPLICATION_SIZE,
  payload: { size }
});

export const ALL_APPLICATIONS = "ALL_APPLICATIONS";

export const allApplications = applications => ({
  type: ALL_APPLICATIONS,
  payload: { applications }
});

export const SET_SELECTED_APPLICATION = "SELECTED_APPLICATION";

export const setSelectedApplication = selectedApplication => ({
  type: SET_SELECTED_APPLICATION,
  payload: { selectedApplication }
})