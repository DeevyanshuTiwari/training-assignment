"""
Program to create a custom iterator
that returns numbers from 1 to N.
"""


class NumberIterator:
    """
    Custom iterator that returns
    numbers from 1 to N.
    """

    def __init__(
            self,
            maximum_number: int
    ) -> None:
        self.maximum_number = maximum_number
        self.current_number = 1

    def __iter__(self):
        """
        Return iterator object.
        """
        return self

    def __next__(self) -> int:
        """
        Return next number.
        """

        if self.current_number > self.maximum_number:
            raise StopIteration

        number = self.current_number
        self.current_number += 1

        return number


def display_numbers(
        maximum_number: int
) -> None:
    """
    Display numbers from 1 to N.
    """

    number_iterator = NumberIterator(
        maximum_number
    )

    for number in number_iterator:
        print(number)


def main() -> None:
    """
    Execute the program.
    """

    maximum_number = int(
        input("Enter N: ")
    )

    display_numbers(
        maximum_number
    )


if __name__ == "__main__":
    main()