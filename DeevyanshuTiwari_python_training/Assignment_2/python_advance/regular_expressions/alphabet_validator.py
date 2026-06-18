"""
Program to validate whether
a string contains only alphabets.
"""

import re


ALPHABET_PATTERN: str = r"^[A-Za-z]+$"


def contains_only_alphabets(
        text: str
) -> bool:
    """
    Check whether text contains
    only alphabets.
    """

    return bool(
        re.match(
            ALPHABET_PATTERN,
            text
        )
    )


def main() -> None:
    """
    Execute the program.
    """

    text = input(
        "Enter text: "
    )

    if contains_only_alphabets(
            text
    ):
        print(
            "Contains only alphabets."
        )
    else:
        print(
            "Contains non-alphabet characters."
        )


if __name__ == "__main__":
    main()