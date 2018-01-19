
-- ### TEST DATA ###

INSERT INTO APP(APP_ID,APP_NM,APP_DN,APP_URL) VALUES 
    (5,'Demo Test','Demo Test App','https://demo.net/test/');


INSERT INTO APP_USER_ROLE (APP_ID,USER_ROLE_ID) VALUES
	(1,1), (1,2), (1,3), (1,4);

INSERT INTO APP_USER_GRP (APP_ID,USER_GRP_ID) VALUES
	(1,1), (1,2), (1,3),
	(2,1), (2,2),
	(3,1);