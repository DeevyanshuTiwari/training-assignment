"""
Convert loop-based logic
into functional style.
"""


NUMBER_LIST = [
    1,
    2,
    3,
    4,
    5
]


def display_squared_numbers() -> None:
    """
    Display squared numbers
    using map().
    """

    squared_numbers = list(
        map(
            lambda number:
            number * number,
            NUMBER_LIST
        )
    )

    print(
        squared_numbers
    )


def main() -> None:
    """
    Execute the program.
    """

    display_squared_numbers()


if __name__ == "__main__":
    main()