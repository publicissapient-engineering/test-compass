export const getFeatures = state => {
  return state.features.data;
};

export const getFeatureCount = state => {
  return state.features.count;
};

export const getSize = state => {
  return state.features.size;
};

export const getPage = state => {
  return state.features.page;
};

export const getIncludeFeatures = state => {
  return state.features.IncludeFeatureData;
};

export const getIncludeFeatureCount = state => {
  return state.features.includeFeatureCount;
};

export const getIncludeFeatureSize = state => {
  return state.features.includeFeatureSize;
};

export const getIncludeFeaturePage = state => {
  return state.features.includeFeaturePage;
};

export const getSelectedFeature = state => {
  return state.features.selectedFeature;
};

export const getAssociatedFeatures = state => {
  return state.features.associatedFeaturesData;
};


