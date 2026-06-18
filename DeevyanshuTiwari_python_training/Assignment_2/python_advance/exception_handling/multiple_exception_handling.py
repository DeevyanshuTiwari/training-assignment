"""
Program to handle multiple exceptions.
"""


def perform_division() -> None:
    """
    Take user input and handle
    multiple exceptions.
    """

    try:
        user_number = int(
            input("Enter a number: ")
        )

        result = 100 / user_number

        print(
            f"Result: {result}"
        )

    except ValueError:
        print(
            "Invalid input. Please enter an integer."
        )

    except ZeroDivisionError:
        print(
            "Division by zero is not allowed."
        )


def main() -> None:
    """
    Execute the program.
    """

    perform_division()


if __name__ == "__main__":
    main()