import { BASE_URL } from "../../EnvVariables";
import {
  startLoading,
  stopLoading,
  displayMessage
} from "../../actions/common/actions";
import {
  runFeature,
  listRuns,
  setPage,
  setSize,
  setSelectedRun,
  setRunCount
} from "../../actions/run/actions";
import { token } from "../common/auth";

export const runFeatureRequest = (
  featureId,
  selectedEnvironmentId
) => async dispatch => {
  try {
    dispatch(startLoading());
    if (selectedEnvironmentId) {
      const body = {
        feature_id: featureId,
        env_id: selectedEnvironmentId
      };
      const response = await fetch(`${BASE_URL}/api/run/feature`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "post",
        body: JSON.stringify(body)
      });
      const runReponse = await response.json();
      dispatch(
        displayMessage(
          "Run initiated successfully. Please check the runs for more details."
        )
      );
      dispatch(runFeature(runReponse));
    } else {
      dispatch(
        displayMessage(
          "Please select environment details before running the feature."
        )
      );
    }
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not intiate the run for the feature. Please try again later."
      )
    );
    dispatch(stopLoading());
  }
};

export const runBusinessScenarioRequest = (
  bussinessScenarioId,
  selectedEnvironmentId
) => async dispatch => {
  try {
    dispatch(startLoading());
    if (selectedEnvironmentId) {
      const body = {
        business_scenario_id: bussinessScenarioId,
        env_id: selectedEnvironmentId
      };

      const response = await fetch(`${BASE_URL}/api/run/businessScenario`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "post",
        body: JSON.stringify(body)
      });
      const runReponse = await response.json();
      dispatch(
        displayMessage(
          "Run initiated successfully. Please check the runs for more details."
        )
      );
      dispatch(runFeature(runReponse));
    } else {
      dispatch(
        displayMessage(
          "Please select environment details from the before running the business scenario."
        )
      );
    }
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage(
        "Could not intiate the run for the business scenario. Please try again later."
      )
    );
    dispatch(stopLoading());
  }
};

export const listRunRequest = (page = 0, size = 10) => async dispatch => {
  try {
    dispatch(startLoading());
    const runCountResponse = await fetch(`${BASE_URL}/api/run/count`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token()
      },
      method: "get"
    });
    const runCount = await runCountResponse.json();
    dispatch(setRunCount(runCount.count));
    dispatch(setPage(page));
    dispatch(setSize(size));
    const response = await fetch(
      `${BASE_URL}/api/run/list?page=${page}&size=${size}`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token()
        },
        method: "get"
      }
    );
    const runs = await response.json();
    dispatch(listRuns(runs));
    if (runs.length > 0) {
      dispatch(setSelectedRun(runs[0]));
    }
    dispatch(stopLoading());
  } catch (e) {
    dispatch(
      displayMessage("Could not fetch the runs. Try the operation again")
    );
    dispatch(stopLoading());
  }
};

export const setSelectedRunRequest = run => async dispatch => {
  dispatch(setSelectedRun(run));
};
