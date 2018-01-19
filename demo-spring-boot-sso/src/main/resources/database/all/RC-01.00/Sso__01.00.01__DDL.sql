
CREATE TABLE ENT_AUDT(
    ENT_AUDT_ID           int           NOT NULL,
    CRT_TS                datetime      NOT NULL,
    CRT_USER_EMAIL        nvarchar(140) NOT NULL,
    LAST_MODF_TS          datetime      NULL,
    LAST_MODF_USER_EMAIL  nvarchar(140) NULL,
    CONSTRAINT PK2__ENT_AUDT PRIMARY KEY CLUSTERED (ENT_AUDT_ID)
);

CREATE TABLE USER_ACCT(
    USER_ACCT_ID    int              NOT NULL,
    EMP_NR          nvarchar(140)    NOT NULL,
    EMAIL_ADDR      nvarchar(140)    NOT NULL,
    ACCT_ENBL       bit              NOT NULL,
    ACCT_EXP        bit              NOT NULL,
    ACCT_LCK        bit              NOT NULL,
    LCK_VERS_NR     int              NOT NULL,
    ENT_AUDT_ID     int              NOT NULL,
    CONSTRAINT PK__USER_ACCT PRIMARY KEY CLUSTERED (USER_ACCT_ID),
    CONSTRAINT AK1__USER_ACCT UNIQUE (EMAIL_ADDR),
    CONSTRAINT AK2__USER_ACCT UNIQUE (EMP_NR) 
);

ALTER TABLE USER_ACCT ADD CONSTRAINT R1__USER_ACCT__ENT_AUDT 
    FOREIGN KEY (ENT_AUDT_ID)
    REFERENCES ENT_AUDT(ENT_AUDT_ID);

CREATE TABLE USER_ROLE(
    USER_ROLE_ID    int              NOT NULL,
    USER_ROLE_NM    nvarchar(60)     NOT NULL,
    USER_ROLE_DN    nvarchar(255)    NOT NULL,
    LCK_VERS_NR     int              NOT NULL,
    ENT_AUDT_ID     int              NOT NULL,
    CONSTRAINT PK__USER_ROLE PRIMARY KEY CLUSTERED (USER_ROLE_ID)
);

ALTER TABLE USER_ROLE ADD CONSTRAINT R1__USER_ROLE__ENT_AUDT 
    FOREIGN KEY (ENT_AUDT_ID)
    REFERENCES ENT_AUDT(ENT_AUDT_ID);
    
ALTER TABLE USER_ROLE ADD CONSTRAINT AK1_USER_ROLE_NM  UNIQUE (USER_ROLE_NM);

CREATE TABLE USER_GRP(
    USER_GRP_ID    int              NOT NULL,
    USER_GRP_NM    nvarchar(60)     NOT NULL,
    USER_GRP_DN    nvarchar(255)    NOT NULL,
    LCK_VERS_NR    int              NOT NULL,
    ENT_AUDT_ID    int              NOT NULL,
    CONSTRAINT PK__USER_GRP PRIMARY KEY CLUSTERED (USER_GRP_ID)
);

ALTER TABLE USER_GRP ADD CONSTRAINT R1__USER_GRP__ENT_AUDT 
    FOREIGN KEY (ENT_AUDT_ID)
    REFERENCES ENT_AUDT(ENT_AUDT_ID);

CREATE TABLE ACCT_USER_ROLE(
    USER_ROLE_ID    int    NOT NULL,
    USER_ACCT_ID    int    NOT NULL,
    CONSTRAINT PK__ACCT_USER_ROLE PRIMARY KEY CLUSTERED (USER_ROLE_ID, USER_ACCT_ID)
);

ALTER TABLE ACCT_USER_ROLE ADD CONSTRAINT R1__ACCT_USER_ROLE__USER_ACCT 
    FOREIGN KEY (USER_ACCT_ID)
    REFERENCES USER_ACCT(USER_ACCT_ID);

ALTER TABLE ACCT_USER_ROLE ADD CONSTRAINT R2__ACCT_USER_ROLE__USER_ROLE 
    FOREIGN KEY (USER_ROLE_ID)
    REFERENCES USER_ROLE(USER_ROLE_ID);

CREATE TABLE ACCT_USER_GRP(
    USER_GRP_ID     int    NOT NULL,
    USER_ACCT_ID    int    NOT NULL,
    CONSTRAINT PK__ACCT_USER_GRP PRIMARY KEY CLUSTERED (USER_GRP_ID, USER_ACCT_ID)
);

ALTER TABLE ACCT_USER_GRP ADD CONSTRAINT R1__ACCT_USER_GRP__USER_ACCT 
    FOREIGN KEY (USER_ACCT_ID)
    REFERENCES USER_ACCT(USER_ACCT_ID);

ALTER TABLE ACCT_USER_GRP ADD CONSTRAINT R2__ACCT_USER_GRP__USER_GRP 
    FOREIGN KEY (USER_GRP_ID)
    REFERENCES USER_GRP(USER_GRP_ID);

CREATE TABLE USER_LOGN_H(
    USER_LOGN_ID    int             NOT NULL,
    USER_ACCT_ID    int             NOT NULL,
    LOGN_TS         datetime        NOT NULL,
    LOGN_STAT_CD    nvarchar(10)    NOT NULL,
    LOGOUT_TS       datetime        NULL,
    CONSTRAINT PK__USER_LOGN_H PRIMARY KEY CLUSTERED (USER_LOGN_ID)
);

ALTER TABLE USER_LOGN_H ADD CONSTRAINT R1__USER_LOGN_H__USER_ACCT 
    FOREIGN KEY (USER_ACCT_ID)
    REFERENCES USER_ACCT(USER_ACCT_ID);

CREATE TABLE PRMSN(
    PRMSN_ID     int              NOT NULL,
    PRMSN_NM     nvarchar(60)     NOT NULL,
    PRMSN_DN     nvarchar(255)    NOT NULL,
    LCK_VERS_NR  int              NOT NULL,
    ENT_AUDT_ID  int              NOT NULL,
    CONSTRAINT PK__PRMSN PRIMARY KEY CLUSTERED (PRMSN_ID)
);

ALTER TABLE PRMSN ADD CONSTRAINT R1__PRMSN__ENT_AUDT 
    FOREIGN KEY (ENT_AUDT_ID)
    REFERENCES ENT_AUDT(ENT_AUDT_ID);

ALTER TABLE PRMSN ADD CONSTRAINT AK1_PRMSN_NM  UNIQUE (PRMSN_NM);

CREATE TABLE USER_ROLE_PRMSN(
    USER_ROLE_ID    int    NOT NULL,
    PRMSN_ID        int    NOT NULL,
    CONSTRAINT PK__USER_ROLE_PRMSN PRIMARY KEY CLUSTERED (USER_ROLE_ID, PRMSN_ID)
);

ALTER TABLE USER_ROLE_PRMSN ADD CONSTRAINT R1__USER_ROLE_PRMSN__USER_ROLE 
    FOREIGN KEY (USER_ROLE_ID)
    REFERENCES USER_ROLE(USER_ROLE_ID);

ALTER TABLE USER_ROLE_PRMSN ADD CONSTRAINT R2__USER_ROLE_PRMSN__PRMSN 
    FOREIGN KEY (PRMSN_ID)
    REFERENCES PRMSN(PRMSN_ID);
