export const getRuns = state => {
  return state.runs.data;
};

export const getRunCount = state => {
  return state.runs.count;
};

export const getSize = state => {
  return state.runs.size;
};

export const getPage = state => {
  return state.runs.page;
};

export const getSelectedRun = state => {
  return state.runs.selectedRun;
};
