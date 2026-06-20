"""
Program to convert numbers
into squares using map().
"""


NUMBER_LIST = [1, 2, 3, 4, 5]


def main() -> None:
    """
    Execute the program.
    """

    squared_numbers = list(
        map(
            lambda number: number * number,
            NUMBER_LIST
        )
    )

    print(
        squared_numbers
    )


if __name__ == "__main__":
    main()