"""
Program demonstrating
debugging using pdb.
"""

import pdb


def calculate_average(
        total_marks: int,
        total_subjects: int
) -> float:
    """
    Calculate average marks.
    """

    pdb.set_trace()

    average = (
            total_marks
            * total_subjects
    )

    return average


def main() -> None:
    """
    Execute the program.
    """

    result = calculate_average(
        500,
        5
    )

    print(
        f"Average: {result}"
    )


if __name__ == "__main__":
    main()