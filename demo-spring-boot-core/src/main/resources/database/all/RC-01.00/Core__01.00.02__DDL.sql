
CREATE TABLE APP(
    APP_ID     int              NOT NULL,
    APP_NM     nvarchar(60)     NOT NULL,
    APP_DN     nvarchar(255)    NULL,
    APP_URL    nvarchar(255)    NOT NULL,
    CONSTRAINT PK__APP PRIMARY KEY CLUSTERED (APP_ID)
);

CREATE TABLE APP_USER_GRP(
    APP_ID         int    NOT NULL,
    USER_GRP_ID    int    NOT NULL,
    CONSTRAINT PK__APP_USER_GRP PRIMARY KEY CLUSTERED (APP_ID, USER_GRP_ID)
);

CREATE TABLE APP_USER_ROLE(
    APP_ID          int    NOT NULL,
    USER_ROLE_ID    int    NOT NULL,
    CONSTRAINT PK__APP_USER_ROLE PRIMARY KEY CLUSTERED (APP_ID, USER_ROLE_ID)
);

CREATE TABLE ENT_AUDT(
    ENT_AUDT_ID          int         NOT NULL,
    CRT_TS               datetime    NOT NULL,
    CRT_USER_ID          int         NOT NULL,
    LAST_MODF_TS         datetime    NOT NULL,
    LAST_MODF_USER_ID    int         NOT NULL,
    CONSTRAINT PK28_1 PRIMARY KEY CLUSTERED (ENT_AUDT_ID)
);

CREATE TABLE GBL_CNFG(
    GBL_CNFG_ID       int              NOT NULL,
    GBL_CNFG_CNTXT    nvarchar(60)     NOT NULL,
    GBL_CNFG_NM       nvarchar(60)     NOT NULL,
    GBL_CNFG_VALU     varchar(4000)    NOT NULL,
    CONSTRAINT PK26 PRIMARY KEY CLUSTERED (GBL_CNFG_ID)
);

ALTER TABLE APP_USER_GRP ADD CONSTRAINT R1__APP_USER_GRP__APP 
    FOREIGN KEY (APP_ID)
    REFERENCES core.APP(APP_ID);

ALTER TABLE APP_USER_ROLE ADD CONSTRAINT R1__APP_USER_ROLE__APP 
    FOREIGN KEY (APP_ID)
    REFERENCES core.APP(APP_ID);