"""
Program to check whether
a word exists in a sentence.
"""

import re


def search_word_in_sentence(
        sentence: str,
        word: str
) -> bool:
    """
    Search word in sentence.

    Args:
        sentence: Input sentence.
        word: Word to search.

    Returns:
        True if found else False.
    """

    return bool(
        re.search(
            word,
            sentence
        )
    )


def main() -> None:
    """
    Execute the program.
    """

    sentence = input(
        "Enter a sentence: "
    )

    word = input(
        "Enter word to search: "
    )

    if search_word_in_sentence(
            sentence,
            word
    ):
        print(
            "Word Found"
        )
    else:
        print(
            "Word Not Found"
        )


if __name__ == "__main__":
    main()