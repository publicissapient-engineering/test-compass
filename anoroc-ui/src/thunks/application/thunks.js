import { BASE_URL } from "../../EnvVariables";
import {
  addApplication,
  getApplicationCount,
  listApplication,
  setApplicationPage,
  setApplicationSize,
  allApplications,
  setSelectedApplication
} from "../../actions/application/actions";
import {
  startLoading,
  stopLoading,
  displayMessage
} from "../../actions/common/actions";
import { token } from "../common/auth";

export const addOrEditApplicationRequest = (name, id = 0) => async dispatch => {
  console.log("Add application request", name);
  try {
    dispatch(startLoading());
    const body = {
      name: name,
      id: id
    };
    const response = await fetch(`${BASE_URL}/api/applications/save`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token()
      },
      method: "post",
      body: JSON.stringify(body)
    });
    const application = await response.json();
    if (response.status === 201 || response.status === 202) {
      dispatch(addApplication(application));
      dispatch(listApplicationRequest());
      if (id === 0) {
        dispatch(displayMessage("Application added successfully."));
      } else {
        dispatch(displayMessage("Application updated successfully."));
      }
    } else {
      dispatch(
        displayMessage(
          "Could not add the application. Try the operation again."
        )
      );
    }
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage("Could not add the application. Try the operation again")
    );
    dispatch(stopLoading());
  }
};

export const getApplicationCountRequest = () => async dispatch => {
  dispatch(startLoading());
  console.log("Get application count request");
  try {
    const applicationCountResponse = await fetch(
      `${BASE_URL}/api/applications/count`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const applicationCount = await applicationCountResponse.json();
    dispatch(getApplicationCount(applicationCount.count));
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not fetch application count. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};

export const listApplicationRequest = (
  page = 0,
  size = 10
) => async dispatch => {
  try {
    dispatch(startLoading());
    const applicationCountResponse = await fetch(
      `${BASE_URL}/api/applications/count`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const applicationCount = await applicationCountResponse.json();
    dispatch(getApplicationCount(applicationCount.count));
    dispatch(setApplicationPage(page));
    dispatch(setApplicationSize(size));
    const response = await fetch(
      `${BASE_URL}/api/applications/list?page=${page}&size=${size}`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const applications = await response.json();
    dispatch(listApplication(applications));
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not fetch the applications. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};

export const getAllApplicationsRequest = () => async dispatch => {
  try {
    dispatch(startLoading());
    const response = await fetch(`${BASE_URL}/api/applications/all`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token()
      },
      method: "get"
    });
    const applications = await response.json();
    dispatch(allApplications(applications));
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not fetch the applications. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};

export const setSelectedApplicationRequest = application => async dispatch => {
  dispatch(setSelectedApplication(application));
};
