"""
Program to demonstrate a built-in iterator
using range().
"""


START_NUMBER: int = 1
END_NUMBER: int = 5


def iterate_using_range() -> None:
    """
    Create an iterator from range
    and iterate over its elements.
    """

    range_iterator = iter(
        range(
            START_NUMBER,
            END_NUMBER + 1
        )
    )

    for number in range_iterator:
        print(number)


def main() -> None:
    """
    Execute the program.
    """

    iterate_using_range()


if __name__ == "__main__":
    main()