#!/bin/bash

# Define the file path
FILE="Folder_Path/FileName"

# Check if the file exists
if [ -f "$FILE" ]; then
    # If the file exists, delete it
    rm "$FILE"
