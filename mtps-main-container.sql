SELECT COUNT(*) AS counts 
FROM @UP20_HIVE_SCHEMA@.party_entity 
WHERE partysystemsourceid = 10004 
AND capturedate = (
    SELECT MAX(capturedate) 
    FROM @UP20_HIVE_SCHEMA@.party_entity 
    WHERE partysystemsourceid = 10004
);


SELECT ISNULL(SUM(REC_CNT), 0) AS COUNTS 
FROM SRC_FILE 
WHERE SYS_SRC_ID = ${SRC_ID} 
AND CONVERT(date, LD_TMSTMP) = (
    SELECT CONVERT(date, MAX(LD_TMSTMP)) 
    FROM SRC_FILE 
    WHERE SYS_SRC_ID = ${SRC_ID}
);
