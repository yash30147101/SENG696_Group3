LocalDate comparisonDate = LocalDate.parse("2001-11-09");

hdfs dfs -D fs.s3a.endpoint=$S3_ENDPOINT -D fs.s3a.access.key=$S3_ACCESS_KEY -D fs.s3a.secret.key=$S3_SECRET_KEY -ls $LOCAL_PATH/* | while read file; do hdfs dfs -D fs.s3a.endpoint=$S3_ENDPOINT -D fs.s3a.access.key=$S3_ACCESS_KEY -D fs.s3a.secret.key=$S3_SECRET_KEY -put "$file" s3a://$S3_BUCKET/Fintrac_Data_Files/STR/$ENV/landing/; done
