"""
Program to calculate sum
using a thread.
"""

import threading


START_NUMBER: int = 1
END_NUMBER: int = 100


def calculate_sum() -> None:
    """
    Calculate sum from 1 to 100.
    """

    total_sum = sum(
        range(
            START_NUMBER,
            END_NUMBER + 1
        )
    )

    print(
        f"Sum: {total_sum}"
    )


def main() -> None:
    """
    Execute the program.
    """

    sum_thread = threading.Thread(
        target=calculate_sum
    )

    sum_thread.start()


if __name__ == "__main__":
    main()