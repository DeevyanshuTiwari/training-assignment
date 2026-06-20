"""
Program to demonstrate pdb
inside a loop.
"""

import pdb


NUMBER_LIST = [10, 20, 30, 40, 50]


def process_numbers() -> None:
    """
    Process numbers and inspect
    variable values using pdb.
    """

    total_sum = 0

    for number in NUMBER_LIST:

        pdb.set_trace()

        total_sum += number

    print(
        f"Total Sum: {total_sum}"
    )


def main() -> None:
    """
    Execute the program.
    """

    process_numbers()


if __name__ == "__main__":
    main()