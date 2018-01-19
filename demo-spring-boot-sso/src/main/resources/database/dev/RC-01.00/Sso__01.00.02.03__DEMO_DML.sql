-- PRMSN

INSERT INTO ENT_AUDT(ENT_AUDT_ID,CRT_TS,CRT_USER_EMAIL,LAST_MODF_TS,LAST_MODF_USER_EMAIL) VALUES (8,now(),'sysadmin',null,null);
INSERT INTO PRMSN (PRMSN_ID, PRMSN_NM, PRMSN_DN, LCK_VERS_NR, ENT_AUDT_ID) VALUES 
    (1, 'CAC_VIEW_DEMO_PAGE', 'Allows users to view the demo page', 0, 8);

INSERT INTO ENT_AUDT(ENT_AUDT_ID,CRT_TS,CRT_USER_EMAIL,LAST_MODF_TS,LAST_MODF_USER_EMAIL) VALUES (9,now(),'sysadmin',null,null);
INSERT INTO PRMSN (PRMSN_ID, PRMSN_NM, PRMSN_DN, LCK_VERS_NR, ENT_AUDT_ID) VALUES 
    (2, 'CAC_EDIT_DEMO_PAGE', 'Allows users to perform changes on the demo page', 0, 9);

-- USER_ROLE_PRMSN
	
INSERT INTO USER_ROLE_PRMSN VALUES
	-- ADMIN
	((SELECT USER_ROLE_ID FROM USER_ROLE WHERE USER_ROLE_NM = 'ADMIN'),          (SELECT PRMSN_ID FROM PRMSN WHERE PRMSN_NM = 'CAC_VIEW_DEMO_PAGE')),
	((SELECT USER_ROLE_ID FROM USER_ROLE WHERE USER_ROLE_NM = 'ADMIN'),          (SELECT PRMSN_ID FROM PRMSN WHERE PRMSN_NM = 'CAC_EDIT_DEMO_PAGE'));
