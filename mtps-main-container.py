import os
import sys
import pandas as pd

def count_records(SRC_ID, file_location):
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

        # Check if the file is CSV or Excel
        if file.endswith('.csv'):
            df = pd.read_csv(file_path)
        elif file.endswith('.xlsx'):
            df = pd.read_excel(file_path)

        # Drop empty rows
        df.dropna(how='all', inplace=True)

        # Get the count of records
        count = df.shape[0]

        # Write the count to the output file
        with open(os.path.join(file_location, output_filename), 'w') as f:
            f.write(str(count))

        # Check if the file is CSV
        if file.endswith('.csv'):
            try:
                df = pd.read_csv(file_path, encoding='utf-8')
            except UnicodeDecodeError:
                df = pd.read_csv(file_path, encoding='ISO-8859-1')  # Use a different encoding

if __name__ == "__main__":
    SRC_ID = sys.argv[1]
    file_location = "/app/up20/dev-titan/data/recon/source/lvs/{}/Windows_Server/".format(SRC_ID)
    count_records(SRC_ID, file_location)
