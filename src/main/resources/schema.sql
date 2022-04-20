CREATE TABLE GogoLookConfig (
    configKey VARCHAR(32) NOT NULL,
    configValue VARCHAR(1024) NOT NULL,
    CONSTRAINT primary_key_gogo PRIMARY KEY (configKey)
);

CREATE TABLE ShareLink (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    hashKey VARCHAR(32) NOT NULL,
    routeUrl VARCHAR(1024) NOT NULL,
    title VARCHAR(128) NOT NULL,
    content VARCHAR(128) NOT NULL,
    imgUrl VARCHAR(512) NOT NULL,
    CONSTRAINT primary_key_link PRIMARY KEY (id)
);

CREATE INDEX idx_share_hashKey ON ShareLink(hashKey);

CREATE TABLE LiveMap (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    liveId VARCHAR(128) NOT NULL,
    title VARCHAR(256) NOT NULL,
    userId VARCHAR(128) NOT NULL,
    CONSTRAINT primary_key_live PRIMARY KEY (liveId)
);

CREATE TABLE UserMap (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    userId VARCHAR(128) NOT NULL,
    avatarUrl VARCHAR(512) NOT NULL,
    backgroundUrl VARCHAR(512) NOT NULL,
    name VARCHAR(128) NOT NULL,
    CONSTRAINT primary_key_user PRIMARY KEY (userId)
);

CREATE TABLE MemberLog (
    member_uuid VARCHAR(100) NOT NULL,
    ip_address VARCHAR(128) NOT NULL,
    ip_country VARCHAR(100) NOT NULL,
    user_agent VARCHAR(200),
    device VARCHAR(32),
    os VARCHAR(32) NOT NULL,
    last_time TIMESTAMP,
    member_device_id VARCHAR(36) NOT NULL,
    CONSTRAINT primary_key_member PRIMARY KEY (member_uuid, member_device_id)
);