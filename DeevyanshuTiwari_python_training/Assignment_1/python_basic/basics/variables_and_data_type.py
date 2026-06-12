"""
Programs for:
4. Variables and Data Types
5. Swapping Two Numbers
6. Arithmetic Operations
"""


def display_variable_types():
    """Create variables and display their types."""

    integer_number = 10
    decimal_number = 10.5
    student_name = "Deevyanshu"
    is_student = True

    print("Integer Type:", type(integer_number))
    print("Float Type:", type(decimal_number))
    print("String Type:", type(student_name))
    print("Boolean Type:", type(is_student))


def swap_numbers():
    """Swap two numbers."""

    first_number = int(input("Enter first number: "))
    second_number = int(input("Enter second number: "))

    print("\nBefore Swapping:")
    print("First Number =", first_number)
    print("Second Number =", second_number)

    first_number, second_number = second_number, first_number

    print("\nAfter Swapping:")
    print("First Number =", first_number)
    print("Second Number =", second_number)


def perform_arithmetic_operations():
    """Perform basic arithmetic operations."""

    first_number = float(input("Enter first number: "))
    second_number = float(input("Enter second number: "))

    print("\nSum =", first_number + second_number)
    print("Difference =", first_number - second_number)
    print("Multiplication =", first_number * second_number)

    if second_number != 0:
        print("Division =", first_number / second_number)
    else:
        print("Division by zero is not allowed.")


def main():
    """Run all programs."""

    print("Question 4")
    display_variable_types()

    print("\nQuestion 5")
    swap_numbers()

    print("\nQuestion 6")
    perform_arithmetic_operations()


if __name__ == "__main__":
    main()