"""
Program to process a large dataset
using a generator.
"""


DATASET_SIZE: int = 1_000_000


def generate_dataset():
    """
    Generate data one item at a time.
    """

    for number in range(
            1,
            DATASET_SIZE + 1
    ):
        yield number


def process_dataset() -> None:
    """
    Process dataset using generator.
    """

    dataset_generator = (
        generate_dataset()
    )

    processed_count = 0

    for number in dataset_generator:
        processed_count += 1

        if processed_count == 10:
            break

    print(
        f"Processed {processed_count} records."
    )


def main() -> None:
    """
    Execute the program.
    """

    process_dataset()


if __name__ == "__main__":
    main()