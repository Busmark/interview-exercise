CREATE TABLE exercise(
  id INT PRIMARY KEY,
  tournament_id INT,
  reward_amount INT,
  player_id INT,
  player_name VARCHAR
);

CREATE TABLE tournament(
    id INT NOT NULL AUTO_INCREMENT,
    tournament_id INT NOT NULL PRIMARY KEY,
    reward_amount INT NOT NULL
);

CREATE TABLE player(
    pid INT NOT NULL AUTO_INCREMENT,
    player_id INT NOT NULL PRIMARY KEY,
    player_name VARCHAR NOT NULL,
    fk_id INT NOT NULL,
    CONSTRAINT fk_id
        foreign key (fk_id) references tournament(tournament_id)
        ON DELETE CASCADE
);




