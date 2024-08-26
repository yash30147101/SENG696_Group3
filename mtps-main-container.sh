sed 's/\\//g' input.csv > output.csv

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

# Function to keep only the latest date file and move the rest to backup
keep_latest_and_backup() {
    local dir=$1
    local pattern=$2
    local latest_file=$(ls -1 ${dir}/${pattern} | sort -t '_' -k4,4 -r | head -n 1)
    local all_files=$(ls -1 ${dir}/${pattern})
    
    for file in ${all_files}; do
        if [ "${file}" != "${latest_file}" ]; then
            mv "${file}" "${BACKUP_DIR}/"
        fi
    done
}

# Keep only the latest date files in the directories
keep_latest_and_backup "${UP20_DIR}" "UP20_LVS_${SRC_ID}_*.psv"
keep_latest_and_backup "${Y5M0_DIR}" "Y5M0_LVS_${SRC_ID}_*.psv"
keep_latest_and_backup "${Q500_DIR}" "Q500_LVS_${SRC_ID}_*.psv"
keep_latest_and_backup "${WINDOWS_SERVER_DIR}" "Windows_Server_LVS_${SRC_ID}_*.psv"

# Get today's date in YYYYMMDD format
today_date=$(date +%Y%m%d)

# Move the Comparison file to the backup directory with today's date appended
if [ -f "${COMMON_PATH}/${SRC_ID}/Comparison/${SRC_ID}_LVS_Count_Comparison.txt" ]; then
    mv "${COMMON_PATH}/${SRC_ID}/Comparison/${SRC_ID}_LVS_Count_Comparison.txt" "${BACKUP_DIR}/${SRC_ID}_LVS_Count_Comparison_${today_date}.txt"
fi
if [ -f "${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Success.txt" ]; then
    rm -f "${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Success.txt"
fi
if [ -f "${COMMON_PATH}/${SRC_ID}/Comparison/LVS.tar" ]; then
    rm -f "${COMMON_PATH}/${SRC_ID}/Comparison/LVS.tar"
fi

# Get the counts from the files
UP20_count=$(awk 'NR==2 {print $1}' ${UP20_DIR}/UP20_LVS_${SRC_ID}_*.psv)
Y5M0_count=$(awk 'NR==4 {print $2}' ${Y5M0_DIR}/Y5M0_LVS_${SRC_ID}_*.psv)
Q500_count=$(awk 'NR==1 {print $1}' ${Q500_DIR}/Q500_LVS_${SRC_ID}_*.psv)

# Check if the Windows Server file exists and get the count and filename if it does
if [ -f ${WINDOWS_SERVER_DIR}/Windows_Server_LVS_${SRC_ID}_*.psv ]; then
    WINDOWS_SERVER_count=$(awk 'NR==1 {print $1}' ${WINDOWS_SERVER_DIR}/Windows_Server_LVS_${SRC_ID}_*.psv)
    LVS_FileName=$(awk 'NR==2 {print $0}' ${WINDOWS_SERVER_DIR}/Windows_Server_LVS_${SRC_ID}_*.psv)
else
    WINDOWS_SERVER_count="NA"
    LVS_FileName="NA"
fi

# Get the business date from the file names
business_date=$(basename ${UP20_DIR}/UP20_LVS_${SRC_ID}_*.psv | cut -d'_' -f4 | cut -d'.' -f1)

# Check if all files have the same business date
if [ $(basename ${Y5M0_DIR}/Y5M0_LVS_${SRC_ID}_*.psv | cut -d'_' -f4 | cut -d'.' -f1) != ${business_date} ] || \
   [ $(basename ${Q500_DIR}/Q500_LVS_${SRC_ID}_*.psv | cut -d'_' -f4 | cut -d'.' -f1) != ${business_date} ]; then
    echo "Files do not have the same business date. Exiting with code 1."
    exit 1
fi

# Check for mismatches and create the Comparison file if needed
comparison_output=""
if [ ${Y5M0_count} -ne ${UP20_count} ] || [ ${Q500_count} -ne ${UP20_count} ] || ([ ${WINDOWS_SERVER_count} != "NA" ] && [ ${WINDOWS_SERVER_count} -ne ${UP20_count} ]); then
    comparison_output+="======================================================\n"
    comparison_output+="Business Date: ${business_date}\n"
    comparison_output+="======================================================\n"
    comparison_output+="\n"
    comparison_output+="LVS Source: ${SRC_ID}\n"
    comparison_output+="LVS filename: ${LVS_FileName}\n"
    comparison_output+="\n"
    comparison_output+="LVS File Count: ${WINDOWS_SERVER_count}\n"
    comparison_output+="Y5M0 Count: ${Y5M0_count}\n"
    comparison_output+="UP20 Count: ${UP20_count}\n"
    comparison_output+="Q500 Count: ${Q500_count}\n"
    comparison_output+="\n"

    if [ ${Y5M0_count} -ne ${UP20_count} ]; then
        comparison_output+="***Record Count Mismatch: Y5M0 count is ${Y5M0_count} & UP20 count is ${UP20_count}\n"
    fi
    if [ ${Q500_count} -ne ${UP20_count} ]; then
        comparison_output+="***Record Count Mismatch: Q500 count is ${Q500_count} & UP20 count is ${UP20_count}\n"
    fi
    if [ ${WINDOWS_SERVER_count} != "NA" ] && [ ${WINDOWS_SERVER_count} -ne ${UP20_count} ]; then
        comparison_output+="***Record Count Mismatch: LVS File count is ${WINDOWS_SERVER_count} & UP20 count is ${UP20_count}\n"
    fi
    comparison_output+="======================================================\n"
    echo -e "${comparison_output}" > ${COMMON_PATH}/${SRC_ID}/Comparison/${SRC_ID}_LVS_Count_Comparison.txt
else
    echo "No count mismatch found among UP20, Q500, Y5M0, and LVS File Source" > ${COMMON_PATH}/${SRC_ID}/Comparison/LVS_Success.txt
fi

# If all goes well, move all the files to the backup directory
mv ${UP20_DIR}/* ${BACKUP_DIR}/
mv ${Y5M0_DIR}/* ${BACKUP_DIR}/
mv ${Q500_DIR}/* ${BACKUP_DIR}/
if [ -f ${WINDOWS_SERVER_DIR}/Windows_Server_LVS_${SRC_ID}_*.psv ]; then
    mv ${WINDOWS_SERVER_DIR}/* ${BACKUP_DIR}/
fi

# Exit with code 0
exit 0
