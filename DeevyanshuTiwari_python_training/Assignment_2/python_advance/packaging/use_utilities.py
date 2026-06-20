"""
Program to use utility functions.
"""

from utility_functions import (
    calculate_square,
    calculate_cube
)


def main() -> None:
    """
    Execute the program.
    """

    print(
        calculate_square(5)
    )

    print(
        calculate_cube(5)
    )


if __name__ == "__main__":
    main()