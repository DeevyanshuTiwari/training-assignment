"""
Use package modules.
"""

from student_package.student import (
    get_student_name
)

from student_package.teacher import (
    get_teacher_name
)


def main() -> None:
    """
    Execute the program.
    """

    print(
        get_student_name()
    )

    print(
        get_teacher_name()
    )


if __name__ == "__main__":
    main()