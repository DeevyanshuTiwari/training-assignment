"""
Program to find product of all
elements using reduce().
"""

from functools import reduce


NUMBER_LIST = [1, 2, 3, 4, 5]


def main() -> None:
    """
    Execute the program.
    """

    product = reduce(
        lambda first_number,
               second_number:
        first_number * second_number,
        NUMBER_LIST
    )

    print(
        f"Product: {product}"
    )


if __name__ == "__main__":
    main()