import React from "react";
import { connect } from "react-redux";
import { RP_LAUNCH_URL } from "../../EnvVariables";

const Reportportal = ({}) => {
    const rpLaunchUrl = `${RP_LAUNCH_URL}`
    return ( 
    <div>
        <iframe 
        src = {rpLaunchUrl}
        height = "800"
        width = "100%"
        frameBorder = "0"
        title = "Reports" />
    </div>
    )
}
const mapStateToProps = state => ({});

const mapDispatchToProps = dispatch => ({});

export default connect(mapStateToProps, mapDispatchToProps)(Reportportal);