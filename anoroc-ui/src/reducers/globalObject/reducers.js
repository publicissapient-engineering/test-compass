import {
    ADD_GLOBAL_OBJECT,
    LIST_GLOBAL_OBJECT,
    GLOBAL_OBJECT_COUNT,
    SET_GLOBAL_OBJECT_PAGE,
    SET_GLOBAL_OBJECT_SIZE,
    UPDATE_GLOBAL_OBJECT,
    SET_SELECTED_GLOBAL_OBJECT,
    ALL_GLOBAL_OBJECTS,
    SET_SELECTED_GLOBAL_OBJECT_ID
  } from "../../actions/globalObject/actions";
  
  export const globalObjects = (state = [], action) => {
    const { type, payload } = action;
    switch (type) {
      case ADD_GLOBAL_OBJECT: {
        return state;
      }
      case LIST_GLOBAL_OBJECT: {
        const { globalObjects } = payload;
        const globalObjectsList = {
          ...state,
          data: globalObjects
        };
        return globalObjectsList;
      }
      case GLOBAL_OBJECT_COUNT: {
        const { count } = payload;
        const globalObjectCount = {
          ...state,
          count
        };
        return globalObjectCount;
      }
      case SET_GLOBAL_OBJECT_PAGE: {
        const { page } = payload;
        const pageData = {
          ...state,
          page
        };
        return pageData;
      }
      case SET_GLOBAL_OBJECT_SIZE: {
        const { size } = payload;
        const sizeData = {
          ...state,
          size
        };
        return sizeData;
      }
      case UPDATE_GLOBAL_OBJECT: {
        const { globalObject: updatedGlobalObject } = payload;
        return {
          ...state,
          data: state.data.map(globalObject => {
            if (globalObject.id === updatedGlobalObject.id) {
              return updatedGlobalObject;
            }
            return globalObject;
          })
        };
      }
      case SET_SELECTED_GLOBAL_OBJECT: {
        const { selectedGlobalObject } = payload;
        const selectedGlobalObjectData = {
          ...state,
          selectedGlobalObject
        };
        return selectedGlobalObjectData;
      }
      case ALL_GLOBAL_OBJECTS: {
        const { globalObjects } = payload;
        const globalObjectsList = {
          ...state,
          allGlobalObjects: globalObjects
        };
        return globalObjectsList;
      }
      case SET_SELECTED_GLOBAL_OBJECT_ID: {
        const { selectedGlobalObjectId } = payload;
        const selectedGlobalObjectData = {
          ...state,
          selectedGlobalObjectId
        };
        return selectedGlobalObjectData;
      }
      default:
        return state;
    }
  };
  