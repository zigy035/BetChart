INSERT INTO COUNTRY (COUNTRY_ID, NAME) VALUES
('EN', 'England'),
('DE', 'Germany'),
('FR', 'France'),
('IT', 'Italy'),
('ES', 'Spain'),
('NL', 'Netherlands'),
('BE', 'Belgium'),
('GR', 'Greece'),
('PO', 'Portugal'),
('TU', 'Turkey');

INSERT INTO SEASON (SEASON_ID, NAME, START_YEAR, END_YEAR, CSV_URL) VALUES
('2017-2018', 'Season 2017/2018', 2017, 2018, '/mmz4281/1718'),
('2018-2019', 'Season 2018/2019', 2018, 2019, '/mmz4281/1819'),
('2019-2020', 'Season 2019/2020', 2019, 2020, '/mmz4281/1920');

INSERT INTO LEAGUE (LEAGUE_ID, COUNTRY_ID, NAME, URL) VALUES
('E0', 'EN', 'Premier League', '/england/premier-league'),
('E1', 'EN', 'Championship', '/england/championship'),
('D1', 'DE', 'Bundesliga', '/germany/bundesliga'),
('D2', 'DE', 'Bundesliga 2', '/germany/2-bundesliga'),
('F1', 'FR', 'Ligue 1', '/france/ligue-1'),
('F2', 'FR', 'Ligue 2', '/france/ligue-2'),
('I1', 'IT', 'Serie A', '/italy/serie-a'),
('I2', 'IT', 'Serie B', '/italy/serie-b'),
('SP1', 'ES', 'LaLiga', '/spain/laliga'),
('SP2', 'ES', 'LaLiga2', '/spain/laliga2'),
('N1', 'NL', 'Eredivisie', '/netherlands/eredivisie'),
('B1', 'BE', 'Jupiler League', '/belgium/jupiler-league'),
('G1', 'GR', 'Super League', '/greece/super-league'),
('P1', 'PO', 'Primeira Liga', '/portugal/primeira-liga'),
('T1', 'TU', 'Super Lig', '/turkey/super-lig');

INSERT INTO SEASON_LEAGUE_CSV (SEASON_ID, LEAGUE_ID, CSV_URL) VALUES
('2019-2020', 'E0', '/mmz4281/1920/E0.csv'),
('2019-2020', 'E1', '/mmz4281/1920/E1.csv'),
('2019-2020', 'D1', '/mmz4281/1920/D1.csv'),
('2019-2020', 'D2', '/mmz4281/1920/D2.csv'),
('2019-2020', 'F1', '/mmz4281/1920/F1.csv'),
('2019-2020', 'F2', '/mmz4281/1920/F2.csv'),
('2019-2020', 'I1', '/mmz4281/1920/I1.csv'),
('2019-2020', 'I2', '/mmz4281/1920/I2.csv'),
('2019-2020', 'SP1', '/mmz4281/1920/SP1.csv'),
('2019-2020', 'SP2', '/mmz4281/1920/SP2.csv'),
('2019-2020', 'N1', '/mmz4281/1920/N1.csv'),
('2019-2020', 'B1', '/mmz4281/1920/B1.csv'),
('2019-2020', 'G1', '/mmz4281/1920/G1.csv'),
('2019-2020', 'P1', '/mmz4281/1920/P1.csv'),
('2019-2020', 'T1', '/mmz4281/1920/T1.csv');
