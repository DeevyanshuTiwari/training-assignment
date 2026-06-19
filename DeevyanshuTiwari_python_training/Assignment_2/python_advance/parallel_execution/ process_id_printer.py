"""
Program to create two processes
and print their Process IDs.
"""

import multiprocessing
import os


def print_process_id() -> None:
    """
    Print current process ID.
    """

    print(
        f"Process ID: {os.getpid()}"
    )


def main() -> None:
    """
    Execute the program.
    """

    first_process = multiprocessing.Process(
        target=print_process_id
    )

    second_process = multiprocessing.Process(
        target=print_process_id
    )

    first_process.start()
    second_process.start()

    first_process.join()
    second_process.join()


if __name__ == "__main__":
    main()