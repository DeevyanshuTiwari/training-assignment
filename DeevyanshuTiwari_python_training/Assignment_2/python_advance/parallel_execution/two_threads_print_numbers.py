"""
Program to create two threads
that print numbers from 1 to 5.
"""

import threading


START_NUMBER: int = 1
END_NUMBER: int = 5


def print_numbers(
        thread_name: str
) -> None:
    """
    Print numbers from 1 to 5.
    """

    for number in range(
            START_NUMBER,
            END_NUMBER + 1
    ):
        print(
            f"{thread_name}: {number}"
        )


def main() -> None:
    """
    Execute the program.
    """

    first_thread = threading.Thread(
        target=print_numbers,
        args=("Thread-1",)
    )

    second_thread = threading.Thread(
        target=print_numbers,
        args=("Thread-2",)
    )

    first_thread.start()
    second_thread.start()


if __name__ == "__main__":
    main()