import matplotlib.pyplot as plt
import pandas as pd

if __name__ == '__main__':

    filename = "TeamC_G03"

    # Assuming your CSV file is named 'your_file.csv'
    df = pd.read_csv(filename + '.csv', delimiter=',')  # Adjust delimiter if needed

    # Extracting relevant columns
    run = df['Run']
    time_ms = df['Time(ms)']
    energy_J = df['Energy(J)'].str.extract(r'\((\d+\.\d+)J\)').astype(float)

    # Plotting
    plt.figure(figsize=(10, 6))
    plt.plot(run, time_ms, label='Time(ms)', marker='o')
    plt.plot(run, energy_J, label='Energy(J)', marker='o')

    # Adding labels and title
    plt.xlabel('Run')
    plt.ylabel('Value')
    plt.title('Time and Energy per Run')
    plt.legend()

    # Show the plot
    plt.savefig(filename + '.png')
    plt.show()

