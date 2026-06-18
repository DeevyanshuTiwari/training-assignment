"""
Program to extract all numbers
from a string using regular expressions.
"""

import re


def extract_numbers_from_text(
        text: str
) -> list[str]:
    """
    Extract numbers from text.

    Args:
        text: Input string.

    Returns:
        List of numbers found.
    """

    return re.findall(
        r"\d+",
        text
    )


def main() -> None:
    """
    Execute the program.
    """

    text = input(
        "Enter a string: "
    )

    numbers = (
        extract_numbers_from_text(
            text
        )
    )

    print(
        f"Numbers Found: {numbers}"
    )


if __name__ == "__main__":
    main()