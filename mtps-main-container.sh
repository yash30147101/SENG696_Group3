#!/bin/bash

# Check if the App_Id starts with "LVS_"
if [[ ${App_Id} == LVS_* ]]; then
  # Extract the last 5 characters of App_Id
  LAST_FIVE_CHARS=${App_Id: -5}
  
  # Define the directory to check
  CHECK_DIR="${APP_HOME}/data/recon/source/lvs/${LAST_FIVE_CHARS}/Q500/"
  
  # Check if there are no .psv files in the directory
  if ! ls "${CHECK_DIR}"*.psv 1> /dev/null 2>&1; then
    echo "No .psv files found in ${CHECK_DIR}. Exiting script."
    exit 0
  fi
fi
