import { BASE_URL } from "../../EnvVariables";
import {
  addBusinessScenario,
  listBusinessScenario,
  getBusinessScenarioCount,
  setBusinessScenarioPage,
  setBusinessScenarioSize,
  updateBusinessScenario,
  setSelectedBusinessScenario
} from "../../actions/businessScenario/actions";
import {
  startLoading,
  stopLoading,
  displayMessage
} from "../../actions/common/actions";
import { token } from "../common/auth";

export const addBusinessScenarioRequest = (
  name,
  description = ""
) => async dispatch => {
  try {
    dispatch(startLoading());
    const body = {
      name: name,
      description: description
    };
    const response = await fetch(`${BASE_URL}/api/businessScenario/save`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token()
      },
      method: "post",
      body: JSON.stringify(body)
    });
    const businessScenario = await response.json();
    if (response.status === 201) {
      dispatch(addBusinessScenario(businessScenario));
      dispatch(listBusinessScenarioRequest());
      dispatch(displayMessage("Business scenario added successfully."));
    } else {
      dispatch(
        displayMessage(
          "Could not add the business scenario. Try the operation again."
        )
      );
    }
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not add the business scenario. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};

export const getBusinessScenarioCountRequest = () => async dispatch => {
  dispatch(startLoading());
  console.log("Get businessScenario count request");
  try {
    const businessScenarioCountResponse = await fetch(
      `${BASE_URL}/api/businessScenario/count`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const businessScenarioCount = await businessScenarioCountResponse.json();
    dispatch(getBusinessScenarioCount(businessScenarioCount.count));
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not fetch business scenario count. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};

export const listBusinessScenarioRequest = (
  page = 0,
  size = 10
) => async dispatch => {
  try {
    dispatch(startLoading());
    const businessScenarioCountResponse = await fetch(
      `${BASE_URL}/api/businessScenario/count`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const businessScenarioCount = await businessScenarioCountResponse.json();
    dispatch(getBusinessScenarioCount(businessScenarioCount.count));
    dispatch(setBusinessScenarioPage(page));
    dispatch(setBusinessScenarioSize(size));
    const response = await fetch(
      `${BASE_URL}/api/businessScenario/list?page=${page}&size=${size}`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const businessScenarios = await response.json();
    dispatch(listBusinessScenario(businessScenarios));
    if (businessScenarios.length > 0) {
      dispatch(setSelectedBusinessScenario(businessScenarios[0]));
    }
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not fetch the business scenarios. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};

export const updateBusinessScenarioRequest = (
  id = 0,
  name = "",
  description = "",
  featureIds = []
) => async dispatch => {
  try {
    const body = {
      id: id,
      name: name,
      description: description,
      featureIds: featureIds
    };
    const response = await fetch(`${BASE_URL}/api/businessScenario/save`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token()
      },
      method: "post",
      body: JSON.stringify(body)
    });
    const businessScenario = await response.json();
    if (response.status === 202) {
      dispatch(displayMessage("Business Scenario updated successfully."));
      dispatch(updateBusinessScenario(businessScenario));
      dispatch(setSelectedBusinessScenario(businessScenario));
    } else {
      dispatch(
        displayMessage(
          "Could not update the business scenario. Try the operation again."
        )
      );
    }
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not update the business scenario. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};

export const setSelectedBusinessScenarioRequest = businessScenario => async dispatch => {
  dispatch(startLoading());
  console.log("Set selected business scenario request");
  try {
    const businessScenarioResponse = await fetch(
      `${BASE_URL}/api/businessScenario/${businessScenario.id}`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const businessScenarioObj = await businessScenarioResponse.json();
    dispatch(setSelectedBusinessScenario(businessScenarioObj));
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not fetch business scenario count. Try the operation again"
      )
    );
    dispatch(stopLoading());
  }
};

export const setBusinessScenarioSearchTxt = (
  enteredBusinessScenarioSearchTxt,
  businessScenarios,
  page,
  size,
  count
) => async dispatch => {
  dispatch(getBusinessScenarioCount(count));
  dispatch(setBusinessScenarioPage(page));
  dispatch(setBusinessScenarioSize(size));
  dispatch(listBusinessScenario(businessScenarios));
};
