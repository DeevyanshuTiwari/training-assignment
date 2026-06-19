"""
Use mathematical operation package.
"""

from math_operations.addition import (
    add_numbers
)

from math_operations.subtraction import (
    subtract_numbers
)

from math_operations.multiplication import (
    multiply_numbers
)

from math_operations.division import (
    divide_numbers
)


def main() -> None:
    """
    Execute the program.
    """

    print(
        add_numbers(10, 5)
    )

    print(
        subtract_numbers(10, 5)
    )

    print(
        multiply_numbers(10, 5)
    )

    print(
        divide_numbers(10, 5)
    )


if __name__ == "__main__":
    main()