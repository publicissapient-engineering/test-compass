import { createStore, combineReducers, applyMiddleware } from "redux";
import { persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage";
import thunk from "redux-thunk";
import { composeWithDevTools } from "redux-devtools-extension";
import autoMergeLevel2 from "redux-persist/lib/stateReconciler/autoMergeLevel2";
import { features } from "../reducers/feature/reducers";
import { runs } from "../reducers/run/reducers";
import { loading, message } from "../reducers/common/reducers";
import { debug } from "../reducers/debug/reducers";
import { applications } from "../reducers/application/reducers";
import { businessScenarios } from "../reducers/businessScenario/reducers";
import { environments } from "../reducers/environment/reducers";
import { globalObjects } from "../reducers/globalObject/reducers";

const reducers = {
  features,
  loading,
  message,
  runs,
  debug,
  applications,
  businessScenarios,
  environments,
  globalObjects
};

const rootReducer = combineReducers(reducers);
const persistConfig = {
  key: "anoroc",
  storage,
  stateReconciler: autoMergeLevel2
};
const persistedReducer = persistReducer(persistConfig, rootReducer);

export const configureStore = () =>
  createStore(persistedReducer, composeWithDevTools(applyMiddleware(thunk)));
