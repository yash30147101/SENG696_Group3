#!/bin/bash

# Check if the first argument starts with "LVS_" or "lvs_"
if [[ $1 == LVS_* || $1 == lvs_* ]]; then
  # Convert the argument to uppercase
  UPPERCASE_ARG=$(echo $1 | tr '[:lower:]' '[:upper:]')
  
  # Define the source and destination directories
  SOURCE_DIR="/dq5/recon/dm/${UPPERCASE_ARG}/target/"
  DEST_DIR="/dq5/recon/dm/${UPPERCASE_ARG}/backup/"
  
  # Move all files from the source to the destination directory
  mv "${SOURCE_DIR}"* "${DEST_DIR}"
  
  echo "Files moved from ${SOURCE_DIR} to ${DEST_DIR}"
else
  echo "The argument does not start with 'LVS_' or 'lvs_'"
fi
