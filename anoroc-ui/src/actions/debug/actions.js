export const START_DEBUG_SESSION = "START_DEBUG_SESSION";

export const startDebugSession = sessionId => ({
  type: START_DEBUG_SESSION,
  payload: { sessionId }
});
