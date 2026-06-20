"""
Program to create an iterator
for a list and print elements
using next().
"""


def display_list_elements() -> None:
    """
    Create an iterator for a list
    and print elements using next().
    """

    number_list = [10, 20, 30, 40, 50]

    list_iterator = iter(
        number_list
    )

    print(next(list_iterator))
    print(next(list_iterator))
    print(next(list_iterator))
    print(next(list_iterator))
    print(next(list_iterator))


def main() -> None:
    """
    Execute the program.
    """

    display_list_elements()


if __name__ == "__main__":
    main()