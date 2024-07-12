import os
import sys

def count_records(SRC_ID, base_dir):
    # Construct the file location using the base directory and the source ID
    file_location = os.path.join(base_dir, SRC_ID, "Windows_Server")

    # Get the list of files in the directory
    files = os.listdir(file_location)

    for file in files:
        # Get the filename without extension
        filename_without_extension = os.path.splitext(file)[0]

        # Extract the date from the filename
        date = filename_without_extension[-8:]

        # Construct the output filename
        output_filename = f"Windows_Server_LVS_{SRC_ID}_{date}.psv"

        # Full path to the file
        file_path = os.path.join(file_location, file)

        # Check if the file is CSV
        if file.endswith('.csv'):
            with open(file_path, 'r') as f:
                lines = f.readlines()
                # Count non-empty lines, ignoring the header
                count = sum(1 for line in lines[1:] if line.strip())

            # Write the count to the output file
            with open(os.path.join(file_location, output_filename), 'w') as f:
                f.write(str(count))

if __name__ == "__main__":
    SRC_ID = sys.argv[1]
    base_dir = sys.argv[2]  # Get the base directory from the command-line arguments
    count_records(SRC_ID, base_dir)
