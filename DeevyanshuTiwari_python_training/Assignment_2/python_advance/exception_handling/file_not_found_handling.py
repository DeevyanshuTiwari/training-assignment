"""
Program to handle FileNotFoundError.
"""


FILE_NAME: str = "sample.txt"


def read_file() -> None:
    """
    Open and read a file.
    """

    try:
        with open(
                FILE_NAME,
                "r"
        ) as file:

            file_content = file.read()

            print(
                "File Content:"
            )

            print(
                file_content
            )

    except FileNotFoundError:
        print(
            f"Error: '{FILE_NAME}' not found."
        )


def main() -> None:
    """
    Execute the program.
    """

    read_file()


if __name__ == "__main__":
    main()