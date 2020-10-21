import { BASE_URL } from "../../EnvVariables";
import {
  addGlobalObject,
  listGlobalObject,
  getGlobalObjectCount,
  setGlobalObjectPage,
  setGlobalObjectSize,
  updateGlobalObject,
  setSelectedGlobalObject,
  setSelectedGlobalObjectId,
  allGlobalObjects
} from "../../actions/globalObject/actions";
import {
  startLoading,
  stopLoading,
  displayMessage
} from "../../actions/common/actions";
import { token } from "../common/auth";

export const addGlobalObjectRequest = (
  name,
  applicationId,
  content = ""
) => async dispatch => {
  try {
    dispatch(startLoading());
    const body = {
      name: name,
      content: content,
      application_id: applicationId
    };
    const response = await fetch(`${BASE_URL}/api/globalObject/save`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token()
      },
      method: "post",
      body: JSON.stringify(body)
    });
    const globalObject = await response.json();
    if (response.status === 201) {
      dispatch(addGlobalObject(globalObject));
      dispatch(listGlobalObjectRequest());
      dispatch(displayMessage("Global Object added successfully."));
    } else {
      dispatch(
        displayMessage(
          "Could not add the global object. Try the operation again."
        )
      );
    }
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage("Could not add the global object. Try the operation again")
    );
    dispatch(stopLoading());
  }
};

export const getGlobalObjectCountRequest = () => async dispatch => {
  dispatch(startLoading());
  console.log("Get global object count request");
  try {
    const globalObjectCountResponse = await fetch(
      `${BASE_URL}/api/globalObject/count`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const globalObjectCount = await globalObjectCountResponse.json();
    dispatch(getGlobalObjectCount(globalObjectCount.count));
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not fetch global object count. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};

export const listGlobalObjectRequest = (
  page = 0,
  size = 10
) => async dispatch => {
  try {
    dispatch(startLoading());
    const globalObjectCountResponse = await fetch(
      `${BASE_URL}/api/globalObject/count`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const globalObjectCount = await globalObjectCountResponse.json();
    dispatch(getGlobalObjectCount(globalObjectCount.count));
    dispatch(setGlobalObjectPage(page));
    dispatch(setGlobalObjectSize(size));
    const response = await fetch(
      `${BASE_URL}/api/globalObject/list?page=${page}&size=${size}`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const globalObjects = await response.json();
    dispatch(listGlobalObject(globalObjects));
    if (globalObjects.length > 0) {
      dispatch(setSelectedGlobalObject(globalObjects[0]));
    }
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not fetch the global objects. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};

export const updateGlobalObjectRequest = (
  id = 0,
  name = "",
  content = "",
  applicationId = 0
) => async dispatch => {
  try {
    const body = {
      id: id,
      name: name,
      content: content,
      application_id: applicationId
    };
    const response = await fetch(`${BASE_URL}/api/globalObject/save`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token()
      },
      method: "post",
      body: JSON.stringify(body)
    });
    const globalObject = await response.json();
    if (response.status === 202) {
      dispatch(displayMessage("Global object updated successfully."));
      dispatch(updateGlobalObject(globalObject));
      dispatch(setSelectedGlobalObject(globalObject));
    } else {
      dispatch(
        displayMessage(
          "Could not update the global object. Try the operation again."
        )
      );
    }
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not update the global object. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};

export const setSelectedGlobalObjectRequest = globalObject => async dispatch => {
  dispatch(setSelectedGlobalObject(globalObject));
};

export const getAllGlobalObjectsRequest = () => async dispatch => {
  try {
    dispatch(startLoading());
    const response = await fetch(`${BASE_URL}/api/globalObject/all`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token()
      }
    });
    const globalObjects = await response.json();
    dispatch(allGlobalObjects(globalObjects));
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not fetch the global objects. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};

export const setSelectedGlobalObjectIdRequest = globalObjectId => async dispatch => {
  dispatch(setSelectedGlobalObjectId(globalObjectId));
};
