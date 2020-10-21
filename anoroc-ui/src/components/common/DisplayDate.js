import React from "react";
import Box from "@material-ui/core/Box";

const DisplayDate = ({ prefixString = "", dateString, postfixString = "" }) => {
  const options = {
    dateStyle: "full",
    timeStyle: "short",
    hour12: true,
    hourCycle: "h12"
  };
  return (
    <Box>
      {prefixString}
      {new Date(Date.parse(dateString)).toLocaleString("en-US", options)}
      {postfixString}
    </Box>
  );
};

export default DisplayDate;
