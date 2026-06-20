"""
Program to use
ProcessPoolExecutor.
"""

from concurrent.futures import (
    ProcessPoolExecutor
)


NUMBER_LIST = [1, 2, 3, 4, 5]


def calculate_square(
        number: int
) -> int:
    """
    Return square of a number.
    """

    return (
            number * number
    )


def main() -> None:
    """
    Execute the program.
    """

    with ProcessPoolExecutor() as executor:

        results = executor.map(
            calculate_square,
            NUMBER_LIST
        )

        for result in results:
            print(result)


if __name__ == "__main__":
    main()