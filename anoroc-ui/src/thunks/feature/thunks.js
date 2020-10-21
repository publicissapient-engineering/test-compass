import { BASE_URL } from "../../EnvVariables";
import {
  addFeature,
  listFeature,
  getFeatureCount,
  setFeaturePage,
  setFeatureSize,
  updateFeature,
  setSelectedFeature,
  listIncludeFeature,
  getIncludeFeatureCount,
  setIncludeFeaturePage,
  setIncludeFeatureSize,
  listAssociatedFeature
} from "../../actions/feature/actions";
import {
  startLoading,
  stopLoading,
  displayMessage
} from "../../actions/common/actions";
import { token } from "../common/auth";

export const addFeatureRequest = (
  name,
  featureType = "KARATE",
  applicationId = 0,
  content = "",
  xpath = ""
) => async dispatch => {
  try {
    dispatch(startLoading());
    const body = {
      name: name,
      content: content,
      xpath: xpath,
      feature_type: featureType,
      application_id: applicationId
    };
    const response = await fetch(`${BASE_URL}/api/feature/save`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token()
      },
      method: "post",
      body: JSON.stringify(body)
    });
    const feature = await response.json();
    if (response.status === 200) {
      dispatch(addFeature(feature));
      dispatch(listFeatureRequest());
      dispatch(displayMessage("Feature added successfully."));
    } else {
      dispatch(
        displayMessage("Could not add the feature. Try the operation again.")
      );
    }
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage("Could not add the feature. Try the operation again")
    );
    dispatch(stopLoading());
  }
};

export const getFeatureCountRequest = () => async dispatch => {
  dispatch(startLoading());
  console.log("Get feature count request");
  try {
    const featureCountResponse = await fetch(
      `${BASE_URL}/api/feature/count?contains=&notIn=0`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const featureCount = await featureCountResponse.json();
    dispatch(getFeatureCount(featureCount.count));
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage("Could not fetch feature count. Try the operation again")
    );
    dispatch(stopLoading());
  }
};

export const listFeatureRequest = (
  page = 0,
  size = 10,
  searchByName = ""
) => async dispatch => {
  try {
    dispatch(startLoading());
    const featureCountResponse = await fetch(
      `${BASE_URL}/api/feature/count?contains=${searchByName}&notIn=0`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const featureCount = await featureCountResponse.json();
    dispatch(getFeatureCount(featureCount.count));
    dispatch(setFeaturePage(page));
    dispatch(setFeatureSize(size));
    const response = await fetch(
      `${BASE_URL}/api/feature/list?page=${page}&size=${size}&name=${searchByName}`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const features = await response.json();
    dispatch(listFeature(features));
    if (features.length > 0) {
      dispatch(setSelectedFeature(features[0]));
    }
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage("Could not fetch the features. Try the operation again")
    );
    dispatch(stopLoading());
  }
};

export const listIncludeFeatureRequest = (
  page = 0,
  size = 10,
  searchText = "",
  selectedFeatureId = 0
) => async dispatch => {
  try {
    dispatch(startLoading());
    console.log("Get list of include feature request");
    const featureCountResponse = await fetch(
      `${BASE_URL}/api/feature/count?contains=${searchText}&notIn=${selectedFeatureId}`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const featureCount = await featureCountResponse.json();
    dispatch(getIncludeFeatureCount(featureCount.count));
    dispatch(setIncludeFeaturePage(page));
    dispatch(setIncludeFeatureSize(size));
    const response = await fetch(
      `${BASE_URL}/api/feature/list?page=${page}&size=${size}&notIn=${selectedFeatureId}&name=${searchText}`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const features = await response.json();
    dispatch(listIncludeFeature(features));
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage("Could not fetch the features. Try the operation again")
    );
    dispatch(stopLoading());
  }
};

export const updateFeatureRequest = (
  id = 0,
  name = "",
  content = "",
  xpath = "",
  featureType = "KARATE",
  applicationId = 0,
  includeFeatures = []
) => async dispatch => {
  try {
    const body = {
      id: id,
      name: name,
      content: content,
      xpath: xpath,
      feature_type: featureType,
      application_id: applicationId,
      include_features: includeFeatures
    };
    const response = await fetch(`${BASE_URL}/api/feature/save`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token()
      },
      method: "post",
      body: JSON.stringify(body)
    });
    const feature = await response.json();
    if (response.status === 200) {
      dispatch(displayMessage("Feature updated successfully."));
      dispatch(updateFeature(feature));
      dispatch(setSelectedFeature(feature));
    } else {
      dispatch(
        displayMessage("Could not update the feature. Try the operation again.")
      );
    }
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage("Could not update the feature. Try the operation again")
    );
    dispatch(stopLoading());
  }
};

export const associatedFeaturesRequest = selectedFeatureId => async dispatch => {
  console.log("Get associated feature request");
  try {
    dispatch(startLoading());
    const response = await fetch(
      `${BASE_URL}/api/feature/associatedList/${selectedFeatureId}`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const associatedFeatures = await response.json();
    dispatch(listAssociatedFeature(associatedFeatures));
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage("Could not fetch the features. Try the operation again")
    );
    dispatch(stopLoading());
  }
};

export const setSelectedFeatureRequest = feature => async dispatch => {
  dispatch(setSelectedFeature(feature));
};
