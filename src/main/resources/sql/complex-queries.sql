SELECT HC.NAME, AC.NAME,
CASE
    WHEN G.HOME_GOALS_FT > G.AWAY_GOALS_FT THEN "1"
    WHEN G.HOME_GOALS_FT < G.AWAY_GOALS_FT THEN "2"
    ELSE "X"
END AS RESULT
FROM GAME G
JOIN CLUB HC ON HC.CLUB_ID = G.HOME_CLUB_ID
JOIN CLUB AC ON AC.CLUB_ID = G.AWAY_CLUB_ID
WHERE G.SEASON_ID = '2019-2020' AND LEAGUE_ID = 'E0' AND
G.PLAY_DATE BETWEEN '2019-10-10' AND '2019-10-20';


SELECT 
ROUND(AVG(CASE
    WHEN G.HOME_GOALS_FT > G.AWAY_GOALS_FT THEN G.ODD_1
    WHEN G.HOME_GOALS_FT < G.AWAY_GOALS_FT THEN G.ODD_2
    ELSE G.ODD_X
END), 2) AS RESULT
FROM GAME G
JOIN CLUB HC ON HC.CLUB_ID = G.HOME_CLUB_ID
JOIN CLUB AC ON AC.CLUB_ID = G.AWAY_CLUB_ID
WHERE G.SEASON_ID = '2019-2020' AND LEAGUE_ID = 'T1' AND
/*G.PLAY_DATE BETWEEN '2019-10-10' AND '2019-10-20';*/
G.PLAY_DATE > '2019-07-01';


/*Negative surprise index*/
SELECT
	/*G.PLAY_DATE, HC.NAME, AC.NAME, G.HOME_GOALS_FT, G.AWAY_GOALS_FT, G.ODD_1, G.ODD_2, ABS(G.ODD_1 - G.ODD_2)*/
	COALESCE(SUM(ABS(G.ODD_1 - G.ODD_2)), 0)
FROM GAME G
JOIN CLUB HC ON HC.CLUB_ID = G.HOME_CLUB_ID
JOIN CLUB AC ON AC.CLUB_ID = G.AWAY_CLUB_ID
WHERE G.SEASON_ID = '2019-2020' AND LEAGUE_ID = 'E0'
AND G.PLAY_DATE > '2019-07-01' AND (
(G.ODD_1 > G.ODD_2 AND G.HOME_GOALS_FT > G.AWAY_GOALS_FT AND AC.CLUB_ID = 1271)
OR
(G.ODD_1 < G.ODD_2 AND G.HOME_GOALS_FT < G.AWAY_GOALS_FT AND HC.CLUB_ID = 1271)
);


/*Positive surprise index*/
SELECT /*G.PLAY_DATE, HC.NAME, AC.NAME, G.HOME_GOALS_FT, G.AWAY_GOALS_FT, G.ODD_1, G.ODD_2, ABS(G.ODD_1 - G.ODD_2)*/
COALESCE(SUM(ABS(G.ODD_1 - G.ODD_2)), 0)
FROM GAME G
JOIN CLUB HC ON HC.CLUB_ID = G.HOME_CLUB_ID
JOIN CLUB AC ON AC.CLUB_ID = G.AWAY_CLUB_ID
WHERE G.SEASON_ID = '2019-2020' AND LEAGUE_ID = 'E0'
AND G.PLAY_DATE > '2019-07-01' AND (
(G.ODD_1 > G.ODD_2 AND G.HOME_GOALS_FT > G.AWAY_GOALS_FT AND HC.CLUB_ID = 1271)
OR
(G.ODD_1 < G.ODD_2 AND G.HOME_GOALS_FT < G.AWAY_GOALS_FT AND AC.CLUB_ID = 1271)
);