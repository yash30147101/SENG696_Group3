#!/bin/bash

# The SRC_ID is passed as a parameter at runtime
SRC_ID=$1
TITAN_DIR=@TITAN_DIR_UCD@ 
APP_HOME=@APP_HOME_UCD@/${TITAN_DIR}
COMMON_PATH="${APP_HOME}/data/recon/source/lvs"

# Define the directory paths
UP20_DIR="${COMMON_PATH}/${SRC_ID}/UP20"
Y5M0_DIR="${COMMON_PATH}/${SRC_ID}/Y5M0"
Q500_DIR="${COMMON_PATH}/${SRC_ID}/Q500"
WINDOWS_SERVER_DIR="${COMMON_PATH}/${SRC_ID}/Windows_Server"
BACKUP_DIR="${COMMON_PATH}/${SRC_ID}/backup"

# Get the counts from the files
UP20_count=$(awk 'NR==2 {print $1}' ${UP20_DIR}/UP20_LVS_${SRC_ID}_*.psv)
Y5M0_count=$(awk 'NR==4 {print $2}' ${Y5M0_DIR}/Y5M0_LVS_${SRC_ID}_*.psv)
Q500_count=$(awk 'NR==1 {print $1}' ${Q500_DIR}/Q500_LVS_${SRC_ID}_*.psv)
WINDOWS_SERVER_count=$(awk 'NR==1 {print $1}' ${WINDOWS_SERVER_DIR}/Windows_Server_LVS_${SRC_ID}_*.psv)

# Get the business date from the file names
business_date=$(basename ${UP20_DIR}/UP20_LVS_${SRC_ID}_*.psv | cut -d'_' -f4 | cut -d'.' -f1)

# Check if all files have the same business date
if [ $(basename ${Y5M0_DIR}/Y5M0_LVS_${SRC_ID}_*.psv | cut -d'_' -f4 | cut -d'.' -f1) != ${business_date} ] || \
   [ $(basename ${Q500_DIR}/Q500_LVS_${SRC_ID}_*.psv | cut -d'_' -f4 | cut -d'.' -f1) != ${business_date} ] || \
   [ $(basename ${WINDOWS_SERVER_DIR}/Windows_Server_LVS_${SRC_ID}_*.psv | cut -d'_' -f4 | cut -d'.' -f1) != ${business_date} ]; then
    echo "Files do not have the same business date. Exiting with code 1."
    exit 1
fi

# Write the output to the Comparison file
echo "======================================================" > ${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Count_Comparison.txt
echo "Business Date: ${business_date}" >> ${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Count_Comparison.txt
echo "======================================================" >> ${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Count_Comparison.txt
echo "" >> ${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Count_Comparison.txt
echo "LVS Source: ${SRC_ID}" >> ${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Count_Comparison.txt
echo "" >> ${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Count_Comparison.txt
echo "Y5M0 Count: ${Y5M0_count}" >> ${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Count_Comparison.txt
echo "UP20 Count: ${UP20_count}" >> ${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Count_Comparison.txt
echo "Q500 Count: ${Q500_count}" >> ${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Count_Comparison.txt
echo "Windows_Server Count: ${WINDOWS_SERVER_count}" >> ${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Count_Comparison.txt
echo "" >> ${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Count_Comparison.txt

# Check for mismatches and write alerts to the Comparison file
if [ ${Y5M0_count} -ne ${UP20_count} ]; then
    echo "***Alert Mismatch: Y5M0 count is ${Y5M0_count} & UP20 count is ${UP20_count}" >> ${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Count_Comparison.txt
fi
if [ ${UP20_count} -ne ${Q500_count} ]; then
    echo "***Alert Mismatch: UP20 count is ${UP20_count} & Q500 count is ${Q500_count}" >> ${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Count_Comparison.txt
fi
if [ ${UP20_count} -ne ${WINDOWS_SERVER_count} ]; then
    echo "***Alert Mismatch: UP20 count is ${UP20_count} & Windows_Server count is ${WINDOWS_SERVER_count}" >> ${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Count_Comparison.txt
fi
echo "======================================================" >> ${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Count_Comparison.txt
