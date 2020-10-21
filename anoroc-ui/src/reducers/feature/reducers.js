import {
  ADD_FEATURE,
  LIST_FEATURE,
  FEATURE_COUNT,
  SET_FEATURE_PAGE,
  SET_FEATURE_SIZE,
  UPDATE_FEATURE,
  SET_SELECTED_FEATURE,
  LIST_INCLUDE_FEATURE,
  INCLUDE_FEATURE_COUNT,
  SET_INCLUDE_FEATURE_PAGE,
  SET_INCLUDE_FEATURE_SIZE,
  LIST_ASSOCIATED_FEATURE
} from "../../actions/feature/actions";

export const features = (state = [], action) => {
  const { type, payload } = action;
  switch (type) {
    case ADD_FEATURE: {
      return state;
    }
    case LIST_FEATURE: {
      const { features } = payload;
      const featuresList = {
        ...state,
        data: features
      };
      return featuresList;
    }
    case FEATURE_COUNT: {
      const { count } = payload;
      const featureCount = {
        ...state,
        count
      };
      return featureCount;
    }
    case SET_FEATURE_PAGE: {
      const { page } = payload;
      const pageData = {
        ...state,
        page
      };
      return pageData;
    }
    case SET_FEATURE_SIZE: {
      const { size } = payload;
      const sizeData = {
        ...state,
        size
      };
      return sizeData;
    }
    case UPDATE_FEATURE: {
      const { feature: updatedFeature } = payload;
      return {
        ...state,
        data: state.data.map(feature => {
          if (feature.id === updatedFeature.id) {
            return updatedFeature;
          }
          return feature;
        })
      };
    }
    case SET_SELECTED_FEATURE: {
      const { selectedFeature } = payload;
      const selectedFeatureData = {
        ...state,
        selectedFeature
      };
      return selectedFeatureData;
    }
    case LIST_INCLUDE_FEATURE: {
      const { includeFeatures } = payload;
      const featuresList = {
        ...state,
        IncludeFeatureData: includeFeatures
      };
      return featuresList;
    }
    case INCLUDE_FEATURE_COUNT: {
      const { includeFeatureCount } = payload;
      const featureCount = {
        ...state,
        includeFeatureCount
      };
      return featureCount;
    }
    case SET_INCLUDE_FEATURE_PAGE: {
      const { includeFeaturePage } = payload;
      const pageData = {
        ...state,
        includeFeaturePage
      };
      return pageData;
    }
    case SET_INCLUDE_FEATURE_SIZE: {
      const { includeFeatureSize } = payload;
      const sizeData = {
        ...state,
        includeFeatureSize
      };
      return sizeData;
    }
    case LIST_ASSOCIATED_FEATURE: {
      const { associatedFeatures } = payload;
      const featuresList = {
        ...state,
        associatedFeaturesData: associatedFeatures
      };
      return featuresList;
    }
    default:
      return state;
  }
};
