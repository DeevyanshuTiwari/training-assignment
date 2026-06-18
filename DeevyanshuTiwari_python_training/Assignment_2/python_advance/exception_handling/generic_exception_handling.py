"""
Program to catch all exceptions
and print the error message.
"""


def handle_all_exceptions() -> None:
    """
    Catch all exceptions and
    display the error message.
    """

    try:
        user_number = int(
            input("Enter a number: ")
        )

        result = 100 / user_number

        print(
            f"Result: {result}"
        )

    except Exception as error:
        print(
            f"Error: {error}"
        )


def main() -> None:
    """
    Execute the program.
    """

    handle_all_exceptions()


if __name__ == "__main__":
    main()