# HIBP Downloader

This Java-based application fetches hash ranges from the "Have I Been Pwned" API and saves them as text files. Each range corresponds to a specific prefix of SHA-1 hashes, allowing users to download and analyze pwned password data locally.

## Features

- **Parallel Downloads**: Utilizes multi-threading to download hash ranges concurrently for faster execution.
- **Organized Storage**: Downloads are saved in a dedicated `hashedPasswords` directory.
- **Logging**: Displays logs in the terminal, indicating which hash ranges have been successfully downloaded.

## Prerequisites

- **Java 11** or higher
- **Maven** for dependency management and project setup

## Setup and Installation

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/ptechofficial/hibp-java-downloader.git
    cd hibp-downloader
    ```

2. **Build the Project**:
    ```bash
    mvn clean install
    ```

3. **Run the Application**:
    ```bash
    java -cp target/hibp-downloader-1.0-SNAPSHOT.jar com.hibpdownloader.HibpDownloader
    ```

## Usage

The application will automatically start downloading hash ranges from the HIBP API when executed. The files are saved in the `hashedPasswords` directory, and progress is logged in the terminal.

## Code Structure

- `HibpDownloader.java`: Main class containing the logic for downloading and saving hash ranges.
- `getHashRange(int i)`: Converts an integer to the corresponding SHA-1 hash prefix.
- `fetchAndSaveHashRange(int hashRange)`: Fetches a specific hash range from the API and saves it to a file.

## Customization

- **Parallelism**: Modify the `MAX_PARALLELISM` constant to control the number of threads used for downloading.
- **Output Directory**: Change the `hashedPasswords` directory name in the code if you prefer a different location.

## Contributing

Feel free to fork the repository, make improvements, and submit pull requests. Contributions are welcome!

## License

This project is licensed under the MIT License.
