import { React, useEffect, useState } from 'react';
import {useParams} from 'react-router-dom';
import { MatchDetailCard } from '../components/MatchDetailCard';

export const MatchPage = () => {

 const[matches, setMatches] = useState([]);
 const {teamName, year} = useParams();
 useEffect(
  () => {
    const fetchMatches = async() => {
            const response = await fetch("http://localhost:8080/teams/"+teamName+"/matches?year="+year);
            const data = await response.json();
            setMatches(data);
    };
    fetchMatches();
  }, []
 );

      return (
        <div className="MatchPage">
          Match Page
          <h1> {teamName} </h1>
            {
            matches.map(match => <MatchDetailCard key={match.id} teamName={teamName} match={match}/>)
            }
        </div>
        );



}

