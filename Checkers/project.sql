drop table user;

CREATE TABLE user (
	username	varchar(25),
	password	varbinary(25),
	no_wins		int(10));


ALTER TABLE user
	ADD CONSTRAINT user_username_pk	PRIMARY KEY(username);
