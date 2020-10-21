export const getApplicationCount = state => {
  return state.applications.count;
};

export const getApplications = state => {
  return state.applications.data;
};

export const getSize = state => {
  return state.applications.size;
};

export const getPage = state => {
  return state.applications.page;
};

export const getAllApplications = state => {
  return state.applications.allApplications;
};

export const getSelectedApplication = state => {
  return state.applications.selectedApplication;
}