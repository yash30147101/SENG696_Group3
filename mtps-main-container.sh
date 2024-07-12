#!/bin/bash

# The name of the Python script
PYTHON_SCRIPT="fetch_LVS_Windows_Server_counts.py"

# The arguments to the Python script
SRC_ID="your_source_id"
BASE_DIR="your_base_directory"

# Run the Python script with Python 3
python3 $PYTHON_SCRIPT $SRC_ID $BASE_DIR
