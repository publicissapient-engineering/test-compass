export const ADD_FEATURE = "ADD_FEATURE";

export const addFeature = feature => ({
  type: ADD_FEATURE,
  payload: { feature }
});

export const LIST_FEATURE = "LIST_FEATURE";

export const listFeature = features => ({
  type: LIST_FEATURE,
  payload: { features }
});

export const FEATURE_COUNT = "FEATURE_COUNT";

export const getFeatureCount = count => ({
  type: FEATURE_COUNT,
  payload: { count }
});

export const SET_FEATURE_PAGE = "SET_FEATURE_PAGE";

export const setFeaturePage = page => ({
  type: SET_FEATURE_PAGE,
  payload: { page }
});

export const SET_FEATURE_SIZE = "SET_FEATURE_SIZE";

export const setFeatureSize = size => ({
  type: SET_FEATURE_SIZE,
  payload: { size }
});

export const UPDATE_FEATURE = "UPDATE_FEATURE";

export const updateFeature = feature => ({
  type: UPDATE_FEATURE,
  payload: { feature }
});

export const SET_SELECTED_FEATURE = "SET_SELECTED_FEATURE";

export const setSelectedFeature = selectedFeature => ({
  type: SET_SELECTED_FEATURE,
  payload: { selectedFeature }
});

export const INCLUDE_FEATURE_COUNT = "INCLUDE_FEATURE_COUNT";

export const getIncludeFeatureCount = includeFeatureCount => ({
  type: INCLUDE_FEATURE_COUNT,
  payload: { includeFeatureCount }
});

export const SET_INCLUDE_FEATURE_PAGE = "SET_INCLUDE_FEATURE_PAGE";

export const setIncludeFeaturePage = includeFeaturePage => ({
  type: SET_INCLUDE_FEATURE_PAGE,
  payload: { includeFeaturePage }
});

export const SET_INCLUDE_FEATURE_SIZE = "SET_INCLUDE_FEATURE_SIZE";

export const setIncludeFeatureSize = includeFeatureSize => ({
  type: SET_INCLUDE_FEATURE_SIZE,
  payload: { includeFeatureSize }
});

export const LIST_INCLUDE_FEATURE = "LIST_INCLUDE_FEATURE";

export const listIncludeFeature = includeFeatures => ({
  type: LIST_INCLUDE_FEATURE,
  payload: { includeFeatures }
});

export const LIST_ASSOCIATED_FEATURE = "LIST_ASSOCIATED_FEATURE";

export const listAssociatedFeature = associatedFeatures => ({
  type: LIST_ASSOCIATED_FEATURE,
  payload: { associatedFeatures }
});


