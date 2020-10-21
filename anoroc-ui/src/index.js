import React from "react";
import ReactDOM from "react-dom";
import { Provider } from "react-redux";
import { configureStore } from "./store/store";
import { persistStore } from "redux-persist";
import { PersistGate } from "redux-persist/lib/integration/react";
import Anoroc from "./Anoroc";
import * as serviceWorker from "./serviceWorker";
import { ReactKeycloakProvider } from "@react-keycloak/web";
import keycloak from "./keycloak";

const store = configureStore();
const persistor = persistStore(store);

const keycloakProviderInitConfig = {
  onLoad: "login-required"
};

ReactDOM.render(
  <ReactKeycloakProvider
    authClient={keycloak}
    initConfig={keycloakProviderInitConfig}
  >
    <Provider store={store}>
      <PersistGate persistor={persistor} loading={<div>Loading...</div>}>
        <Anoroc />
      </PersistGate>
    </Provider>
  </ReactKeycloakProvider>,
  document.getElementById("root")
);

serviceWorker.unregister();
