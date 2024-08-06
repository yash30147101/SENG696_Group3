#!/bin/bash

# Define the source directory
SRCDIR="${APP_HOME}/data/recon/$FROM/$SRC/$SRCID/$TYPE"

# Get the current year and month
YYYYMM=$(date +%Y%m)

# Rename PensionHistoryReport files
for file in "$SRCDIR"/PensionHistoryReport*.csv; do
  if [[ -f "$file" ]]; then
    mv "$file" "${SRCDIR}/PensionHistoryReport${YYYYMM}01.csv"
  fi
done

# Rename RecipientHistoryReport files
for file in "$SRCDIR"/RecipientHistoryReport*.csv; do
  if [[ -f "$file" ]]; then
    mv "$file" "${SRCDIR}/RecipientHistoryReport${YYYYMM}01.csv"
  fi
done
