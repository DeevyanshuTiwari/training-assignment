"""
Programs for:
25. List Operations
26. Count Even and Odd Numbers
27. Reverse a List Without Using reverse()
"""


def perform_list_operations():
    """Find sum, maximum, sort list, and remove duplicates."""

    numbers = [10, 20, 30, 40, 50, 20, 60, 70, 30, 80]

    print("Original List:", numbers)

    print("Sum =", sum(numbers))
    print("Maximum Number =", max(numbers))

    sorted_numbers = sorted(numbers)
    print("Sorted List =", sorted_numbers)

    unique_numbers = list(set(numbers))
    print("List Without Duplicates =", unique_numbers)


def count_even_and_odd_numbers():
    """Count even and odd numbers in a list."""

    numbers = [10, 15, 20, 25, 30, 35, 40]

    even_count = 0
    odd_count = 0

    for number in numbers:
        if number % 2 == 0:
            even_count += 1
        else:
            odd_count += 1

    print("Even Numbers =", even_count)
    print("Odd Numbers =", odd_count)


def reverse_list():
    """Reverse a list without using reverse()."""

    numbers = [10, 20, 30, 40, 50]

    reversed_numbers = numbers[::-1]

    print("Original List =", numbers)
    print("Reversed List =", reversed_numbers)


def main():
    """Run all list programs."""

    print("Question 25")
    perform_list_operations()

    print("\nQuestion 26")
    count_even_and_odd_numbers()

    print("\nQuestion 27")
    reverse_list()


if __name__ == "__main__":
    main()