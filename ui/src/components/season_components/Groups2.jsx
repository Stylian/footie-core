import React, {Component} from 'react';
import {
    AppBar,
    Box,
    Card,
    CardContent,
    CardHeader,
    Grid,
    Tab,
    TableBody,
    TableCell,
    TableRow,
    Tabs
} from "@material-ui/core";
import GroupsMatches from "./groups_components/GroupsMatches";
import GroupsDisplay from "./groups_components/GroupsDisplay";

class Groups2 extends Component {

    constructor(props) {
        super(props);

        this.state = {
            tabActive: 0,
            isLoaded: false
        };

    }

    componentDidMount() {
        fetch("/rest/persist/tabs/groups2/" + this.props.year)
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState(state => {
                        return {
                            ...state,
                            tabActive: result,
                            isLoaded: true,
                        }
                    });
                },
                (error) => {
                    this.setState(state => {
                        return {
                            ...state,
                            isLoaded: true,
                            error
                        }
                    });
                }
            )
    }

    handleChange = (event, newValue) => {

        fetch("/rest/persist/tabs/groups2/" + this.props.year, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: newValue
        })
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState(state => {
                        return {
                            ...state,
                            tabActive: newValue,
                        }
                    });
                },
                (error) => {
                    this.setState(state => {
                        return {
                            ...state,
                            isLoaded: true,
                            error
                        }
                    });
                }
            )
    }


    render() {
        return (
            this.state.isLoaded ? (
                <Box style={{margin: 30, "margin-top": 10}}>
                    <AppBar position="static">
                        <Tabs value={this.state.tabActive} onChange={this.handleChange}>
                            <Tab label="Groups"/>
                            <Tab label="Matches"/>
                             <Tab label="Rules"/>
                        </Tabs>
                    </AppBar>

                    {this.state.tabActive === 0 && <GroupsDisplay year={this.props.year} round={2}/>}
                    {this.state.tabActive === 1 && <GroupsMatches year={this.props.year} round={2}/>}
                     {this.state.tabActive === 2 && (
                                            <Grid item sm={4}>
                                                <Card style={{margin: 20}}>
                                                    <CardHeader title={"Rules"} align={"center"} titleTypographyProps={{variant: 'h7'}}
                                                    />
                                                    <CardContent>
                                                        <table className="table">
                                                            <TableBody>
                                                                <TableRow>
                                                                    <TableCell align={"right"}>Teams</TableCell>
                                                                    <TableCell>
                                                                        <ul>
                                                                            <li>8</li>
                                                                        </ul>
                                                                    </TableCell>
                                                                </TableRow>
                                                                <TableRow>
                                                                    <TableCell align={"right"}>Participation</TableCell>
                                                                    <TableCell>
                                                                        <ul>
                                                                            <li>top 2 teams from each 1st stage's groups</li>
                                                                        </ul>
                                                                    </TableCell>
                                                                </TableRow>
                                                                <TableRow>
                                                                    <TableCell align={"right"}>Format</TableCell>
                                                                    <TableCell>
                                                                        <ul>
                                                                            <li>2 groups, round robin</li>
                                                                            <li>same games excluded</li>
                                                                            <li>1st group stage points are carried over</li>
                                                                        </ul>
                                                                    </TableCell>
                                                                </TableRow>
                                                                <TableRow>
                                                                    <TableCell align={"right"}>Winners</TableCell>
                                                                    <TableCell>
                                                                        <ul>
                                                                            <li>1st team promotes to ½ finals</li>
                                                                            <li>2nd and 3rd teams promote to ¼ finals</li>
                                                                        </ul>
                                                                    </TableCell>
                                                                </TableRow>
                                                                <TableRow>
                                                                    <TableCell align={"right"}>Order rules</TableCell>
                                                                    <TableCell>
                                                                        <ol>
                                                                            <li>most points</li>
                                                                            <li>best goal difference</li>
                                                                            <li>most goals scored</li>
                                                                            <li>most wins</li>
                                                                            <li>alphabetical?</li>
                                                                        </ol>
                                                                    </TableCell>
                                                                </TableRow>
                                                                <TableRow>
                                                                    <TableCell align={"right"}>Coefficients granted</TableCell>
                                                                    <TableCell>
                                                                        <ul>
                                                                            <li>1st place: 2000</li>
                                                                            <li>2nd place: 600</li>
                                                                            <li>23rd place: 300</li>
                                                                            <li>win: 1000</li>
                                                                            <li>draw: 500</li>
                                                                            <li>each goal scored: 100</li>
                                                                        </ul>
                                                                    </TableCell>
                                                                </TableRow>
                                                            </TableBody>
                                                        </table>
                                                    </CardContent>
                                                </Card>
                                            </Grid>
                                        )}
                </Box>
            ) : (
                <span></span>
            )
        );
    }
}


export default Groups2;
