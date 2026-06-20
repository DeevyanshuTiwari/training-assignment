"""
Program to explain the difference
between iterator and generator.
"""


def generate_numbers():
    """
    Generator function.
    """

    yield 1
    yield 2
    yield 3


def demonstrate_iterator() -> None:
    """
    Demonstrate iterator example.
    """

    number_list = [1, 2, 3]

    list_iterator = iter(
        number_list
    )

    print("Iterator Output:")

    print(next(list_iterator))
    print(next(list_iterator))
    print(next(list_iterator))


def demonstrate_generator() -> None:
    """
    Demonstrate generator example.
    """

    number_generator = (
        generate_numbers()
    )

    print("\nGenerator Output:")

    print(next(number_generator))
    print(next(number_generator))
    print(next(number_generator))


def main() -> None:
    """
    Execute the program.
    """

    demonstrate_iterator()
    demonstrate_generator()


if __name__ == "__main__":
    main()