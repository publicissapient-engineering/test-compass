export const getEnvironments = state => {
  return state.environments.data;
};

export const getEnvironmentCount = state => {
  return state.environments.count;
};

export const getSize = state => {
  return state.environments.size;
};

export const getPage = state => {
  return state.environments.page;
};

export const getSelectedEnvironment = state => {
  return state.environments.selectedEnvironment;
};

export const getAllEnvironments = state => {
  return state.environments.allEnvironments;
};

export const getSelectedEnvironmentId = state => {
  return state.environments.selectedEnvironmentId;
};
