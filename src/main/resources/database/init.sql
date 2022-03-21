DROP FUNCTION IF EXISTS IncrementCallsAndGetUrl;
DROP TABLE IF EXISTS tz_links;
DROP TABLE IF EXISTS tz_sites;

CREATE TABLE tz_sites (
    id SERIAL PRIMARY KEY,
    login VARCHAR(40) NOT NULL UNIQUE,
    password VARCHAR(40) NOT NULL,
    enabled BOOLEAN DEFAULT true
);

CREATE TABLE tz_links (
    id BIGSERIAL PRIMARY KEY,
    id_site INTEGER NOT NULL,
    code VARCHAR(20) UNIQUE,
    link TEXT NOT NULL UNIQUE,
    calls BIGINT NOT NULL DEFAULT 0
);

CREATE OR REPLACE FUNCTION IncrementCallsAndGetUrl (
    shortcode VARCHAR(20)
) RETURNS tz_links AS $url$
    DECLARE urlId BIGINT;
    DECLARE rec tz_links;
BEGIN
    SELECT id INTO urlId FROM tz_links WHERE code = $1;
    UPDATE tz_links SET calls = calls + 1 WHERE id = urlId;
    SELECT * INTO rec FROM tz_links WHERE id = urlId;
    RETURN rec;
END; $url$ LANGUAGE plpgsql;