import { React } from 'react';
import {Link} from 'react-router-dom';


export const MatchDetailCard = ({teamName, match}) => {
    if(!match) return null;
    const otherTeam = match.team1===teamName ? match.team2 : match.team1;
    const otherTeamRoute = "/teams/"+otherTeam;
  return (
    <div className="MatchDetailCard">
      <h3> Latest Matches </h3>
      <h1> vs  <Link to={otherTeamRoute}>{otherTeam}</Link> </h1>
      <h2> {match.date} </h2>
      <h3> at {match.venue} </h3>
      <h4> Winner - {match.winner} </h4>
    </div>
  );
}

