"""
Program to handle ZeroDivisionError.
"""


def divide_numbers() -> None:
    """
    Divide two numbers and handle division by zero.
    """

    try:
        first_number = float(
            input("Enter first number: ")
        )

        second_number = float(
            input("Enter second number: ")
        )

        division_result = (
                first_number / second_number
        )

        print(
            f"Result: {division_result}"
        )

    except ZeroDivisionError:
        print(
            "Cannot divide by zero."
        )


def main() -> None:
    """
    Execute the program.
    """

    divide_numbers()


if __name__ == "__main__":
    main()