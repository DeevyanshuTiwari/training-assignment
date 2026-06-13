"""
Programs for:
35. Write Name to File
36. Count Words, Lines, and Characters
37. Append Data to File
38. Copy File Content
39. Search Word in File
"""


def write_name_to_file():
    """Create a file and write name into it."""

    with open("student.txt", "w") as file:
        file.write("Deevyanshu Tiwari")

    print("Name written successfully.")


def count_file_details():
    """Count words, lines, and characters in a file."""

    with open("student.txt", "r") as file:
        content = file.read()

    word_count = len(content.split())
    line_count = len(content.splitlines())
    character_count = len(content)

    print("Words:", word_count)
    print("Lines:", line_count)
    print("Characters:", character_count)


def append_data_to_file():
    """Append data to existing file."""

    with open("student.txt", "a") as file:
        file.write("\nB.Tech CSE")

    print("Data appended successfully.")


def copy_file_content():
    """Copy content from one file to another."""

    with open("student.txt", "r") as source_file:
        content = source_file.read()

    with open("copy_student.txt", "w") as destination_file:
        destination_file.write(content)

    print("File copied successfully.")


def search_word_in_file():
    """Search a word in a file."""

    search_word = input("Enter word to search: ")

    with open("student.txt", "r") as file:
        content = file.read()

    if search_word in content:
        print("Word found.")
    else:
        print("Word not found.")


def main():
    """Run all file handling programs."""

    print("Question 35")
    write_name_to_file()

    print("\nQuestion 36")
    count_file_details()

    print("\nQuestion 37")
    append_data_to_file()

    print("\nQuestion 38")
    copy_file_content()

    print("\nQuestion 39")
    search_word_in_file()


if __name__ == "__main__":
    main()