CREATE TABLE ShareLink (
    id INTEGER PRIMARY KEY,
    hashKey VARCHAR(32) NOT NULL,
    routeUrl VARCHAR(1024) NOT NULL,
    title VARCHAR(128) NOT NULL,
    content VARCHAR(128) NOT NULL,
    imgUrl VARCHAR(512) NOT NULL
);

CREATE INDEX idx_share_hashKey ON ShareLink(hashKey);