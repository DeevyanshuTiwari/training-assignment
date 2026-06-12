"""
Programs for:
28. Tuple Operations
29. Convert Tuple to List and Modify
30. Set Operations
31. Remove Duplicates Using Set
32. Student Dictionary
33. Character Frequency Using Dictionary
34. Merge Two Dictionaries
"""


def tuple_operations():
    """Create a tuple and access its elements."""

    student_details = ("Deevyanshu", 22, "CSE")

    print("Tuple:", student_details)
    print("First Element:", student_details[0])
    print("Second Element:", student_details[1])
    print("Third Element:", student_details[2])


def modify_tuple():
    """Convert tuple to list and modify it."""

    numbers_tuple = (10, 20, 30, 40)

    numbers_list = list(numbers_tuple)
    numbers_list.append(50)

    print("Original Tuple:", numbers_tuple)
    print("Modified List:", numbers_list)


def set_operations():
    """Perform union, intersection, and difference."""

    first_set = {1, 2, 3, 4, 5}
    second_set = {4, 5, 6, 7, 8}

    print("Union:", first_set.union(second_set))
    print("Intersection:", first_set.intersection(second_set))
    print("Difference:", first_set.difference(second_set))


def remove_duplicates():
    """Remove duplicates from a list using set."""

    numbers = [10, 20, 20, 30, 40, 40, 50]

    unique_numbers = list(set(numbers))

    print("Original List:", numbers)
    print("List Without Duplicates:", unique_numbers)


def student_dictionary():
    """Create and access student dictionary."""

    student = {
        "name": "Deevyanshu",
        "age": 22,
        "branch": "CSE"
    }

    print("Student Name:", student["name"])
    print("Student Age:", student["age"])
    print("Student Branch:", student["branch"])


def count_character_frequency():
    """Count frequency of characters in a string."""

    text = input("Enter a string: ")

    character_frequency = {}

    for character in text:
        if character in character_frequency:
            character_frequency[character] += 1
        else:
            character_frequency[character] = 1

    print("Character Frequency:")
    print(character_frequency)


def merge_dictionaries():
    """Merge two dictionaries."""

    first_dictionary = {
        "name": "Deevyanshu"
    }

    second_dictionary = {
        "branch": "CSE"
    }

    merged_dictionary = {
        **first_dictionary,
        **second_dictionary
    }

    print("Merged Dictionary:")
    print(merged_dictionary)


def main():
    """Run all programs."""

    print("Question 28")
    tuple_operations()

    print("\nQuestion 29")
    modify_tuple()

    print("\nQuestion 30")
    set_operations()

    print("\nQuestion 31")
    remove_duplicates()

    print("\nQuestion 32")
    student_dictionary()

    print("\nQuestion 33")
    count_character_frequency()

    print("\nQuestion 34")
    merge_dictionaries()


if __name__ == "__main__":
    main()