"""
Program to extract even numbers
using filter().
"""


NUMBER_LIST = [1, 2, 3, 4, 5, 6, 7, 8]


def main() -> None:
    """
    Execute the program.
    """

    even_numbers = list(
        filter(
            lambda number: number % 2 == 0,
            NUMBER_LIST
        )
    )

    print(
        even_numbers
    )


if __name__ == "__main__":
    main()