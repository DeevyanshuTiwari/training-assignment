"""
Program to simulate
file downloading using threads.
"""

import threading
import time


DOWNLOAD_TIME: int = 2


def download_file(
        file_name: str
) -> None:
    """
    Simulate file download.
    """

    print(
        f"Downloading {file_name}..."
    )

    time.sleep(
        DOWNLOAD_TIME
    )

    print(
        f"{file_name} Download Complete"
    )


def main() -> None:
    """
    Execute the program.
    """

    first_thread = threading.Thread(
        target=download_file,
        args=("File1.pdf",)
    )

    second_thread = threading.Thread(
        target=download_file,
        args=("File2.pdf",)
    )

    third_thread = threading.Thread(
        target=download_file,
        args=("File3.pdf",)
    )

    first_thread.start()
    second_thread.start()
    third_thread.start()

    first_thread.join()
    second_thread.join()
    third_thread.join()

    print(
        "All Downloads Completed"
    )


if __name__ == "__main__":
    main()