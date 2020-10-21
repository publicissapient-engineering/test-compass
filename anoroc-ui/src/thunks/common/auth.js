export const token = () => {
  return localStorage.getItem("anoroc-token");
};

export const setToken = token => {
  localStorage.setItem("anoroc-token", token);
};

export const clear = () => {
  localStorage.clear();
};
