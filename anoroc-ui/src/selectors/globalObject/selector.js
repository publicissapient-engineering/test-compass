export const getGlobalObjects= state => {
    return state.globalObjects.data;
  };
  
  export const getGlobalObjectCount = state => {
    return state.globalObjects.count;
  };
  
  export const getSize = state => {
    return state.globalObjects.size;
  };
  
  export const getPage = state => {
    return state.globalObjects.page;
  };
  
  export const getSelectedGlobalObject = state => {
    return state.globalObjects.selectedGlobalObject;
  };
  
  export const getAllGlobalObjects= state => {
    return state.globalObjects.allGlobalObjects;
  };
  
  export const getSelectedGlobalObjectId = state => {
    return state.globalObjects.selectedGlobalObjectId;
  };
  