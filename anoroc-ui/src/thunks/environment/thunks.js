import { BASE_URL } from "../../EnvVariables";
import {
  addEnvironment,
  listEnvironment,
  getEnvironmentCount,
  setEnvironmentPage,
  setEnvironmentSize,
  updateEnvironment,
  setSelectedEnvironment,
  setSelectedEnvironmentId,
  allEnvironments
} from "../../actions/environment/actions";
import {
  startLoading,
  stopLoading,
  displayMessage
} from "../../actions/common/actions";
import { token } from "../common/auth";

export const addEnvironmentRequest = (name, content = "") => async dispatch => {
  try {
    dispatch(startLoading());
    const body = {
      name: name,
      content: content
    };
    const response = await fetch(`${BASE_URL}/api/environment/save`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token()
      },
      method: "post",
      body: JSON.stringify(body)
    });
    const environment = await response.json();
    if (response.status === 201) {
      dispatch(addEnvironment(environment));
      dispatch(listEnvironmentRequest());
      dispatch(displayMessage("Environment added successfully."));
    } else {
      dispatch(
        displayMessage(
          "Could not add the environment. Try the operation again."
        )
      );
    }
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage("Could not add the environment. Try the operation again")
    );
    dispatch(stopLoading());
  }
};

export const getEnvironmentCountRequest = () => async dispatch => {
  dispatch(startLoading());
  console.log("Get environment count request");
  try {
    const environmentCountResponse = await fetch(
      `${BASE_URL}/api/environment/count`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const environmentCount = await environmentCountResponse.json();
    dispatch(getEnvironmentCount(environmentCount.count));
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not fetch environment count. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};

export const listEnvironmentRequest = (
  page = 0,
  size = 10
) => async dispatch => {
  try {
    dispatch(startLoading());
    const environmentCountResponse = await fetch(
      `${BASE_URL}/api/environment/count`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const environmentCount = await environmentCountResponse.json();
    dispatch(getEnvironmentCount(environmentCount.count));
    dispatch(setEnvironmentPage(page));
    dispatch(setEnvironmentSize(size));
    const response = await fetch(
      `${BASE_URL}/api/environment/list?page=${page}&size=${size}`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const environments = await response.json();
    dispatch(listEnvironment(environments));
    if (environments.length > 0) {
      dispatch(setSelectedEnvironment(environments[0]));
    }
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not fetch the environments. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};

export const updateEnvironmentRequest = (
  id = 0,
  name = "",
  anorocContents = "",
  karateContents = "",
  settingsContent = ""
) => async dispatch => {
  try {
    const body = {
      id: id,
      name: name,
      anoroc_content: anorocContents,
      karate_content: karateContents,
      settings_content: settingsContent
    };
    const response = await fetch(`${BASE_URL}/api/environment/save`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token()
      },
      method: "post",
      body: JSON.stringify(body)
    });
    const environment = await response.json();
    if (response.status === 202) {
      dispatch(displayMessage("Environment updated successfully."));
      dispatch(updateEnvironment(environment));
      dispatch(setSelectedEnvironment(environment));
    } else {
      dispatch(
        displayMessage(
          "Could not update the environment. Try the operation again."
        )
      );
    }
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not update the environment. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};

export const setSelectedEnvironmentRequest = environment => async dispatch => {
  dispatch(setSelectedEnvironment(environment));
};

export const getAllEnvironmentsRequest = () => async dispatch => {
  try {
    dispatch(startLoading());
    const response = await fetch(`${BASE_URL}/api/environment/all`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token()
      }
    });
    const environments = await response.json();
    dispatch(allEnvironments(environments));
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not fetch the environments. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};

export const setSelectedEnvironmentIdRequest = environmentId => async dispatch => {
  dispatch(setSelectedEnvironmentId(environmentId));
};
