SELECT COUNT(*) AS counts 
FROM @UP20_HIVE_SCHEMA@.party_entity 
WHERE partysystemsourceid = 10004 
AND capturedate = (
    SELECT MAX(capturedate) 
    FROM @UP20_HIVE_SCHEMA@.party_entity 
    WHERE partysystemsourceid = 10004
);


