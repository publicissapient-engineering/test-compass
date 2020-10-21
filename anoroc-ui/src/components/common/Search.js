import React from "react";
import Box from "@material-ui/core/Box";
import { fade,makeStyles } from "@material-ui/core/styles";
import SearchIcon from '@material-ui/icons/Search';
import InputBase from '@material-ui/core/InputBase';
import { getSearchText } from "../../selectors/common/selector"; 
import {
  getBusinessScenarios,
  getBusinessScenarioCount,
  getPage,
  getSize
} from "../../selectors/businessScenario/selector";
import {setBusinessScenarioSearchTxt} from "../../thunks/businessScenario/thunks";
import { connect } from "react-redux";
import { Button } from "@material-ui/core";


const useStyles = makeStyles(theme => ({
    search: {
        position: 'relative',
        borderRadius: theme.shape.borderRadius,
        backgroundColor: fade(theme.palette.common.white, 0.15),
        '&:hover': {
          backgroundColor: fade(theme.palette.common.white, 0.25),
        },
        marginRight: theme.spacing(2),
        marginLeft: 0,
        width: '100%',
        [theme.breakpoints.up('sm')]: {
          marginLeft: theme.spacing(3),
          width: 'auto',
        },
      },
      searchIcon: {
        padding: theme.spacing(0, 2),
        height: '100%',
        position: 'absolute',
        pointerEvents: 'none',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
      },
      inputRoot: {
        color: 'inherit',
      },
      inputInput: {
        padding: theme.spacing(1, 1, 1, 0),
        // vertical padding + font size from searchIcon
        paddingLeft: `calc(1em + ${theme.spacing(4)}px)`,
        transition: theme.transitions.create('width'),
        width: '100%',
        [theme.breakpoints.up('md')]: {
          width: '20ch',
        },
      }
}));


const SearchBox = (props) => {
  
    const classes = useStyles();
    

    return (
        <Box>
            <div className={classes.search}>
              <div className={classes.searchIcon}>
                <SearchIcon />
              </div>
              <InputBase placeholder="Search…" inputProps={{ 'aria-label': 'search' }} classes={{
                root: classes.inputRoot,
                input: classes.inputInput,
              }} onChange={props.searchHandler}/>
            </div>
        </Box>
    );
};


const mapStateToProps = state => ({});

const mapDispatchToProps = dispatch => ({});

export default connect(mapStateToProps, mapDispatchToProps)(SearchBox);
