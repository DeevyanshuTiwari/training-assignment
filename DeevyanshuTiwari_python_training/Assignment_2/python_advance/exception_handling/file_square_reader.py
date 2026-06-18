"""
Program to read a number from a file
and print its square using
try-except-else-finally.
"""

FILE_NAME: str = "number.txt"


def read_number_from_file() -> None:
    """
    Read a number from a file
    and print its square.
    """

    try:
        with open(
                FILE_NAME,
                "r"
        ) as file:

            number = int(
                file.read().strip()
            )

    except FileNotFoundError:
        print(
            "File not found."
        )

    except ValueError:
        print(
            "File does not contain a valid integer."
        )

    else:
        square = number * number

        print(
            f"Square: {square}"
        )

    finally:
        print(
            "File operation completed."
        )


def main() -> None:
    """
    Execute the program.
    """

    read_number_from_file()


if __name__ == "__main__":
    main()