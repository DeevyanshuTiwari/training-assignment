"""
Program to generate even numbers
from 1 to 50 using a generator expression.
"""


MAXIMUM_NUMBER: int = 50


def generate_even_numbers() -> None:
    """
    Generate and display even numbers
    using a generator expression.
    """

    even_number_generator = (
        number
        for number in range(
        1,
        MAXIMUM_NUMBER + 1
    )
        if number % 2 == 0
    )

    for even_number in even_number_generator:
        print(even_number)


def main() -> None:
    """
    Execute the program.
    """

    generate_even_numbers()


if __name__ == "__main__":
    main()