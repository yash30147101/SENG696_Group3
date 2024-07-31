import os
import sys
import pandas as pd

def count_records(SRC_ID, base_dir):
    file_location = os.path.join(base_dir, SRC_ID, "Windows_Server")
    files = os.listdir(file_location)

    for file in files:
        filename_without_extension = os.path.splitext(file)[0]
        date = filename_without_extension[-8:]
        output_filename = f"Windows_Server_LVS_{SRC_ID}_{date}.psv"

        file_path = os.path.join(file_location, file)

        df = pd.DataFrame([])

        # Check if the file is CSV or Excel
        if file.endswith('.csv'):
            try:
                df = pd.read_csv(file_path, encoding='utf-8')
            except UnicodeDecodeError:
                df = pd.read_csv(file_path, encoding='ISO-8859-1')
        elif file.endswith('.xls'):
            df = pd.read_excel(file_path, sheet_name=0)

        # Drop empty rows
        df.dropna(how='all', inplace=True)

        # Get the count of records
        count = df.shape[0]

        # Write the count and file name to the output file
        with open(os.path.join(file_location, output_filename), 'w') as f:
            f.write(str(count) + '\n')
            f.write(file)

if __name__ == "__main__":
    SRC_ID = sys.argv[1]
    base_dir = sys.argv[2]
    count_records(SRC_ID, base_dir)
