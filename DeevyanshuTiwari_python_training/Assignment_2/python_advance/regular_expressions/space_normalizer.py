"""
Program to replace multiple spaces
with a single space.
"""

import re


MULTIPLE_SPACE_PATTERN: str = r"\s+"


def normalize_spaces(
        text: str
) -> str:
    """
    Replace multiple spaces
    with a single space.
    """

    return re.sub(
        MULTIPLE_SPACE_PATTERN,
        " ",
        text
    ).strip()


def main() -> None:
    """
    Execute the program.
    """

    text = input(
        "Enter text: "
    )

    normalized_text = (
        normalize_spaces(
            text
        )
    )

    print(
        f"Result: {normalized_text}"
    )


if __name__ == "__main__":
    main()