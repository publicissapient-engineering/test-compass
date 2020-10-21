export const ADD_GLOBAL_OBJECT = "ADD_GLOBAL_OBJECT";

export const addGlobalObject = globalObject => ({
  type: ADD_GLOBAL_OBJECT,
  payload: { globalObject }
});

export const LIST_GLOBAL_OBJECT = "LIST_GLOBAL_OBJECT";

export const listGlobalObject = globalObjects => ({
  type: LIST_GLOBAL_OBJECT,
  payload: { globalObjects }
});

export const GLOBAL_OBJECT_COUNT = "GLOBAL_OBJECT_COUNT";

export const getGlobalObjectCount = count => ({
  type: GLOBAL_OBJECT_COUNT,
  payload: { count }
});

export const SET_GLOBAL_OBJECT_PAGE = "SET_GLOBAL_OBJECT_PAGE";

export const setGlobalObjectPage = page => ({
  type: SET_GLOBAL_OBJECT_PAGE,
  payload: { page }
});

export const SET_GLOBAL_OBJECT_SIZE = "SET_GLOBAL_OBJECT_SIZE";

export const setGlobalObjectSize = size => ({
  type: SET_GLOBAL_OBJECT_SIZE,
  payload: { size }
});

export const UPDATE_GLOBAL_OBJECT = "UPDATE_GLOBAL_OBJECT";

export const updateGlobalObject = globalObject => ({
  type: UPDATE_GLOBAL_OBJECT,
  payload: { globalObject }
});

export const SET_SELECTED_GLOBAL_OBJECT = "SET_SELECTED_GLOBAL_OBJECT";

export const setSelectedGlobalObject = selectedGlobalObject => ({
  type: SET_SELECTED_GLOBAL_OBJECT,
  payload: { selectedGlobalObject }
});

export const ALL_GLOBAL_OBJECTS = "ALL_GLOBAL_OBJECTS";

export const allGlobalObjects = globalObjects => ({
  type: ALL_GLOBAL_OBJECTS,
  payload: { globalObjects }
});

export const SET_SELECTED_GLOBAL_OBJECT_ID = "SET_SELECTED_GLOBAL_OBJECT_ID";

export const setSelectedGlobalObjectId = selectedGlobalObjectId => ({
  type: SET_SELECTED_GLOBAL_OBJECT_ID,
  payload: { selectedGlobalObjectId }
});
