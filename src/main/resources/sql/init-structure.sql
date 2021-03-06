CREATE TABLE COUNTRY (
	COUNTRY_ID VARCHAR(2) NOT NULL,
	NAME VARCHAR(50) NOT NULL,
	PRIMARY KEY (COUNTRY_ID)
) ENGINE=InnoDB;

CREATE TABLE SEASON (
	SEASON_ID VARCHAR(10) NOT NULL,
	NAME VARCHAR(50) NOT NULL,
	START_YEAR INTEGER NOT NULL,
	END_YEAR INTEGER NOT NULL,
	CSV_URL VARCHAR(50) NULL,
	PRIMARY KEY (SEASON_ID)
) ENGINE=InnoDB;

CREATE TABLE LEAGUE (
	LEAGUE_ID VARCHAR(3) NOT NULL,
	COUNTRY_ID VARCHAR(2) NOT NULL,
	NAME VARCHAR(50) NOT NULL,
	URL VARCHAR(50) NOT NULL,
	PRIMARY KEY (LEAGUE_ID),
	FOREIGN KEY (COUNTRY_ID) REFERENCES COUNTRY (COUNTRY_ID)
) ENGINE=InnoDB;

CREATE TABLE CLUB (
	CLUB_ID INTEGER NOT NULL AUTO_INCREMENT,
	COUNTRY_ID VARCHAR(2) NOT NULL,
	NAME VARCHAR(50) NOT NULL,
	URL VARCHAR(50) NULL,
	PRIMARY KEY (CLUB_ID),
	FOREIGN KEY (COUNTRY_ID) REFERENCES COUNTRY (COUNTRY_ID),
	CONSTRAINT UQ_CLUB_NAME UNIQUE (NAME)
) ENGINE=InnoDB;

CREATE TABLE GAME (
	GAME_ID INTEGER NOT NULL AUTO_INCREMENT,
	SEASON_ID VARCHAR(10) NOT NULL,
	LEAGUE_ID VARCHAR(3) NOT NULL,
	HOME_CLUB_ID INTEGER NOT NULL,
	AWAY_CLUB_ID INTEGER NOT NULL,
	PLAY_DATE TIMESTAMP NOT NULL,

	HOME_GOALS_FT INTEGER NULL,
	AWAY_GOALS_FT INTEGER NULL,
	HOME_GOALS_HT INTEGER NULL,
    AWAY_GOALS_HT INTEGER NULL,

    HOME_SHOTS INTEGER NULL,
    AWAY_SHOTS INTEGER NULL,
    HOME_SHOTS_OT INTEGER NULL,
    AWAY_SHOTS_OT INTEGER NULL,
    HOME_FOULS INTEGER NULL,
    AWAY_FOULS INTEGER NULL,

    ODD_1 DECIMAL(5,2) NULL,
    ODD_X DECIMAL(5,2) NULL,
    ODD_2 DECIMAL(5,2) NULL,

    IS_AWARDED TINYINT NOT NULL,

	PRIMARY KEY (GAME_ID),
	FOREIGN KEY (SEASON_ID) REFERENCES SEASON (SEASON_ID),
	FOREIGN KEY (LEAGUE_ID) REFERENCES LEAGUE (LEAGUE_ID),
	FOREIGN KEY (HOME_CLUB_ID) REFERENCES CLUB (CLUB_ID),
	FOREIGN KEY (AWAY_CLUB_ID) REFERENCES CLUB (CLUB_ID),
	CONSTRAINT UQ_SEASON_HOME_AWAY_CLUB UNIQUE (SEASON_ID, LEAGUE_ID, HOME_CLUB_ID, AWAY_CLUB_ID)
) ENGINE=InnoDB;