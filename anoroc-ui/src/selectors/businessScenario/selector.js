export const getBusinessScenarios = state => {
  return state.businessScenarios.data;
};

export const getBusinessScenarioCount = state => {
  return state.businessScenarios.count;
};

export const getSize = state => {
  return state.businessScenarios.size;
};

export const getPage = state => {
  return state.businessScenarios.page;
};

export const getSelectedBusinessScenario = state => {
  return state.businessScenarios.selectedBusinessScenario;
};
